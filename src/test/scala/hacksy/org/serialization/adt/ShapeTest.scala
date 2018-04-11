package hacksy.org.serialization.adt

import org.scalatest.{FunSuite, Matchers}
import spray.json._

class ShapeTest extends FunSuite with Matchers {
  val circle: Shape = Circle(5)
  val circleString: String = """{"type":"circle","parameters":{"radius":5}}"""

  val rectangle: Shape = Rectangle(4,5)
  val rectangleString: String = """{"type":"rectangle","parameters":{"height":4,"width":5}}"""

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

  test("missing parameters key") {
    val invalidShapeString: String = """{"type":"rectangle"}"""
    the [RuntimeException] thrownBy {
      invalidShapeString.parseJson.convertTo[Shape]
    } should have message "Invalid Shape"
  }

  test("incorrect type") {
    val invalidShapeString: String = """{"type":"sirkle","parameters":{"radius":5}}"""
    the [RuntimeException] thrownBy {
      invalidShapeString.parseJson.convertTo[Shape]
    } should have message "Invalid Shape"
  }

  test("missing everything") {
    val invalidShapeString: String = "{}"
    the [RuntimeException] thrownBy {
      invalidShapeString.parseJson.convertTo[Shape]
    } should have message "Invalid Shape"
  }
}
