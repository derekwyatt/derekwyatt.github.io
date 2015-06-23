package org.derekwyatt

import akka.actor._
import akka.pattern.{ ask, pipe }
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.{ ExecutionContext, Future, Promise }
import scala.collection.immutable.Queue

class TheApi[Work, Result](factory: ActorRefFactory, thunk: Work ⇒ Result, concurrentLimit: Int = 5) {
  val workScheduler = factory.actorOf(WorkScheduler.props(concurrentLimit, thunk))

  def schedulerStats(implicit to: Timeout, ec: ExecutionContext): Future[SchedulerStats] =
    workScheduler.ask(GetSchedulerStats()).mapTo[SchedulerStats]

  def doTheWork(work: Work)(implicit timeout: Timeout): Future[Result] = {
    val promise = Promise[Result]()
    val ref = factory.actorOf(WorkWaiter.props(work, workScheduler, promise, timeout))
    promise.future
  }
}

case class GetSchedulerStats()
case class SchedulerStats(queuedWork: Int, runningWork: Int)
case class QueueWork[Work](work: Work, recipient: ActorRef)
case class WorkComplete[Result](result: Result)
case class AreYouStillInterested()
case class IAmStillInterested()
case class IAmNoLongerInerested()

case class WorkTimeoutException[Work](timeout: Timeout, work: Work) extends RuntimeException(s"Timed out ($timeout) while waiting for work to complete")

object WorkWaiter {
  def props[Work, Result](work: Work, workScheduler: ActorRef, workResult: Promise[Result], timeout: Timeout): Props =
    Props(new WorkWaiter(work, workScheduler, workResult, timeout))
}

class WorkWaiter[Work, Result](work: Work, workScheduler: ActorRef, workResult: Promise[Result], timeout: Timeout) extends Actor {

  // Set the timeout to ensure that we can (potentially) fail the promise appropriately
  // Schedule the work with the work scheduler
  override def preStart(): Unit = {
    super.preStart()
    context.setReceiveTimeout(timeout.duration)
    workScheduler ! QueueWork(work, self)
  }

  def interested: Receive = {
    // We were interested, but didn't get the answer in time, so we're not
    // interested any more.
    case ReceiveTimeout ⇒
      // We don't need another ReceiveTimeout
      context.setReceiveTimeout(Duration.Undefined)
      // Fail the result, cuz it's just too late
      workResult.failure(WorkTimeoutException(timeout, work))
      context.become(notInterested)

    // Great! We got the result in time so we can produce the happy ending
    case WorkComplete(result: Result @unchecked) ⇒
      workResult.success(result)
      context.stop(self)

    // Since we're in the `interested` state, we're definitely interested
    case AreYouStillInterested() ⇒
      sender ! IAmStillInterested()
  }

  def notInterested: Receive = {
    // Too bad. We were interested, but the work didn't finish in time so
    // the only reasonable thing we can do is discard this
    case _: WorkComplete[_] ⇒

    // Nope, we're not interesed so don't bother starting
    case AreYouStillInterested() ⇒
      sender ! IAmNoLongerInerested()
      context.stop(self)
  }

  def receive = interested
}

case class Job[Work](work: Work, recipient: ActorRef)

object Worker {
  case class Proceed()
  case class ForgetIt()

  def props[Work, Result](job: Job[Work], thunk: Work ⇒ Result): Props =
    Props(new Worker(job, thunk))
}

class Worker[Work, Result](job: Job[Work], thunk: Work ⇒ Result) extends Actor {
  import context.dispatcher // implicit EC
  import Worker._

  implicit val _timeout = Timeout(5.seconds)

  // First we need to understand whether or not we should even do this
  job.recipient.ask(AreYouStillInterested()).map {
    case IAmStillInterested() ⇒
      Proceed()
    case _ ⇒
      ForgetIt()
  } recover {
    case t: Throwable ⇒
      ForgetIt()
  } pipeTo self

  final def receive = {
    // The recipient is still interested, so let's do it
    case Proceed() ⇒
      val result = thunk(job.work)
      job.recipient ! WorkComplete(result)
      context.stop(self)

    // For whatever reason, we're going to be doing this work for no reason,
    // so let's not do it.
    case ForgetIt() ⇒
      context.stop(self)
  }
}

object WorkScheduler {
  def props[Work, Result](concurrentLimit: Int, thunk: Work ⇒ Result): Props =
    Props(new WorkScheduler(concurrentLimit, thunk))
}

class WorkScheduler[Work, Result](concurrentLimit: Int, thunk: Work ⇒ Result) extends Actor {

  // No big deal, just start the worker
  def spawnWork(job: Job[Work]): ActorRef = context.watch(context.actorOf(Worker.props(job, thunk)))

  // Rather than have internal `var`s, I've opted for closures
  def working(queue: Queue[Job[Work]], runningCount: Int): Receive = {
    // We haven't reached our maximum concurrency limit yet so we don't need to queue the work
    case QueueWork(work: Work @unchecked, recipient) if runningCount < concurrentLimit ⇒
      spawnWork(Job(work, recipient))
      context.become(working(queue, runningCount + 1))

    // We're already running at our maximum so we queue the work instead
    case QueueWork(work: Work @unchecked, recipient) ⇒
      context.become(working(queue.enqueue(Job(work, recipient)), runningCount))

    // One of our workers has completed, so we can (potentially) get something else running
    case Terminated(ref) ⇒
      queue.dequeueOption match {
        case Some((job, q)) ⇒
          spawnWork(job)
          context.become(working(q, runningCount))
        case None ⇒
          context.become(working(queue, runningCount - 1))
      }

    // Really just used by tests to ensure things are ordered
    case GetSchedulerStats() ⇒
      sender ! SchedulerStats(queue.size, runningCount)
  }

  def receive = working(Queue.empty, 0)
}
