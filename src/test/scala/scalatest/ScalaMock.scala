package io.github.aakus
package scalatest

import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec

class ScalaMock extends AnyFlatSpec with MockFactory {

  /** https://www.scalatest.org/user_guide/testing_with_mock_objects
    * http://scalamock.org/user-guide/integration/
    */

  val m = mockFunction[Int, String]
  m.expects(42).returning("Forty Two").once()
}
