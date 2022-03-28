package io.github.aakus
package libs.cats

object Cats {
  import cats._, cats.data._, cats.implicits._

  def f[A,B](le: List[Either[A,B]]): Either[A,List[B]] = {
    le.sequence
  }

  def main(args: Array[String]): Unit = {
    val inputWithError: List[Either[String,Int]] = List(Right(1), Right(2), Left("3"), Left("4"))
    println(f(inputWithError))    //Left(3)

    val inputWithoutError: List[Either[String,Int]] = List(Right(1), Right(2), Right(3))
    println(f(inputWithoutError)) //Right(List(1, 2, 3))

  }



}
