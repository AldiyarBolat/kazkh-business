package domain

case class Movie(id: Int, name: String, rating: String,
  director: String, company: String,
  country: String)

case class CreateMovie(movie: Movie)

case class GetMovie(id: Int)

case class UpdateMovie(movie: Movie)

case class DeleteMovie(movie: Movie)

case class ResponseMsg(msg: String)

