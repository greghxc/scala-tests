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
        case r: Rectangle => JsObject(r.toJson.asJsObject.fields + ("type" -> JsString("rectangle")))
        case c: Circle => JsObject(c.toJson.asJsObject.fields + ("type" -> JsString("circle")))
      }
    }

    override def read(json: JsValue): Shape = {
      json.asJsObject.fields.get("type") match {
        case Some(JsString("circle")) => json.convertTo[Circle]
        case Some(JsString("rectangle")) => json.convertTo[Rectangle]
        case t => throw new RuntimeException(s"Invalid type: $t")
      }
    }
  }
}