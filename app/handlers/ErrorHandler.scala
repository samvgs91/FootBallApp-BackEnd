package handlers

import play.api.libs.json.{JsObject,Json}
import views.html.defaultpages.error

case class Error(error:String,message:String)

object Error {
     implicit val format = Json.format[Error]
}


object ErrorHandler {

   val errorsList = List(
                        Error("NoInput","No Input Data"),
                        Error("InvalidPosition","Invalid Position"),
                        Error("MissingAttribute","Missing Attribute")
                        )
     
   def getErrorObj(errorCode:String) : JsObject = {

            val error = errorsList.find( err => err.error == errorCode)

            error match {
                case Some(value) => Json.obj("error"->value.error,"message"->value.message) 
                case None => Json.obj("error"->"UnIdentifiedError","message"->"Unexpected Error")
            }
       
        }
    }