package org.utils
import java.time.LocalDate
import java.time.format.DateTimeFormatter

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import org.data.Employee
import org.domain.EmployeeRequest
import spray.json._
trait JsonUtils  extends DefaultJsonProtocol with SprayJsonSupport{
  implicit object dateFormatter extends JsonFormat[LocalDate] {
    override def write(obj: LocalDate): JsValue = {
    JsString(obj.toString)
  }

  override def read(json: JsValue): LocalDate = {
    LocalDate.parse(json.toString(), DateTimeFormatter.ISO_DATE)
  }
}
implicit val employeeFormat=jsonFormat3(Employee)
  implicit val employeeRequest=jsonFormat2(EmployeeRequest)
}
