package io.github.aakus
package scalatest

import org.scalatest.matchers.must.Matchers
import org.scalatest.prop.{TableDrivenPropertyChecks, TableFor1}
import org.scalatest.propspec.AnyPropSpec

import scala.collection.immutable._

class TableDrivenProperty
    extends AnyPropSpec
    with TableDrivenPropertyChecks
    with Matchers {

  val examples: TableFor1[Set[Int]] =
    Table("ints", HashSet.empty[Int], TreeSet.empty[Int], BitSet.empty)

  property("an empty Set should have size 0") {
    forAll(examples) { set =>
      set.size must be(0)
    }
  }

  property(
    "invoking head on an empty set should produce NoSuchElementException"
  ) {
    forAll(examples) { set =>
      a[NoSuchElementException] must be thrownBy { set.head }
    }
  }
}
