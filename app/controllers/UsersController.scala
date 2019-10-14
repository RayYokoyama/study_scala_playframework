package controllers

import java.sql._
import javax.inject._
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.db._
import UserForm._
import java.lang.ProcessBuilder.Redirect
import java.sql.Date
import java.sql.Timestamp
import java.util.Calendar

@Singleton
class UsersController @Inject()(
  db: Database, cc: MessagesControllerComponents) 
    extends MessagesAbstractController(cc) {

  def index() = Action {
    var message = "<ul>"    
    try {
      db.withConnection { conn => 
        val client = conn.createStatement
        val res = client.executeQuery("SELECT * from test_users")
        println(res)
        while (res.next) {
          message += "<li>" + res.getString("id") + ":" + res.getString("name") + "</li>"
        }
        message += "</ul>"
      }
    } catch {
      case e:SQLException => 
        println(e)
        message  ="<li>no record...</li></ul>"
    }
    Ok(views.html.index(
      message
    ))
  } 

  def signup() = Action {implicit request =>
    Ok(views.html.signup(
      "フォームを記入してください。",
      form
    ))
  }

  def create() = Action { implicit request =>
    val formdata = form.bindFromRequest
    val data = formdata.get
    val uuid: String = java.util.UUID.randomUUID.toString

    try{
      db.withConnection { conn => 
        val post = conn.prepareStatement(
          "insert into test_users values (?, ?, default)")
        post.setString(1, uuid)
        post.setString(2, data.name)
        post.executeUpdate
      }
    } catch {
      case e: SQLException =>
        Ok(views.html.signup(
          "フォームに入力してください",
          form
        ))
    }
    Redirect(routes.UsersController.index)
  }
}
