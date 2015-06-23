package org.derekwyatt

import akka.actor.{ ActorSystem, Props, Actor, ActorRef }
import akka.pattern.ask
import akka.testkit.TestKit
import java.util.concurrent.atomic.AtomicInteger
import org.scalatest.{ BeforeAndAfterAll, Matchers, FlatSpecLike }
import scala.concurrent.duration._
import scala.concurrent.{ Await, Awaitable }
import org.scalatest.concurrent.Eventually

class CancellingSpec extends TestKit(ActorSystem("CancellingSpec"))
      with Eventually
      with FlatSpecLike
      with BeforeAndAfterAll
      with Matchers {

  override def afterAll() {
    system.shutdown()
  }

  implicit class AwaitablePimp[A](pimpee: Awaitable[A]) {
    def w: A = Await.result(pimpee, 5.seconds)
  }

  class Test(concurrentLimit: Int = 1) {
    val api = new TheApi[Int, Int](system, { sleepMillis ⇒ Thread.sleep(sleepMillis); sleepMillis }, concurrentLimit)
  }

  "Work" should "complete when there's nothing else to do" in new Test {
    api.doTheWork(1)(5.seconds).w should be(1)
  }

  it should "timeout appropriately" in new Test {
    a[WorkTimeoutException[_]] should be thrownBy {
      api.doTheWork(5000)(1.millisecond).w
    }
  }

  it should "not do work when possible" in {
    val ai = new AtomicInteger(0)
    val api = new TheApi[Int, Int](system, { sleepMillis ⇒ Thread.sleep(sleepMillis); ai.set(sleepMillis); sleepMillis }, 1)
    val slow = api.doTheWork(500)(5.seconds)
    // Order of delivery of the messages in the two calls to `doTheWork` are not guaranteed, and we
    // want to make sure they queue up in the right order, so we wait until the first one is running
    eventually {
      api.schedulerStats(1.second, system.dispatcher).w should be(SchedulerStats(0, 1))
    }
    val fast = api.doTheWork(1)(1.millisecond)
    a[WorkTimeoutException[_]] should be thrownBy(fast.w)
    slow.w should be(500)
    // Wait until the scheduler is done with we asked it to do
    eventually {
      api.schedulerStats(1.second, system.dispatcher).w should be(SchedulerStats(0, 0))
    }
    ai.get should be(500)
  }
}
