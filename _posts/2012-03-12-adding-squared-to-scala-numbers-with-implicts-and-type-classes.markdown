---
layout: post
title: Adding `squared` to Scala Numbers with Implicts and Type Classes
header-img: img/female-sitting-in-library.jpg
abstract: A very simple look at how to pimp a method onto a class using Scala's implicits.
tags:
- scala
---
I recently enjoyed a couple of days with [Heiko Seeberger](https://twitter.com/#!/hseeberger) and [Josh Suereth](https://twitter.com/#!/jsuereth) as they dragged me through some [Advanced Scala Training](http://typesafe.com/products/training) and it was quite awesome. As an exercise, to test out my new found skills I [pimped](http://www.artima.com/weblogs/viewpost.jsp?thread=179766) a new method onto "numbers" in Scala and I thought it might be instructive to share.

### The Goal

The goal is really quite simple. All we want to do is add a new method, `squared` onto "numbers". I don't mean `Int` directly, but anything that Scala considers a number. We should see things like this:

``` scala
5.squared    // == 25
25.0.squared // == 625.0
```

### The Code

The code is wonderfully simple for this.

``` scala
implicit def numeric2Powerable[A : Numeric](i: A) = new {
  def squared: A = implicitly[Numeric[A]].times(i, i)
}
```

Done. What we've managed to do in such a small amount of code is quite surprising. Let's break it down.

-   `implicit`: The `implicit` puts the conversion definition into the local scope so that we can get a transparent conversion from our number to our pimped object.
-   `[A : Numeric]`: This is a [context bound](http://stackoverflow.com/questions/2982276/what-is-a-context-bound-in-scala). It states that the type parameter we're going to constrain this implicit conversion to must have an instance of the [Numeric](http://www.scala-lang.org/api/current/index.html#scala.math.Numeric) type class in scope. It's this constraint that limits the types we're pimping down to being numbers.
-   `implicitly[...]`: Because we've chosen the "clean" declaration for the context bound (as opposed to the slightly uglier curried parameter version) the instance of the type class has not been bound to an identifier that we can later use. In order to get access to the instance, we must "look it up". Because [scala.Predef](http://www.scala-lang.org/api/current/index.html#scala.Predef$) is always imported by default, and because `implicitly` is defined there, we have access to it, and this is what we use to resolve the implicit instance.

The reset is self-explanatory.

That's all there is to it. We've defined an implicit conversion from a number to a new pimp-class, used a type class to narrow the target types for the pimp, and pulled in the instance of the type class to give us a concrete tool to use in performing the computation. Not bad for a couple of lines of code.

**Addendum**: [Missingfaktor](https://twitter.com/#!/missingfaktor) showed me another little fun bit that you can add on to the code above to make the implementation feel more natural. We can import the implicits that are defined on Numeric to give us some infix notation instead.

``` scala
implicit def numeric2Powerable[A : Numeric](i: A) = new {
  import Numeric.Implicits._
  def squared: A = i * i
}
```

It doesn't change the API at all, but it does look a little niftier with respect to the implementation. It's a little less simple, from an instructive point of view, because you start to wonder where the `implicitly` wandered away to. So, some more explanation is probably in order.

Specifically, what we're importing is:

``` scala
object Numeric {
  trait ExtraImplicits {
    implicit def infixNumericOps[T](x: T)(implicit num: Numeric[T]): Numeric[T]#Ops = new num.Ops(x)    
  }
  object Implicits extends ExtraImplicits { }
}
```

So, it's really the `infixNumericOps` that we're pulling in, and it's delegating to `Ops`. So, what's `Ops`?

`Ops` is defined as an inner class to the `Numeric` type trait and looks like:

``` scala
class Ops(lhs: T) {
  def +(rhs: T) = plus(lhs, rhs)
  def -(rhs: T) = minus(lhs, rhs)
  def *(rhs: T) = times(lhs, rhs)
  def unary_-() = negate(lhs)
  def abs(): T = Numeric.this.abs(lhs)
  def signum(): Int = Numeric.this.signum(lhs)
  def toInt(): Int = Numeric.this.toInt(lhs)
  def toLong(): Long = Numeric.this.toLong(lhs)
  def toFloat(): Float = Numeric.this.toFloat(lhs)
  def toDouble(): Double = Numeric.this.toDouble(lhs)    
}
```

So, when we stitch this together, here's what happens:

1.  We bring `infixNumericOps` into scope.
2.  We then invoke `i * i`, which is equivalent to `i.*(i)`.
3.  The `*` can't be found on `i` since `i` is of type `A`.
4.  However, now that we have `infixNumericOps` in scope, the compiler can use that conversion to create an instance of `Numeric[A]#Ops`.
5.  `Numeric[A]#Ops` *does* have `*` defined for type `A`, thus, a new `Ops` is constructued, holding our value `i` within it.
6.  The compiler then applies the `*` operator to to the second value of `i` and we're done.

Cooler, but a hell of a lot more complicated to explain :)
