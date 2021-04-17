package io.github.aakus
package tio

object Console {
  def putStrLn(str: => String): TIO[Unit] = TIO.effect(println(str))
}
