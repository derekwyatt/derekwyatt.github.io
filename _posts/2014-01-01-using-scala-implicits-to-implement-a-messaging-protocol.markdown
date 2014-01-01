---
layout: post
title: Using Scala Implicits to Implement a Messaging Protocol
---
(**NOTE**: I migrated this post, which I originally wrote, from my old employer's web site because the format of it kept breaking every time they did an update to the site. [Here's the original](http://blog.primal.com/using-scala-implicits-to-implement-a-messaging-protocol/))

A while back, when I worked at [Primal][1] I needed to create tons of different families of [Akka][2] [Actors][3], each of which implemented a structure defined in a JSON format.  With all of the different families that would be running around, I wanted to make sure that I could determine (via logs) the different threads of conversation that were going on.

(For the impatient, you can find all of this working code in [Primal][1]'s [Github repository][4].)

The Goal
========

Passing messages between [Actors][3] in [Akka][2] is very simple, and looks like this:

``` scala
someActor ! SomeMessage("Hello there, Mr. Actor")
```

Simple, elegant, and already hides part of the pain from the developer. When you send a message from one Actor to another, the sender goes along with it. This becomes more obvious when you see the method definition of `!`:

``` scala
def !(msg: Any)(implicit sender: ActorRef)
```

[Akka][2] ensures that the sender of the message gets wrapped up inside an envelope by putting the current Actor into the implicit scope with a member value:

``` scala
implicit final val self = // this Actor's corresponding ActorRef
```

What we're looking for is to create our own envelope that carries a great deal more information than just the sender of the message. We want to create an envelope that has the following:

- The class type of the sender
- The unique identifier of the sender
- The unique identifier of the intended recipient
- The class type of the message being sent
- The timestamp of the creation time of the message
- A unique identifier for the current “unit of work” in which this message is participating
- A sequence number that indicates where this message sits in the timeline of the unit of work
- A version number for the protocol version of the envelope
- The message itself

And the important part, is that we want all of this information to be supplied without burdening the code that sends the message. It should look like this:

``` scala
someActor emit SomeMessage("Hello there, Mr. Actor")
```

The guy that actually sends the message should have to do very little in order to gain all of the functionality that we want. The other side of that coin is that the guy shouldn’t be able to screw up.

The How (in pictures)
=====================

There are actually two separate goals, and they’re in tension:

1. Simplicity for the user of our library
2. Richness of data in the envelope

This tension is resolved using implicits, OO inheritance principles, and functional delegation. First, let’s remember what [Akka][2] does for us automatically:

<center><span style="font-size: 0.4em">Image from <a href="http://primal.com">Primal</a></span></center>
<img src="/images/akka-envelope-wrapping.png" style="background: white;">

When sending a message from one Actor to another, [Akka][2] automatically packages up our message and the Actor reference of the guy doing the send into an envelope that [Akka][2] can pull apart later. We want to convert the message being sent into an envelope of our own before [Akka][2] gets a hold of it. Based on nothing more than a different method (i.e. emit vs. !) we should be able to provide all we need.

<center><span style="font-size: 0.4em">Image from <a href="http://primal.com">Primal</a></span></center>
<img src="/images/our-envelope-wrapping.png" style="background: white;">

The How (in code)
=================

Let's start with the `Envelope` class:

``` scala
// This is the ultimate class in which we're interested.  It contains all of
// the meta-information we need in order to see what's what
case class Envelope(fromComponentType: ComponentType,
                    fromComponentId: ComponentId,
                    toComponentId: ComponentId,
                    messageType: MessageType,
                    workId: WorkId,
                    messageNum: MessageNum,
                    version: EnvelopeVersion,
                    createdTimeStamp: Long,
                    message: Any)
```

Clearly there are some support types in there that need some definitions, so let’s define them:

``` scala
// Defines the "type" of component in a message (e.g. MessageForwarder)
case class ComponentType(componentType: String)
val unknownComponentType = ComponentType("UnknownComponentType")

// Defines the identity of the given component (e.g. /path/to/MessageForwarder)
case class ComponentId(componentId: String)
val unknownComponentId = ComponentId("UnknownComponentId")

// Defines the type of message being sent (e.g. SendEmail)
case class MessageType(messageType: String)
val unknownMessageType = MessageType("UnknownMessageType")

// Defines the work identifier that this message is part of
case class WorkId(workId: String)
val unknownWorkId = WorkId("UnknownWorkId")
def createWorkId(): WorkId = WorkId(UUID.randomUUID().toString)

// Defines the sequence number of this message within the workId
case class MessageNum(messageNum: Int) {
  def increment: MessageNum = MessageNum(messageNum + 1)
}

// The version of the envelope protocol
case class EnvelopeVersion(version: Int = 1)
```
 
Nice... our basic ADT is in place, and it’s now it’s time to see how it gets constructed. In order to make it happen all sexy-like, it’s going to be done implicitly, by converting from a [Scala][5] Any (which is the type that [Akka][2] uses for messages) into our `Envelope`.

``` scala
// An implicit conversion makes things easy for us; we can convert from an Any
// to an Envelope with our implicit
trait EnvelopeImplicits {
  import scala.language.implicitConversions

  implicit def any2Envelope(a: Any)
                           (implicit fromComponentType: ComponentType,
                                     fromComponentId: ComponentId,
                                     workId: WorkId,
                                     messageNum: MessageNum,
                                     version: EnvelopeVersion): Envelope = 
    Envelope(fromComponentType,
             fromComponentId,
             unknownComponentId,
             MessageType(a.getClass.getSimpleName),
             workId,
             messageNum,
             version,
             System.currentTimeMillis,
             a)
             
}
```
 
Now, when we mix `EnvelopeImplicits` into the right right place, we’ll have an implicit conversion from a message to our envelope. But why on earth would [Scala][5] ever invoke that conversion? The answer lies in another usage of implicits that provides some extra functionality to the ActorRef:

``` scala
// Here we create an implicit class that extends the functionality of the
// ActorRef, which will provide the hook we need in order to convert from the
// Any to the Envelope that holds all of our interesting information
trait ActorRefImplicits {
  implicit class PimpedActorRef(ref: ActorRef) {
    def emit(envelope: Envelope)(implicit sender: ActorRef = Actor.noSender): Unit = {
      ref.tell(envelope.copy(
          toComponentId = ComponentId(makeAnIdForActorRef(ref.path)),
          messageNum = envelope.messageNum.increment
        ), sender)
    }

    def emitForward(envelope: Envelope)(implicit context: ActorContext): Unit = {
      ref.forward(envelope.copy(
          toComponentId = ComponentId(makeAnIdForActorRef(ref.path)),
          messageNum = envelope.messageNum.increment
        ))
    }
  }
}
```
 
Now, were you to mix `ActorRefImplicits` into the right place you'd have an implicit class, which would provide you the appropriate `emit` and `emitForward` calls. The important bit to note is what type these new methods require: `Envelope`. When you do this...

``` scala
someActor emit SomeMessage("Hello!")
```

... the compiler sees a problem. `SomeMessage("Hello!")` is not of type `Envelope`. Rather than throw an error and fail the compile, it searches the implicit scope for a conversion method that can translate something in `SomeMessage`'s class hierarchy into an instance of `Envelope`. Assuming that is available, the conversion will happen and you're good to go!

Now, let’s create a base `Actor` that handles the `Envelope`s for us, as well as puts the appropriate stuff into the implicit scope for subsequent message sending.

``` scala
// Here we go.  The EnvelopingActor base class binds all of the bits an pieces
// together into a decent whole.  Derivation implementations are thus easy.
trait EnvelopingActor extends Actor with EnvelopeImplicits with ActorRefImplicits {
  implicit val myComponentType: ComponentType = ComponentType(getClass.getSimpleName)
  implicit val myComponentId: ComponentId = ComponentId(makeAnIdForActorRef(self.path))

  private var currentWorkIdVar: WorkId = unknownWorkId
  implicit def workId: WorkId = currentWorkIdVar

  private var currentMessageNumVar: MessageNum = MessageNum(-1)
  implicit def messageNum: MessageNum = currentMessageNumVar

  def derivedReceive: Receive

  def derivedReceiveWrapper(wrapped: Receive): Receive = {
    case Envelope(_, _, _, _, workId, messageNum, _, _, message) =>
      currentWorkIdVar = workId
      currentMessageNumVar = messageNum
      wrapped(message)
    case message =>
      currentWorkIdVar = createWorkId()
      currentMessageNumVar = MessageNum(-1)
      wrapped(message)
  }

  final def receive = derivedReceiveWrapper(derivedReceive)
}
```

The job of the `EnvelopingActor` is to handle the complexity for its derivations. Four implicit values are managed:

- "This" `Actor`'s component type
- "This" `Actor`'s component Id
- Any current `workId` that might be known
- Any current message sequence number that might be known

With these values in the implicit scope, any message emit that the derivation might execute will ensure that enveloping happens correctly.

So, let's see it in action...

``` scala
val system = ActorSystem("ImplicitMessaging")
implicit val ec = system.dispatcher
implicit val askTimeout = Timeout(5.seconds)

// Let's send it a simple non-envelope message and see what happens
val a = system.actorOf(Props(new MyActor))

// Now let's send it an existing envelope to see what happens
val env = Envelope(ComponentType("MyActor"),
                   ComponentId(makeAnIdForActorRef(a.path)),
                   ComponentId("Main:1"),
                   MessageType("String"),
                   createWorkId(),
                   MessageNum(0),
                   envelopeVersion,
                   System.currentTimeMillis,
                   "Hello")
a ? env onSuccess {
  case msg =>
    println(s"\n$msg\n")
    system.shutdown()
}
```
 
And this prints out something like the following...

``` scala
Envelope(
  ComponentType(MyActor),
  ComponentId(123456),
  ComponentId(/$a),
  MessageType(String),
  WorkId(6cbdf4ac-5843-4c6b-af30-67e7d28b7b78),
  MessageNum(1),
  EnvelopeVersion(1),
  1369255095475,
  Hello
)
```

There you have it! The main point here is the simplicity of the derivations of the `EnvelopingActor`. One of the most important aspects of Scala's implicits is their ability to move complexity out of user code and into library code. I think the definition of `MyActor` embodies that whole idea:

``` scala
// This class extends the EnvelopingActor and all it has to do is
// implement derivedReceive instead of receive and change ! to emit.
class MyActor extends EnvelopingActor {
  def derivedReceive = {
    case msg =>
      sender emit "Hello"
  }
}
```

`MyActor` doesn't have to understand any of the plumbing, or meta-enveloping that was just set up; all it does is pass messages as it usally does, with the small change of swapping `!` for `emit`.
 
Enjoy Scala's implicits!

Addendum
--------

I use this example in a presentation I gave at my local [Scala][5] meetup, which you can see here:

<center><iframe src="http://www.slideshare.net/slideshow/embed_code/27040335" width="720" height="592" frameborder="0" marginwidth="0" marginheight="0" scrolling="no" style="border:1px solid #CCC;border-width:1px 1px 0;margin-bottom:5px" allowfullscreen> </iframe> <div style="margin-bottom:5px"> <strong> <a href="https://www.slideshare.net/DerekWyatt1/scala-implicits-not-to-be-feared" title="Scala Implicits - Not to be feared" target="_blank">Scala Implicits - Not to be feared</a> </strong> from <strong><a href="http://www.slideshare.net/DerekWyatt1" target="_blank">Derek Wyatt</a></strong> </div></center>

  [1]: http://primal.com "Primal"
  [2]: http://akka.io "Akka"
  [3]: http://doc.akka.io/docs/akka/current/scala/actors.html "Actors"
  [4]: https://github.com/primal-github/implicit-messaging "Primal's Github repository"
  [5]: http://scala-lang.org "Scala"
