package hacksy.org.serialization.adt

import org.scalatest.{FunSuite, Matchers}
import spray.json._

class ShapeTest extends FunSuite with Matchers {
  val circle: Shape = Circle(5)
  val rectangle: Shape = Rectangle(4,5)

  test("serializes circle") {
    val circleString: String = """{"type":"circle","parameters":{"radius":5}}"""
    assert(circle.toJson.compactPrint == circleString)
  }

  test("deserializes circle") {
    val circleString: String = """{"type":"circle","parameters":{"radius":5}}"""
    assert(circleString.parseJson.convertTo[Shape] == circle)
  }

  test("serializes rectangle") {
    val rectangleString: String = """{"type":"rectangle","parameters":{"height":4,"width":5}}"""
    assert(rectangle.toJson.compactPrint == rectangleString)
  }

  test("deserializes rectangle") {
    val rectangleString: String = """{"type":"rectangle","parameters":{"height":4,"width":5}}"""
    assert(rectangleString.parseJson.convertTo[Shape] == rectangle)
  }

  test("when missing parameters key") {
    val invalidShapeString: String = """{"type":"rectangle"}"""
    the [DeserializationException] thrownBy {
      invalidShapeString.parseJson.convertTo[Shape]
    } should have message "Invalid Shape"
  }

  test("when incorrect type") {
    val invalidShapeString: String = """{"type":"sirkle","parameters":{"radius":5}}"""
    the [DeserializationException] thrownBy {
      invalidShapeString.parseJson.convertTo[Shape]
    } should have message "Invalid Shape"
  }

  test("when missing everything") {
    val invalidShapeString: String = "{}"
    the [DeserializationException] thrownBy {
      invalidShapeString.parseJson.convertTo[Shape]
    } should have message "Invalid Shape"
  }
}
