---
layout: post
title: A Sequential Execution Context
header-img: img/underground.png
abstract: Scala's `ExecutionContext` is a much more powerful abstraction than you might think. This post covers how to use it to ensure sequential execution of work without muddying up your code whatsoever.
tags:
- scala
---
I recently did a code review of git commit that was done by the CEO.  In it, he simplified a piece of code that I was somewhat responsible for.  You see, I've got this bright co-op student who does a lot of good stuff without my help.  One day he gave me a code review, and I told him a few things he could improve and sent him on his merry way.  What I was missing, was that there as an astonishingly better way to accomplish what he was doing.  That better way was coded by the CEO of [Auvik][1], and it's why I like working for him :)

The Goal
--------

The goal is really quite simple: you want a bunch of _future work_ to complete in _sequence_ not in parallel.  The specific case is a database table, and we want to be sure that it's not going to do work out-of-order.  Now that you know what the specific use case is, you can forget it because it's no longer important.  We had coded such that the particular piece of database code contained a queue of table operations that needed to be executed.  It was clunky and annoying.  The replacement code is simple and elegant.

The Idea
--------

We're coding this in [Scala][2] and we're using [Future][3]s as the framework for executing the code, which means natural parallelism, of course.  The [Future][3]s run on [ExecutionContext][4]s and that was the obvious fact that I was missing.  The _ExecutionContext_ is the perfect hook for sequencing these, otherwise, parallel operations.

It's the _ExecutionContext_ that is in charge of executing the work, so it makes a natural spot to encapsulate _how_ that work executed.  It was silly to come up with some other way of doing it.

The Code
--------

The beautiful thing is that the code is so damn simple

``` scala
package com.auvik.util

import java.util.concurrent.atomic.AtomicReference

import scala.annotation.tailrec
import scala.concurrent.{ ExecutionContext, Future, Promise }
import scala.util.Try

/**
 * Queues the operations to run in sequence, regardless of the nature of
 * the underlying threadpool.
 */
class SequentialExecutionContext private (ec: ExecutionContext) extends ExecutionContext {
  // Rather than use a traditional "queue", we're going to use a chain of Futures instead.
  // I'm not 100% happy with this, but it does simplify the example tremendously
  val queue = new AtomicReference[Future[Unit]](Future.successful(()))

  /**
   * The execute method is the goal method of the EC.  The implementation ensures that
   * sequentialism is maintained.
   */
  def execute(runnable: Runnable): Unit = {
    // Normally you would see chaining of Futures using a combinator, such as `flatMap`,
    // but that would just create a memory leak.  Every composed Future has a handle to
    // that which it is composed of.  To eliminate that, we compose against another
    // Future that we obtain from this promise.
    val p = Promise[Unit]()

    @tailrec
    def add(): Future[_] = {
      val tail = queue.get()

      // Simple replacement of the head of the queue
      if (!queue.compareAndSet(tail, p.future)) {
        add()
      } else {
        tail
      }
    }

    // Here's the sequentialism.  The 'current' Future must first complete, and then we
    // will execute that which we were given to do in the first place.  The "future"
    // here really does take on the role of a queue
    add().onComplete(_ ⇒ p.complete(Try(runnable.run())))(ec)
  }

  /**
   * yeah, yeah... whatever.
   */
  def reportFailure(cause: Throwable): Unit = ec.reportFailure(cause)
}

object SequentialExecutionContext {
  /**
   * Simple factory method to make construction of the SeqEC simpler
   */
  def apply(ec: ExecutionContext) = new SequentialExecutionContext(ec)
}
```

The code is really, really simple.  In fact, the part that I don't like about it is really the longest part of the code.  This guy just loves to use [Future][3]s like this, and I'm not a huge fan.  The only reasons are just practical, not esoteric:

* It's not obvious what's going on.  You figure out how the queue works only by looking at it really hard.
* It's a memory leak.  There's no way to really tell how much is there, and if something takes forever to complete, you'll just keep queueing things up in ignorance.

I'm a fan of clarity, so the first point is important, but the second point is more important for me.  I really want to know how much is in there.  It's stuff like this that can bring complex systems down without really knowing what's going on, and I very much like to avoid it.

*But the details of the queue aren't important!*  What's important is that it's all done with the _ExecutionContext_.

Usage
-----

Let's say you've got a pre-existing EC out there that is intended for handling blocking JDBC calls.  For a given table, you'd like to ensure that the usage of that pool is sequential.  All you have to do is wrap the pre-existing EC in this new EC.

``` scala
class SomeTable(tableName: String) extends DatabaseTableThingy {
  // this was defined in the DatabaseTableThingy trait
  //    def databaseExecutionContext: ExecutionContext = someCahcedThreadPoolEC()
  
  implicit val mySeqEC = SequentialExecutionContext(databaseExecutionContext)

  def createRow(key: ID, row: Row): Future[Result] = Future {
    // do some database thingy
  }

  def updateRow(key: ID, newRow: Row): Future[Result] = Future {
    // do some database thingy
  }

  // etc...
}
```

The construction of the [Future][3] pulls in the `implicit` EC that we defined earlier. All the code that would normally use the "database" EC, now still uses that same EC but in a sequential manner.

The Bottom Line
---------------

It's all just [ExecutionContext][4]s!  This isn't the first time I've seen someone make a clever use out of the EC, and unfortunately, it's never been me that does it.  One of these days I'll not only recognize the beauty of that abstraction, but I'll actually wield it myself.


  [1]: http://auvik.com "Auvik"
  [2]: http://scala-lang.org "Scala"
  [3]: http://www.scala-lang.org/api/2.11.4/#scala.concurrent.Future "Future"
  [4]: http://www.scala-lang.org/api/2.11.4/#scala.concurrent.ExecutionContext "ExecutionContext"
