---
layout: post
title: Understanding Scala Streams through Fibonacci
header-img: img/old-bridge-with-green-field-in-front-of-it.png
abstract: A functional Stream is a lazily computed data structure that can be hard to wrap your head around. I take a stab at explaining it while computing the Fibonacci series.
tags:
- functional
- scala
---
I took a look at [Scala](http://scala-lang.org) [Streams](http://blog.danielwellman.com/2008/03/streams-in-scal.html) tonight (or was that last night? When am I posting this?) and thought I'd share what I learned from [Literate Programs](http://en.literateprograms.org/Fibonacci_numbers_%28Scala%29) and the Scala source code.

Streams
-------

For the uninitiated, a *Stream* in Scala helps realize one of the fundamental concepts of [Functional Programming](http://www.joelonsoftware.com/items/2006/08/01.html), that of [*laziness*](http://en.wikipedia.org/wiki/Lazy_evaluation). In essence, a *Stream* as infinite - think of a [collection](http://www.codecommit.com/blog/scala/scala-collections-for-the-easily-bored-part-1 "The Scala Collection library is one of those things about Scala that, 'if you look at Scala for only one reason, this would be it', except that there are about a dozen of those 'things'") that just goes on and on and on and on and on and on. It would be [asinine](http://en.wikipedia.org/wiki/Paris_Hilton "Seriously... this chick is too dumb to really warrant our time - Yes, I understand the irony of linking to her here... shut up and keep reading") to construct the entire collection before ever using it as that would only ensure that you never used it. A *Stream* is evaluated on an as-needed basis and only up to the point that you need it.

The Delicious, Delicious Code...
--------------------------------

Let's illustrate using good ol' [Fibonacci Numbers](http://en.wikipedia.org/wiki/Fibonacci_number "Fibonacci numbers are the 'other' facility used to illustrate programming concepts - there are only two, and the other is 'Hello World'."). We'll construct a *Stream* recursively because that's more fun and it gives more meat to discuss. (This example comes straight from [the Literate Programs](http://en.literateprograms.org/Fibonacci_numbers_%28Scala%29) website but I'm hoping to explain it in a bit more depth, and not get it too wrong in the process)

``` scala
import scala.math.BigInt
lazy val fibs: Stream[BigInt] = BigInt(0) #::
                                BigInt(1) #::
                                fibs.zip(fibs.tail).map { n => n._1 + n._2 }
```

(**Note:** The above must all be on one line or the compiler is going to have a tough time pimping it out) Sweet and delicious, no?

Dissection
----------

Let's break it down:

**lazy**

- Strictly speaking, this isn't needed, but why not be as lazy as you can possibly be?
- For the truly uninitiated, this ensures that the `fibs` value is not evaluated until it's actually used.

**val fibs: Stream[BigInt]**

- "What the hell is this? I thought Scala *inferred* types!", I hear you scream.
- Scala does infer types but we're defining a *recursive value* here and Scala needs to understand that the recursive call is a recursive call on a *Stream* as opposed to a *String* or some other completely unrelated type.

**BigInt(0) \#:: BigInt(1) \#::**

- The Fibonacci series starts with 0 and 1 so we shove those in right here. But what's this `#::` stuff?
- Here's our first *real* magical point and even the somewhat initiated may be scratching their heads at this one.
- If you look at the [Stream](http://www.scala-lang.org/api/2.9.0/index.html#scala.collection.immutable.Stream "I'm directing this topic at to Scala 2.9.0 right now as opposed to 'current' because I have no idea what will happen in the future") api you won't find the `#::` member function anywhere, and indeed you shouldn't as we're not working with a *Stream* right now, but a [BigInt](http://www.scala-lang.org/api/2.9.0/index.html#scala.math.BigInt "My Int's bigger than yours"). So with that realization in place, we must also recognize that methods ending in `:` are [right associative](http://stackoverflow.com/questions/1162924/what-good-are-right-associative-methods-in-scala "Someone wanted to call it 'wrong associative' but Martin fired that guy").
  - What this means is that the object we're calling the `#::` method on is actually the *Stream* object that is returned from the `map` command at the end of the call (we'll get back to that, just hang on).
  - Remember how we had to declare that `fibs` was a *Stream* instead of letting Scala infer it? Well, that's (one of the reasons) why we need to do that.
- Now we know that the method is being called on the *Stream* but when we look at the [api](http://www.scala-lang.org/api/2.9.0/index.html#scala.collection.immutable.Stream) for *Stream*, again we don't see that method call. So where is it?
- This method gets [pimped on](http://scala.sygneca.com/patterns/pimp-my-library) through the

  ``` scala
  implicit def consWrapper[A](stream: => Stream): ConsWrapper[A]
  ```

  method defined on the [Stream companion object](http://www.scala-lang.org/api/2.9.0/index.html#scala.collection.immutable.Stream$). Scala will resolve the lack of a `#::` method on *Stream* by finding an implicit conversion to a type that does have that method, and this is the [ConsWrapper](http://www.scala-lang.org/api/2.9.0/index.html#scala.collection.immutable.Stream$$ConsWrapper "I once knew this guy who was a Rapper that spent five years in jail... coincidence?").  The result of that method is to return a new *Stream*.
- Ok, but how's that of any use? The conversion to *ConsWrapper* is going to kill us due to the fact that it's going to call the recursive function... or is it?
- Well, of course it's not. If you look at the paramter to the *ConsWrapper* you'll see why this doesn't cause immediate recursion:

  ``` scala
  new ConsWrapper(tl: => Stream[A])
  ```

  That's a [by-name parameter](http://locrianmode.blogspot.com/2011/07/scala-by-name-parameter.html). This means that it's not invoked until it's used.
- Wow, that's a lot of stuff...

**fibs.zip(fibs.tail)**

- Go look at [the Stream api](http://www.scala-lang.org/api/2.9.0/index.html#scala.collection.immutable.Stream "I bet you didn't even hover over this link.") again, specifically the `zip`. I'll wait. You didn't read it, did you? Fine... on your own head, be it.
- The size of the Stream returned by `tail` is one-less than the size of the Stream itself. This means that the *zipped* result is going to be the size of the Stream returned by *tail, not the size of the Stream itself*.
- So, in the initial case (where the Stream is `Stream(0, 1, [Stream])`, the zipped result is actually `Stream((0, 1), ([Stream]))` because the tail, of one element, is paired with the head. `Stream.zip` creates a `Cons` cell containing `(this.head, that.head)` as the `Cons` head and `(this.tail zip that.tail)` as the `Cons` tail but *as a Stream of `(A1, B)`* so we're not evaluating it now - we'll evaluate it when it gets called.

**map { n =\> n.\_1 + n.\_2 }**
  
- Hmmm... I hesitate to say "duh?" but it really does seem appropriate to do so. I mean, if you've made it this far, you know what this does.

Use it
------

Ok, so what happens when we evaluate it? Let's get the first 20 numbers:

    scala> fibs take 20 foreach println
    0
    1
    1
    2
    3
    5
    8
    13
    21
    34
    55
    89
    144
    233
    377
    610
    987
    1597
    2584
    4181
    scala>

That looks right to me.

WTF?
----

Some of you may still be scratching your heads because I wasn't terribly clear on how this actually works. How is it that each number is evaluated as needed? Well, first let's prove that it is so. I'm going to modify the code slightly.

``` scala
import scala.math.BigInt
lazy val fibs: Stream[BigInt] =
    BigInt(0) #::
    BigInt(1) #::
    fibs.zip(fibs.tail).map(n => {
      println("Evaluating: %s -> %s".format(n._1, n._2))
      n._1 + n._2
    })
```

Now let's take the first 5:

    scala> fibs take 5 foreach println
    0
    1
    Evaluating: 0 -> 1
    1
    Evaluating: 1 -> 1
    2
    Evaluating: 1 -> 2
    3
    scala>

And let's take them again...

    scala> fibs take 5 foreach println
    0
    1
    1
    2
    3
    scala>

And let's take the first 7...

    scala> fibs take 7 foreach println
    0
    1
    1
    2
    3
    Evaluating: 2 -> 3
    5
    Evaluating: 3 -> 5
    8
    scala>

Ok, so it works; things are properly evaluated in a lazy manner. But how?

Why...
------

The culprit behind this magic appears to be the interplay between the `Cons` class, the `StreamIterator` class and the `LazyCell` class:

``` scala
final class Cons[+A](hd: A, tl: => Stream[A]) extends Stream[A]
// Note that the head is a value of type A and the tail is a by-name Stream
```

``` scala
final class StreamIterator[+A](self: Stream[A]) extends Iterator[A] {
  class LazyCell(st: => Stream[A]) {
    // Note the laziness
    lazy val v = st
  }
  private var these = new LazyCell(self)
  def next: A =
    if (isEmpty) Iterator.empty.next
    else {
      // Evaluate the laziness
      val cur = these.v
      // And the concrete value of type A
      val result = cur.head
      // Assign the next lazy cell to be the Stream in the tail
      these = new LazyCell(cur.tail)
      result
    }
}
```

Couple that together with the recursively defined `zip` we saw earlier, and you've got your lazy Stream of Fibonacci numbers. I love this stuff...
