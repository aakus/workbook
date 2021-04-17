package io.github.aakus
package scalatest

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scala.language.reflectiveCalls

import scala.collection.mutable.ListBuffer

class Fixtures extends AnyFlatSpec with Matchers {

  def fixture: Object {
    val builder: StringBuilder
  } =
    new {
      val builder = new StringBuilder("ScalaTest is ")
    }

  behavior of "some test using fixtures"

  it should "use the a fixture" in {
    val fx = fixture
    fx.builder.toString shouldBe "ScalaTest is "
  }

  trait Builder {
    val builder = new StringBuilder("ScalaTest is ")
  }

  trait Buffer {
    val buffer = ListBuffer("ScalaTest", "is")
  }

  // This test needs both the StringBuilder and ListBuffer
  it should "use fixtures as traits" in new Builder with Buffer {
    builder.append("clear!")
    buffer += ("concise!")
    assert(builder.toString === "ScalaTest is clear!")
    assert(buffer === List("ScalaTest", "is", "concise!"))
  }

}
