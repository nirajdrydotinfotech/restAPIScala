package org

import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.stream.ActorMaterializer
import org.actor.EmployeeActor
import org.utils.{JsonUtils, TimeUtils}
import akka.http.scaladsl.server.Directives._
import akka.{NotUsed, util}
import akka.util.{ByteString, Timeout}
import org.data.Employee
import org.domain.EmployeeRequest
import akka.pattern.{Patterns, ask}
import akka.stream.scaladsl.Source
import org.actor.EmployeeActor.{Delete, Save, SearchAll, Update}
import org.service.EmployeeService

import scala.concurrent.duration._
import spray.json._

import scala.concurrent.{Await, Future}

class EmployeeRoute extends JsonUtils{

  implicit val system=ActorSystem("Employee")
  implicit val materializer=ActorMaterializer
  import system.dispatcher
  val actor=system.actorOf(Props[EmployeeActor],"employeeActor")
  val employeeService=new EmployeeService()

  implicit val timeOut=Timeout(5.seconds)


    val getRoute={
    pathPrefix("employee"){

      (pathEndOrSingleSlash & get){
        complete((actor ? SearchAll).mapTo[Seq[Employee]])
     }~
      ( path("update") & put &  parameter("id".as[String])){id=>
        entity(as[EmployeeRequest]){employee=>
          complete((actor ? Update(employee,id)).map(_=>StatusCodes.OK))
        }
      }~
      post{
        entity(as[EmployeeRequest]) { employee =>
          complete((actor ? Save(employee)).map(_ => StatusCodes.OK))
        }
      }~

          (path(Segment) |parameter("id".as[String]) & delete){id=>
            complete((actor ? Delete(id)).map(_=>StatusCodes.OK))

        }



    }
  }
}
