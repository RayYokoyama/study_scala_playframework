package controllers

import java.sql._
import javax.inject._
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.db._
import PostForm._
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
        while (res.next) {
          val uuid = res.getString("id")
          val title = res.getString("text")
          message += s"<li><a href='posts/$uuid'>$title</a></li>"
        }
        message += "</ul>"
      }
    } catch {
      case e:SQLException => 
        println(e)
        message = "<li>no record...</li></ul>"
    }
    Ok(views.html.posts(
      message
    ))
  }

  def show(id: String) = Action {
    var message = ""
    try {
      db.withConnection { conn =>
        val client = conn.createStatement
        val res = client.executeQuery(s"SELECT * from posts where id = '${id}'")
        println(res)
        while (res.next) {
          val text = res.getString("text")
          message += s"<p>${text}</p>"
        }
      }
    } catch {
      case e:SQLException =>
        println(e)
        message = "<p>no record...</p>"
    }
    Ok(views.html.show(
      message
    ))
  }

  def add() = Action {implicit request => 
      println(form)
      Ok(views.html.add(
        "フォームを入力してください。",
        form
      ))
  }

  def create() = Action {implicit request =>
    val formdata = form.bindFromRequest
    val data = formdata.get
    val uuid: String = java.util.UUID.randomUUID.toString    
    // Todo
    // user_idをAPIから入力できるようにする
    val user_id: String = "ssssssssssssssssss"

    try{
      db.withConnection { conn =>
        val user = conn.createStatement.executeQuery(
          s"SELECT * FROM test_users where id = '${user_id}'"
        )
        if(!user.last()){ 
          throw new Exception("ユーザーがいません") 
        }
        while(user.next){
          println(user.getString("id"))
          val user_uuid = user.getString("id")
          println(user_uuid)
          val post = conn.prepareStatement(
            "insert into posts values (?, ?, ?, default, default)")
          post.setString(1, uuid)
          post.setString(2, user_uuid)
          post.setString(3, data.text)
          post.executeUpdate
        }
        Redirect(routes.PostsController.show(uuid: String))
      }
    } catch {
      case e: SQLException =>
        println(e)
        Ok(views.html.add(
          "フォームに入力してください",
          form
        ))
      case e: Exception =>
        Ok(views.html.add(
          e.getMessage,
          form
        ))
    }
  }
}
