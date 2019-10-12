package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import akka.util._
import play.api.http._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { 
    val arr:List[List[String]] = List(
      List("Taro", "taro@gmail.com", "888-888"),
      List("Sato", "sato@gmail.com", "999-999"),
      List("Takasi", "takashi@gmail.com", "666-666")
    )
    Ok(views.html.index(
      "this is a controller massage"
    ))
  }
  
  def form() = Action { request =>
    val form:Option[Map[String, Seq[String]]] = request.body.asFormUrlEncoded
    val param:Map[String, Seq[String]] = form.getOrElse(Map())
    val name:String = param.get("name").get(0)
    val password:String = param.get("pass").get(0)
    val arr:List[List[String]] = List(
      List("Taro", "taro@gmail.com", "888-888"),
      List("Sato", "sato@gmail.com", "999-999"),
      List("Takasi", "takashi@gmail.com", "666-666")
    )
    Ok(views.html.index(
      "name:" + name + ", password:" + password

    ))
  }

  def show(id:Int, name:Option[String]) = Action {
    Ok("test:"+id+":"+name.getOrElse("no-name"))
  }
}