import akka.actor.{ ActorSystem, Props }
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.directives.PathDirectives.path
import akka.pattern.ask
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import domain._
import org.slf4j.LoggerFactory
import repo.{ MovieDAO, MovieRepoImpl }
import services.Core
import slick.jdbc.PostgresProfile
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.duration._

object Boot extends App with JsonSupport {

  implicit val config = ConfigFactory.load()
  val log = LoggerFactory.getLogger("Boot")
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher
  implicit val timeout = Timeout(30.seconds)

  val db: PostgresProfile.backend.Database = Database.forConfig("dbConfig")
  val movieDAO = new MovieDAO()
  val movieRepository = new MovieRepoImpl(db, movieDAO)
  val core = system.actorOf(Props(new Core(movieRepository)))

  val route = {
    pathPrefix("movie") {
      path("create") {
        post {
          entity(as[Movie]) { movie =>
            complete {
              (core ? domain.CreateMovie(movie)).mapTo[ResponseMsg]
            }
          }
        }
      } ~
        path("get") {
          get {
            entity(as[GetMovie]) { getMovie =>
              complete {
                (core ? getMovie).mapTo[Movie]
              }
            }
          }
        } ~ path("update") {
          put {
            entity(as[Movie]) { movie =>
              complete {
                (core ? domain.UpdateMovie(movie)).mapTo[ResponseMsg]
              }
            }
          }
        } ~ path("delete") {
          delete {
            entity(as[Movie]) { movie =>
              complete {
                (core ? domain.DeleteMovie(movie)).mapTo[ResponseMsg]
              }
            }
          }
        }
    }
  }
  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
  log.info("Listening on port 8080...")

}
