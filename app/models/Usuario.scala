package models

import play.api.libs.json.Json

case class Usuario(
 nombre_completo :String
,email :String
,password: String
)

object Usuario {
   implicit val format = Json.format[Usuario]
}



