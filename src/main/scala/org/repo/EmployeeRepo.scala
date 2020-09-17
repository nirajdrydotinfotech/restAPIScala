package org.repo

import org.data.Employee
import org.db.DbConfig
import org.mongodb.scala.MongoCollection
import org.utils.JsonUtils
import org.mongodb.scala.bson.conversions.Bson
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.model.FindOneAndUpdateOptions
import org.mongodb.scala.model.Updates.{combine, set}

import scala.concurrent.Future

object EmployeeRepo extends JsonUtils{
private val employeeDoc:MongoCollection[Employee]=DbConfig.employees

def createCollection()={
DbConfig.database.createCollection("employee").subscribe(
  (result)=>println(s"$result"),
  e=>println(e.getLocalizedMessage),
()=>println("complete")
)
}


  def insertData(emp:Employee)={
    employeeDoc.insertOne(emp).toFuture()
  }
  def findAll()= employeeDoc.find().toFuture()

  def update(emp:Employee,id:String)=
    employeeDoc
      .findOneAndUpdate(equal("_id", id),
        setBsonValue(emp),
        FindOneAndUpdateOptions().upsert(true)).toFuture()

  def delete(id:String)=
    employeeDoc.deleteOne(equal("_id",id)).toFuture()
  private def setBsonValue(emp:Employee)={
    combine(
      set("name",emp.name),
      set("dateOfBirth",emp.dateOfBirth)
    )
  }
}
