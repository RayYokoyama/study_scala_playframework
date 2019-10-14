package controllers

object PostForm {
  import play.api.data._
  import play.api.data.Forms._

  case class Data(text: String)

  val form = Form(
    mapping(
      "text" -> text
    )(Data.apply)(Data.unapply)
  )
}