package org.service

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source
import org.data.Employee
import org.domain.EmployeeRequest
import org.repo.EmployeeRepo

import scala.concurrent.Future

class EmployeeService {

implicit val system=ActorSystem("Employeee")
  implicit val materializer=ActorMaterializer

def saveEmployeeData= (employeeRequest:EmployeeRequest) => {
val employeeDoc:Employee=employeeMapperWithNewId(employeeRequest)
EmployeeRepo.insertData(employeeDoc)
}
def findAll={
  EmployeeRepo.findAll()
}

  def update(employeeRequest: EmployeeRequest,id:String)={
    val employeeDoc=employeeMapperWithNewId(employeeRequest)
EmployeeRepo.update(emp = employeeDoc,id)
  }
  def delete(id:String)=EmployeeRepo.delete(id)

  private def employeeMapperWithNewId(employee:EmployeeRequest)={
Employee(name=employee.name,dateOfBirth = LocalDate.parse(employee.dateOfBirth, DateTimeFormatter.ISO_DATE),
_id=UUID.randomUUID.toString)
  }
}
