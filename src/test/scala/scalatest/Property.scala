package io.github.aakus
package scalatest

import com.danielasfregola.randomdatagenerator.magnolia.RandomDataGenerator
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Gen._
import org.scalacheck.{Arbitrary, Gen}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

/** https://www.scalatest.org/user_guide/property_based_testing
  * https://github.com/typelevel/scalacheck/blob/master/doc/UserGuide.md
  * https://github.com/DanielaSfregola/random-data-generator-magnolia
  *
  * https://github.com/typelevel/scalacheck/blob/master/doc/UserGuide.md#stateful-testing
  */

class Property
    extends AnyFlatSpec
    with ScalaCheckPropertyChecks
    with Matchers
    with RandomDataGenerator {

  "property test" should "work" in {
    forAll { (n: Int, d: Int) =>
      whenever(n < d) {
        n shouldBe <(d)
      }
    }
  }
  case class Example(text: String, n: Int)

  val randomExample: Example = random[Example]

  implicit val exampleGenerator: Gen[Example] = implicitly[Arbitrary[Example]].arbitrary

  "automatic generators" should "also be used" in {
    forAll { (e: Example) =>
      whenever(e.text.nonEmpty) {
        e.text shouldNot be(empty)
      }
    }
  }

  sealed abstract class Tree
  case class Node(left: Tree, right: Tree, v: Int) extends Tree
  case object Leaf extends Tree

  val genLeaf = const(Leaf)

  val genNode = for {
    v <- arbitrary[Int]
    left <- genTree
    right <- genTree
  } yield Node(left, right, v)

  def genTree: Gen[Tree] = Gen.oneOf(genLeaf, lzy(genNode))

}
