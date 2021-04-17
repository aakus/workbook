package io.github.aakus
package libs.zio.manualratelimiter

import zio.{Queue, Schedule, ZIO}
import zio.clock.Clock
import zio.duration.{Duration, durationInt}

object RateLimiter {
  def make(perSecond: Double, buffer: Int): ZIO[Clock, Nothing, RateLimiter] = {
    require(perSecond > 0 && buffer > 0)
    val period: Duration = periodFrom(perSecond)
    for {
      queue <- Queue.bounded[Unit](buffer)
      // keeps draining the queue at the given rate
      _ <- queue.take.repeat(Schedule.fixed(period)).fork
    } yield new RateLimiter(queue)
  }

  private def periodFrom(perSecond: Double) =
    (1.second.toNanos.toDouble / perSecond).toInt.nanos
}

class RateLimiter private (queue: Queue[Unit]) {
  def rateLimit[R, E, A](effect: => ZIO[R, E, A]): ZIO[R, E, A] = {
    queue.offer(()) *> effect
  }
}
