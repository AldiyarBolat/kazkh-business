package domain

import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

trait JsonSupport {
  implicit val f1: RootJsonFormat[Movie] = jsonFormat6(Movie)
  implicit val f3: RootJsonFormat[CreateMovie] = jsonFormat1(CreateMovie)
  implicit val f4: RootJsonFormat[GetMovie] = jsonFormat1(GetMovie)
  implicit val f5: RootJsonFormat[UpdateMovie] = jsonFormat1(UpdateMovie)

  implicit val f8: RootJsonFormat[DeleteMovie] = jsonFormat1(DeleteMovie)
  implicit val f9: RootJsonFormat[ResponseMsg] = jsonFormat1(ResponseMsg)
}
