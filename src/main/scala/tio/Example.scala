package io.github.aakus
package tio

import tio.Clock.sleep
import tio.Console._

import java.time.Instant
import scala.concurrent.duration.{DurationInt, FiniteDuration}
import scala.util.Random
import scala.util.control.NonFatal

object FailAndRecover extends TIOApp {
  override def run: TIO[Any] = {
    (for {
      _ <- putStrLn("running 1")
      _ <- TIO.fail(new RuntimeException)
      _ <- putStrLn("running 2")
    } yield ()).recover {
      case NonFatal(e) => putStrLn(s"recovered from ${e.getClass.getName}")
    }
  }
}

object Foreach10k extends TIOApp {
  override def run: TIO[Any] =
    TIO.foreach(1 to 10000)(i => TIO.effect(println(i)))
}

object SleepExample extends TIOApp {
  def run: TIO[Unit] = {
    for {
      _ <- TIO.effect(println(
        s"[${Instant.now}] running first effect on ${Thread.currentThread.getName}"))
      _ <- sleep(2.seconds)
      _ <- TIO.effect(println(
        s"[${Instant.now}] running second effect on ${Thread.currentThread.getName}"))
    } yield ()
  }
}

object ForkJoin extends TIOApp {
  def run: TIO[Unit] = {
    for {
      _ <- putStrLn("1")
      fiber1 <- (sleep(2.seconds) *> putStrLn("2") *> TIO.succeed(1)).fork()
      _ <- putStrLn("3")
      i <- fiber1.join()
      _ <- putStrLn(s"fiber1 done: $i")
    } yield ()
  }
}

object ForeachParExample extends TIOApp {
  val numbers = 1 to 10
  val random = new Random()
  // sleep up to 1 second, and return the duration slept
  val sleepRandomTime: TIO[FiniteDuration] = TIO
    .effect(random.nextInt(1000).millis)
    .flatMap(t => sleep(t) *> TIO.succeed(t))

  def run = {
    for {
      _ <- putStrLn(s"[${Instant.now}] foreach:")
      _ <- TIO.foreach(numbers)(i => putStrLn(i.toString))
      _ <- putStrLn(s"[${Instant.now}] foreachPar:")
      _ <- TIO.foreachPar(numbers)(i =>
        sleepRandomTime.flatMap(t => putStrLn(s"$i after $t")))
      _ <- putStrLn(s"[${Instant.now}] foreachPar done")
    } yield ()
  }

}
