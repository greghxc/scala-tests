package hacksy.org.serialization.adt

trait ShapeConsumer {
  val ctrl: ShapeController
  def consumeShape(s: Shape): String = {
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
