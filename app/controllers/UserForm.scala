package controllers

object UserForm {
  import play.api.data._
  import play.api.data.Forms._

  case class Data(name: String)

  val form = Form(
    mapping(
      "name" -> text
    )(Data.apply)(Data.unapply)
  )
}
