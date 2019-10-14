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
class PostsController @Inject()(
  db: Database, cc: MessagesControllerComponents)
    extends MessagesAbstractController(cc) {

  def index() = Action {
    var message = "<ul>"
    try {
      db.withConnection { conn =>
        val client = conn.createStatement
        val res = client.executeQuery("SELECT * from posts")
        println(res)
        val uuid = res.getString("id")
        val title = res.getString("title")
        while (res.next) {
          message += s"<li><a href='posts/'$uuid>$title</a></li>"
        }
        message += "</ul>"
      }
    } catch {
      case e:SQLException => {
        println(e)
        message = "<li>no record...</li></ul>"
      }
    }
    Ok(views.html.posts(
      message
    ))
  }

  def show(id: Int) = Action {
    Ok(views.html.show(
      "testdayo"
    ))
  }
}