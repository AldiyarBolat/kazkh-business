package repo

import domain.{ JsonSupport, Movie }
import slick.dbio.DBIO
import slick.jdbc.GetResult
import slick.jdbc.PostgresProfile.api._

class MovieDAO extends JsonSupport {
  def createMovie(movieMsg: Movie): DBIO[Int] = {
    sqlu"""INSERT INTO movietable(movie_id,movie_name, movie_rating, movie_director, movie_company, movie_country) VALUES(${movieMsg.id}, ${movieMsg.name}, ${movieMsg.rating}, ${movieMsg.director}, ${movieMsg.company}, ${movieMsg.country})"""
  }

  implicit val getResult = GetResult(r => Movie(r.nextInt(), r.nextString(), r.nextString(), r.nextString(), r.nextString(), r.nextString()))
  def getMovie(id: Int): DBIO[Vector[Movie]] = {
    sql"""SELECT * FROM movieTable WHERE movie_id = ${id}""".as[Movie]
  }

  def updateMovie(movieMsg: Movie): DBIO[Int] = {
    sqlu"""UPDATE movieTable SET movie_name = ${movieMsg.name} WHERE movie_id = ${movieMsg.id}"""
  }

  def deleteMovie(movieMsg: Movie): DBIO[Int] = {
    sqlu"""DELETE FROM movieTable WHERE movie_id = ${movieMsg.id}"""
  }
}
