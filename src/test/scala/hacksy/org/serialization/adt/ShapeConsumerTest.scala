package hacksy.org.serialization.adt

import org.scalatest.FunSuite
class ShapeConsumerTest extends FunSuite {
  object TestConsumer extends ShapeConsumer {
    override val ctrl: ShapeController = TestController
  }

  object TestController extends ShapeController {
    override def doTheCircle(c: Circle): String = "did the circle!"
    override def doTheRectangle(r: Rectangle): String = "did the rectangle!"
  }

  test("testConsumeShape does the circle") {
    val shape: Shape = Circle(5)
    assert(TestConsumer.consumeShape(shape) == "did the circle!")
  }

  test("testConsumeShape does the rectangle") {
    val shape: Shape = Rectangle(4,5)
    assert(TestConsumer.consumeShape(shape) == "did the rectangle!")
  }
}
