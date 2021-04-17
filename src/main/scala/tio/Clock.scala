package io.github.aakus
package tio

import java.util.{Timer, TimerTask}
import scala.concurrent.duration.Duration
import scala.util.Success

object Clock {
  private val timer = new Timer("tio-timer", /*isDaemon*/ true)

  def sleep[A](duration: Duration): TIO[Unit] =
    TIO.effectAsync[Unit] { onComplete =>
      timer.schedule(new TimerTask {
        override def run(): Unit = onComplete(Success(()))
      }, duration.toMillis)
    }

}
