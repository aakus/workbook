package io.github.aakus
package scalatest

import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.OptionValues
import org.scalatest.flatspec.AnyFlatSpec

class Matchers
    extends AnyFlatSpec
    with org.scalatest.matchers.must.Matchers
    with TypeCheckedTripleEquals
    with OptionValues {

  /**
   * https://www.scalatest.org/user_guide/using_matchers
   */

  "equals" should "not compile when === other type" in {
    val result = 3
    "result must ===(Some(3))" mustNot compile // because of 'with TypeCheckedTripleEquals'
  }

  "equals" should "work in various ways" in {
    val result = 3
    result must equal(3) // can customize equality
    result must be(3) // cannot customize equality, so fastest to compile
    result must ===(3) // can customize equality and enforce type constraints
    result mustEqual 3 // can customize equality, no parentheses required
    result mustBe 3 // cannot customize equality, so fastest to compile, no parentheses required
  }

  "contains" must "take into account normalised strings if enabled" in {
    import org.scalactic.StringNormalizations._

    (List("Hi", "Di", "Ho") must contain("ho"))(after being lowerCased)
  }

  "contains the same elements as" should "work" in {
    List(1, 2, 2, 3, 3, 3) must contain theSameElementsAs Vector(3, 2, 3, 1, 2,  3)

  }

}
