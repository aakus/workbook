package io.github.aakus
package scalatest

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import org.scalatest.{BeforeAndAfter, Inside, Inspectors, OptionValues}

import scala.collection.mutable.ListBuffer

// ...we recommend you use the FlatSpec style for unit and integration testing
// and the FeatureSpec style for acceptance testing.

class FlatSpec
    extends AnyFlatSpec
    with Matchers
    with OptionValues
    with Inside
    with Inspectors
    with BeforeAndAfter {

  val builder = new StringBuilder
  val buffer = new ListBuffer[String]

  before {
    builder.append("ScalaTest is ")
  }

  after {
    builder.clear()
    buffer.clear()
  }

  "Testing" should "be easy" in {
    builder.append("easy!")
    assert(builder.toString === "ScalaTest is easy!")
    assert(buffer.isEmpty)
    buffer += "sweet"
  }

  behavior of "something"

  it should "be fun" in {
    builder.append("fun!")
    assert(builder.toString === "ScalaTest is fun!")
    assert(buffer.isEmpty)
  }

  it should "return 2" in {
    1 + 1 mustEqual 2
  }
}
