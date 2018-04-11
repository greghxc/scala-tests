package hacksy.org.serialization.adt

import spray.json._

sealed trait Shape
case class Rectangle(height: Int, width: Int) extends Shape
case class Circle(radius: Int) extends Shape

object RectangleProtocol extends DefaultJsonProtocol {
  implicit val rectangleFormat = jsonFormat2(Rectangle)
}

object CircleProtocol extends DefaultJsonProtocol {
  implicit val circleFormat = jsonFormat1(Circle)
}

object Shape {
  implicit object ShapeFormat extends RootJsonFormat[Shape] {
    import RectangleProtocol.rectangleFormat
    import CircleProtocol.circleFormat
    override def write(obj: Shape): JsValue = {
      obj match {
        case r: Rectangle =>
          JsObject(Map("type" -> JsString("rectangle"), "parameters" -> JsObject(r.toJson.asJsObject.fields)))
        case c: Circle =>
          JsObject(Map("type" -> JsString("circle"), "parameters" -> JsObject(c.toJson.asJsObject.fields)))
      }
    }

    override def read(json: JsValue): Shape = {
      val jsonAsJsObject = json.asJsObject
      (jsonAsJsObject.fields.get("type"), jsonAsJsObject.fields.get("parameters")) match {
        case (Some(JsString("circle")), Some(p)) => p.convertTo[Circle]
        case (Some(JsString("rectangle")), Some(p)) => p.convertTo[Rectangle]
        case _ => throw new RuntimeException("Invalid Shape")
      }
    }
  }
}