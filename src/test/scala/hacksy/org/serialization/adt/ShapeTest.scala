package hacksy.org.serialization.adt

import org.scalatest.{FunSuite, Matchers}
import spray.json._

class ShapeTest extends FunSuite with Matchers {
  val circle: Shape = Circle(5)
  val circleString: String = "{\"radius\":5,\"type\":\"circle\"}"
  val rectangle: Shape = Rectangle(4,5)
  val rectangleString: String = "{\"height\":4,\"width\":5,\"type\":\"rectangle\"}"

  test("serializes circle") {
    assert(circle.toJson.compactPrint == circleString)
  }

  test("deserializes circle") {
    assert(circleString.parseJson.convertTo[Shape] == circle)
  }

  test("serializes rectangle") {
    assert(rectangle.toJson.compactPrint == rectangleString)
  }

  test("deserializes rectangle") {
    assert(rectangleString.parseJson.convertTo[Shape] == rectangle)
  }
}
