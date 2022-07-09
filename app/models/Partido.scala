package models

import play.api.libs.json.Json
import java.time._

case class PartidoParser( 
 id_organizador :Int
,id_centro_deportivo :Int
,id_status_partido : Int
,fecha_partido : String 
,id_tipo_partido : Int
,gasto_total_soles :Double
)

object PartidoParser {
   implicit val format = Json.format[PartidoParser]
}

