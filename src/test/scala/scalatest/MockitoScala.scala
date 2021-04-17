package io.github.aakus
package scalatest

import org.mockito.scalatest.IdiomaticMockito
import org.scalatest.flatspec.AnyFlatSpec

class MockitoScala extends AnyFlatSpec with IdiomaticMockito {

  /** https://github.com/mockito/mockito-scala
    */

  trait Foo {
    def bar: String
    def bar(v: Int): Int
  }

  val aMock = mock[Foo]

  aMock.bar returns "mocked!"
  aMock.bar returns "mocked!" andThen "mocked again!"
  aMock.bar shouldCall realMethod
  aMock.bar throws new IllegalArgumentException
  aMock.bar answers "mocked!"
  aMock.bar(*) answers ((i: Int) => i * 10)

  "mocked!" willBe returned by aMock.bar
  "mocked!" willBe answered by aMock.bar
  ((i: Int) => i * 10) willBe answered by aMock.bar(*)
  theRealMethod willBe called by aMock.bar
  new IllegalArgumentException willBe thrown by aMock.bar

  aMock.bar.doesNothing()

  aMock wasNever called
  aMock.bar was called
  aMock.bar(*) was called

  aMock.bar wasCalled onlyHere
  aMock.bar wasNever called

  aMock.bar wasCalled twice
  aMock.bar wasCalled 2.times

  aMock.bar wasCalled sixTimes
  aMock.bar wasCalled 6.times

  aMock.bar wasCalled atLeastSixTimes
  aMock.bar wasCalled atLeast(sixTimes)
  aMock.bar wasCalled atLeast(6.times)

  aMock.bar wasCalled atMostSixTimes
  aMock.bar wasCalled atMost(sixTimes)
  aMock.bar wasCalled atMost(6.times)

  import scala.concurrent.duration._

  aMock.bar wasCalled (atLeastSixTimes within 2.seconds)

  aMock wasNever calledAgain

  /*
  InOrder(mock1, mock2) { implicit order =>
    mock2.someMethod() was called
    mock1.anotherMethod() was called
  }
   */
}
