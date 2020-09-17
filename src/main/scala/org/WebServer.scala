package org

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.http.scaladsl.server.Directives._

import scala.io.StdIn


object WebServer extends App {

  implicit val system=ActorSystem("web-app")
  implicit val materializer=ActorMaterializer
  import system.dispatcher

private val routeConfig=new EmployeeRoute()
  val route={
pathPrefix("api"){
  routeConfig.getRoute
}
  }

  val server=Http().newServerAt("localhost",8080).bind(route)
println("server started")
StdIn.readLine()
server.flatMap(_.unbind()).onComplete(_=>system.terminate())
}
