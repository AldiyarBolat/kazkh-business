package repo

import domain.Movie
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

trait MovieRepo {
  def CreateMovie(movie: Movie): Future[Int]
  def GetMovie(id: Int): Future[Vector[Movie]]
  def UpdateMovie(movie: Movie): Future[Int]
  def DeleteMovie(movie: Movie): Future[Int]
}

class MovieRepoImpl(db: Database, movieDAO: MovieDAO) extends MovieRepo {

  override def CreateMovie(movie: Movie): Future[Int] = {
    db.run(movieDAO.createMovie(movie))
  }

  override def GetMovie(id: Int): Future[Vector[Movie]] = {
    (db.run(movieDAO.getMovie(id)))
  }

  override def UpdateMovie(movie: Movie): Future[Int] = {
    db.run(movieDAO.updateMovie(movie))
  }

  override def DeleteMovie(movie: Movie): Future[Int] = {
    db.run(movieDAO.deleteMovie(movie))
  }
}
