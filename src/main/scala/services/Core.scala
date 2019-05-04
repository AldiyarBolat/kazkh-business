package services

import akka.actor.{ Actor, ActorLogging, ActorRef }
import domain._
import repo.MovieRepoImpl

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{ Failure, Success }

class Core(movieRepoImpl: MovieRepoImpl) extends Actor with ActorLogging with JsonSupport {

  override def receive: Receive = {

    case msg @ CreateMovie(movie: Movie) =>
      log.info(s"Create: ${movie.name}")
      val senderRef: ActorRef = sender()

      movieRepoImpl.CreateMovie(msg.movie) onComplete {
        case Success(value) =>
          log.info(s"Success Received")
          senderRef ! ResponseMsg("successfully created")
        case Failure(exception) =>
          log.error(s"Received Error: $exception")
          senderRef ! exception
      }

    case msg: GetMovie =>
      val senderRef = sender()

      movieRepoImpl.GetMovie(msg.id) onComplete {
        case Success(value) =>
          log.info(s" Got Successfully")
          senderRef ! value.head
        case Failure(exception) =>
          log.error(s"Received Error: $exception")
          senderRef ! exception
      }

    case msg @ UpdateMovie(movie: Movie) =>
      val senderRef = sender()

      movieRepoImpl.UpdateMovie(movie) onComplete {
        case Success(value) =>
          log.info(s"Updated Successfully")
          senderRef ! ResponseMsg("successfully updated")
        case Failure(exception) =>
          log.error(s"Received Error: $exception")
          senderRef ! exception
      }

    case msg: DeleteMovie =>
      val senderRef = sender()

      movieRepoImpl.DeleteMovie(msg.movie) onComplete {
        case Success(value) =>
          log.info(s"Deleted Successfully")
          senderRef ! ResponseMsg("successfully deleted")
        case Failure(exception) =>
          log.error(s"Received Error: $exception")
          senderRef ! exception
      }

  }

}

