---
layout: post
title: Here's (one of the reasons) why Scala is awesome
header-img: img/underground.png
abstract: My first real experience with using a monad in a way that simplified the crap out of what it was that I was doing.
tags:
- scala
- functional
---
Ok, I'm no [Monad](http://www.codecommit.com/blog/ruby/monads-are-not-metaphors "Daniel Spiewak sent me to this page... I read pretty much anything he says") expert by any stretch of the imagination... seriously. But, I did something today that has me so jazzed about Monads, partial functions and [Scala](http://www.scala-lang.org) in general, that I just gotta share.

So my task was to take some [JSON](http://www.json.org "How can it be that THE JSON page is so utterly hideous?  I mean, really, it's worse than my website") and convert it to a structure of my own classes, but still really general - i.e. we're not talking about "real" marshalling here.

So I took a simple approach, and used the Scala Library's [JSON Module](http://www.scala-lang.org/api/current/index.html#scala.util.parsing.json.JSON$ "I'll use something 'better' if the need ever arises."). I had to deal with type-casting, defaulting of values, and constraint checking (i.e. *field X must be defined*). The code to do all of this is so simple and so awesome that it's just hard to believe. Now, it's not perfect... I'll shore it up over the next while as need requires... but it's passing all of my tests pretty nicely right now.

I want to convert the following JSON:

``` javascript
{
  "vendor"  : "Whoever", // required
  "colour"  : "blue",    // default to "None"
  "widgets" :            // default to Empty list of strings
  [
    {
      "name"    : "One",   // required
      "flavour" : "Lime"   // default to Strawberry
    },
    {
      "name"    : "Two",   // required
      "flavour" : "Banana" // default to Strawberry
    }
  ]
}
```

to the following concrete Scala:

``` scala
case class Widget(name: String, flavour: String)
case class Contract(vendor: String, colour: String, widgets: List[Widget])
Contract("Whoever", "blue", List(Widget("One", "Lime"),
                                 Widget("Two", "Banana")))
```

I used a [Pattern Matching](http://www.codecommit.com/blog/scala/scala-for-java-refugees-part-4 "Daniel Spiewak again... love that dood.") example for this sort of thing from [an awesome StackOverflow post](http://stackoverflow.com/questions/4170949/how-to-parse-json-in-scala-using-standard-scala-classes) as a starter and then fleshed it out to meet my requirements. Here it is:

``` scala
// Types we need
case class Widget(name: String, flavour: String)
case class Contract(vendor: String, colour: String, widgets: List[Widget])

object Contract {
  // The class caster that came from the StackOverflow page
  // Yeah, this can throw exceptions... I don't care about
  // those for this example
  class ClassCaster[T] {
    def unapply(a: Any): Option[T] = Some(a.asInstanceOf[T])
  }

  // Concrete instances of the casters for pattern matching
  object AsMap extends ClassCaster[Map[String, Any]]
  object AsList extends ClassCaster[List[Any]]
  object AsString extends ClassCaster[String]

  // It can happen
  case class ContractException(message: String) extends Exception(message)

  // Returns an Either that is a hell of a lot more deterministic than
  // throwing exceptions around
  def fromJSON(json: String): Either[ContractException, Contract] = {
    // Helps us get rid of boilerplate
    def error[T](prefix: String): Option[T] =
      throw ContractException("%s must be specified".format(prefix))

    // Catch the exceptions and turn them into Left() instances
    try {
      val parsed = JSON.parseFull(json)
      if (parsed.isEmpty) throw ContractException("Unable to parse JSON.")

      // Here comes the monad / partial function love...
      val contract = for {
        Some(AsMap(map))  < - List(parsed)
        AsString(vendor)  <- map get "vendor" orElse error("vendor")
        AsString(colour)  <- map get "colour" orElse Some("None")
        AsList(widgets)   <- map get "widgets"
      } yield Contract(vendor, colour, for {
          AsMap(widget)     <- widgets
          AsString(name)    <- widget get "name" orElse error("name")
          AsString(flavour) <- widget get "flavour" orElse Some("Strawberry")
        } yield Widget(name, flavour)
      )
      Right(contract.head)
    } catch {
      case e: ContractException =>
        Left(e)
      case e =>
        Left(ContractException(e.getMessage))
    }
  }
}
```

It's very impressive what this code actually handles when you start digging into it. I'm sure you can easily think about what it would be like to code this in Java (or spaghetti monster forbid C++) and know that it's going to be a *lot* more verbose and probably a lot less reliable.

**UPDATE:** One of the key reasons why the above is so cool is this:

``` scala
<- map get "colour" orElse Some("None")
<- widget get "name" orElse error("name")
```

By using a generator syntax (\<-) and the `get` function on [Map](http://www.scala-lang.org/api/current/index.html#scala.collection.Map) we get to work at a higher abstraction that allows us to chain the decision making process rather than what the imperative approach would demand of us. It's the monadic and compositional nature of `Option` that allows us to do this. Consider:

``` scala
String colour = map.get("colour");
if (colour == null)
  colour = "None";

String name = widget.get("name");
if (name == null)
  error("name");
```

I'm going to editorialise (I mean, this is my blog after all) and point out the following problems:

-   It's unclear. Sure, if you've seen it a million times (and let's face, you have... we all have) you know exactly what this "means" but that's not because its meaning is clear.
-   It's not syntactically atomic. Some nasty merge, or some careless programmer can easily insert something between lines 1 and 2 that uses `colour`.
-   It's repetitive in a bad way (is there a good way?). That pattern is going to be copy-pasted over and over again. How many times do you see "colour" in there? Yeah... three. So if I copy-paste that and forget to change the third one, you get this:

``` java
String flavour = widget.get("flavour");
if (flavour == null)
  colour = "None";
```

GHACK!

We need to program at a higher level.
