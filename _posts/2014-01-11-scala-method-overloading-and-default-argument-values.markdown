---
layout: post
title: Scala Method Overloading and Default Argument Values
header-img: img/old-bridge.jpg
abstract: Using the magnet pattern to get around a limitation with Scala's overloading implementation.
tag:
- scala
- implicits
- typeclasses
---
I was writing some code recently that wanted to do this (somewhat contrived to simplify the example):

``` scala
def set(key: String, value: String)(implicit ttl: Duration = 1.hour): Int
def set(key: String, value: ByteString)(implicit ttl: Duration = 2.hours): Int
```

There's some specific detail about how the [Scala][1] compiler implements default values for method parameters - that I haven't investigated and probalby wouldn't understand anyway - that makes it say the following:

```
... multiple overloaded alternatives of method set define default arguments.
```

There's clearly a way around this problem; _don't overload_.  So, I turned to the [magnet pattern][3] as popularized by the [Spray][2] library.  It's really just a specialized use for type classes that carries a cool name so it's easy to talk about and reference.  My use of the pattern, in this case, is even simpler because the return type of the method doesn't vary with the magnet instance.  At any rate, here's the deal:

``` scala
import akka.util.ByteString
import scala.concurrent.duration._

sealed trait ByteStringMagnet {
  val bs: ByteString
  val ttl: Duration
  def apply(f: (ByteString, Duration) => Int): Int = f(bs, ttl)
}

object ByteStringMagnet {
  import language.implicitConversions

  implicit def fromString(s: String)(implicit ttlive: Duration = 1.hour): ByteStringMagnet = new ByteStringMagnet {
    val ttl = ttlive
    val bs = ByteString(s)
  }

  implicit def fromByteString(bytes: ByteString)(implicit ttlive: Duration = 2.hours): ByteStringMagnet = new ByteStringMagnet {
    val ttl = ttlive
    val bs = bytes
  }
}

object Main extends App {
  /**
   * Now we have only one instance of the `set` method; the magnet pattern takes
   * care of pulling the various types down into the argument.
   */
  def set(key: String, magnet: ByteStringMagnet): Int =
    magnet { (value, ttl) =>
      // Do something with the ByteString value and return an Int
      5
    }
}
```

Problem solved.  The implicit conversions are resolved nicely because they're right on the `ByteStringMagnet` and, in general, you should never have to define an implicit conversion anywhere else (unless of course you need to extend the functionality from outside of the library).

  [1]: http://scala-lang.org "Scala"
  [2]: http://spray.io "Spray"
  [3]: http://spray.io/blog/2012-12-13-the-magnet-pattern/ "The magnet pattern"
