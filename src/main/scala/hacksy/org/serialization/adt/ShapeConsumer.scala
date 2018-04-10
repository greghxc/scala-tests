package hacksy.org.serialization.adt

object ShapeConsumer extends ShapeConsumer

trait ShapeConsumer {
  def consumeShape(s: Shape, ctrl: ShapeController): String = {
    s match {
      case c: Circle =>
        ctrl.doTheCircle(c)
      case r: Rectangle =>
        ctrl.doTheRectangle(r)
    }
  }
}

trait ShapeController {
  def doTheCircle(c: Circle): String
  def doTheRectangle(r: Rectangle): String
}
