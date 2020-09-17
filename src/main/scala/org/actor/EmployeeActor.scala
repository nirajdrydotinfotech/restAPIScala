package org.actor

import akka.actor.{Actor, ActorLogging}
import akka.pattern.pipe
import org.actor.EmployeeActor.{Delete, Save, SearchAll, Update}
import org.data.Employee
import org.domain.EmployeeRequest
import org.service.EmployeeService

import scala.concurrent.ExecutionContext.Implicits.global

object EmployeeActor {

  case class Save(emp: EmployeeRequest)

  case object SearchAll

  case class Update(emp: EmployeeRequest, id: String)

  case class Delete(id: String)

}

class
EmployeeActor extends Actor with ActorLogging {
  private val employeeService: EmployeeService = new EmployeeService()

  override def receive: Receive = {
    case Save(emp) =>
      log.info(s"recevied msg saved with employee :$emp")
      sender() ! employeeService.saveEmployeeData(emp)

    case SearchAll =>
      log.info("received msg find ALL")
      employeeService.findAll.pipeTo(sender())
    case Update(emp, id) =>
      log.info(s"received update msg for id $id and employee $emp")
      sender() ! employeeService.update(emp, id)
    case Delete(id) =>
      log.info(s"received msg for deleting employee of id $id")
      sender() ! employeeService.delete(id)
    case _ =>
      log.debug("Unhandled msg")


  }
}
