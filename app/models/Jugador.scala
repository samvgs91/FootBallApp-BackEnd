package models

import play.api.libs.json.Json

case class Jugador( 
 id_usuario :Int
,id_posicion :Int
,altura_cm :Double
,peso_kg: Double
,pie_dominante: Int)

object Jugador {
   implicit val format = Json.format[Jugador]
}


