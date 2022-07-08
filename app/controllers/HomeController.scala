package controllers

import javax.inject._
import play.api._
import play.api.mvc._

import databases.APIDatabase


import scala.concurrent.ExecutionContext
import scala.concurrent.Future

import play.api.libs.json.Json
import play.api.libs.json.{JsError, JsSuccess, Json, Reads}
import play.api.libs.json.JsValue
import play.api.libs.json.JsObject

import models.{Usuario,Jugador}
import play.api.libs.json.JsResult
import akka.actor

import handlers.ErrorHandler._


/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents,db: APIDatabase)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok("Building Back-End App")
  }


  def ping() = Action.async { implicit request =>
     val demo = db.getData()
     demo map {
      r => Ok(r)
     }
  }

   def withRequestBody[A](f: A => Future[Result])(implicit request: Request[AnyContent], reads: Reads[A]): Future[Result] = {
    request.body.asJson match { 
      case Some(body) => 
                           Json.fromJson[A](body) match {
                                 case JsSuccess(a, path) => f(a)
                                 case e@JsError(_) => {
                                       getObjError("NoInput") map (Ok(_))
                                 }
                           }
      case None => getObjError("NoInput") map (Ok(_)) 
    }
   }

  def registrarUsuario() = Action.async { implicit request =>
      withRequestBody[Usuario] { 
         usu =>  
         db.registrarUsuario(usu).map(Ok(_)) 
      }
  } 

  def registrarJugador() = Action.async { implicit request =>
      withRequestBody[Jugador] { 
         jg =>  
         db.registrarJugador(jg).map(Ok(_)) 
      }
  } 

  def getObjError(errorCode:String) : Future[JsObject] = {
     Future {
         getErrorObj(errorCode)
      }
  }
}