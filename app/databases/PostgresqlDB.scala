package databases

import javax.inject.Inject
import javax.inject.Singleton

import play.api.db._
import play.api.mvc._
import play.api.libs.json.{Json,JsObject}

import scala.concurrent.Future

import models.{Usuario,Jugador}

import handlers.ErrorHandler._

@Singleton
class APIDatabase @Inject()(db: Database, dbec: DatabaseExecutionContext){
    def getData(): Future[String] = {
        Future{
            db.withConnection { conn => 

                val stm = conn.createStatement()      
                val query = "SELECT 1 as Value"
                val rs = stm.executeQuery(query)

                rs.next()
                val demo = rs.getString("Value")

                demo 
            }
        }(dbec)
    }

    def registrarUsuario(usu: Usuario): Future[JsObject] = {
      Future{
        db.withConnection{ conn =>
                    try{
                         val stm = conn.createStatement()

                         val nombre_completo = usu.nombre_completo 
                         val email = usu.email
                         val password = usu.password

                         val query = s"INSERT INTO usuario(nombre_completo,email,tipo_login,password) VALUES('$nombre_completo','$email','default','$password') RETURNING id_usuario;"
                         
                         val rs = stm.executeQuery(query)
                         rs.next()
                         Json.obj("id_jugador"->rs.getInt("id_usuario"))
                    } catch {
                         case _: Throwable => getErrorObj("UnKnown")                    
                    } 
                    finally {
                        conn.close()
                    }
        }
      }(dbec)
    }
    //id_usuario:Int,id_posicion:Int,altura_cm:Double,peso_kg: Double,pie_dominante: Int
    def registrarJugador(jg: Jugador): Future[JsObject] = {
      Future{
        db.withConnection{ conn =>
                    try{
                         val stm = conn.createStatement()

                         val id_usuario = jg.id_usuario
                         val id_posicion = jg.id_posicion
                         val altura_cm = jg.altura_cm
                         val peso_kg = jg.peso_kg
                         val pie_dominante = jg.pie_dominante

                         val query = s"INSERT INTO jugador(id_usuario,id_posicion,altura_cm,peso_kg,pie_dominante)VALUES($id_usuario,$id_posicion,$altura_cm,$peso_kg,$pie_dominante) RETURNING id_jugador;"
                         val rs = stm.executeQuery(query)
                         rs.next()
                         Json.obj("id_jugador"->rs.getInt("id_jugador"))
                    } catch {
                         case e: Throwable => 
                          println(e.getMessage())
                          getErrorObj("UnKnown")
                         ///We should log errors somewhere
                    } finally {
                        conn.close()
                    }
        }
      }(dbec)
    }
}