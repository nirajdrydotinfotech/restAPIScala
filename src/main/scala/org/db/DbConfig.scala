package org.db

import ch.rasc.bsoncodec.math.BigDecimalStringCodec
import ch.rasc.bsoncodec.time.LocalDateTimeDateCodec
import org.bson.codecs.configuration.{CodecRegistries, CodecRegistry}
import org.data.Employee
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.MongoClient.DEFAULT_CODEC_REGISTRY
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import com.mongodb.{MongoCredential, ServerAddress}
import com.mongodb.MongoCredential.createCredential
import org.mongodb.scala.{MongoClient, MongoClientSettings, MongoCollection, MongoDatabase, ServerAddress}

import scala.jdk.CollectionConverters._


object DbConfig {

  import org.bson.codecs.configuration.CodecRegistries
/*

  private val javaCodecs = CodecRegistries.fromCodecs(
    new LocalDateTimeDateCodec(),
    new BigDecimalStringCodec()
  )
val user:String="root"
  val password:Array[Char]="example".toCharArray
  val source:String="admin"
  private val credentials:MongoCredential= createCredential(user, source, password)


  val codecRegistry = fromRegistries(fromProviders(classOf[Employee]), DEFAULT_CODEC_REGISTRY,javaCodecs)

  val setting:MongoClientSettings=MongoClientSettings.builder()
    .applyToClusterSettings(b=>b.hosts(List(new ServerAddress("localhost")).asJava))
    .credential(credentials)
    .codecRegistry(codecRegistry)
    .build()

  val client:MongoClient=MongoClient(setting)
  val database:MongoDatabase=client.getDatabase("video")
  val employees: MongoCollection[Employee] = database.getCollection("employee")
*/
val codecRegistry = fromRegistries(fromProviders(classOf[Employee]), DEFAULT_CODEC_REGISTRY)

  val client = MongoClient(s"mongodb://localhost:27017")
  //val db = client.getDatabase("test")
  //val numbersColl = db.getCollection("employeeData").withCodecRegistry(codecRegistry)
  val database: MongoDatabase = client.getDatabase("video").withCodecRegistry(codecRegistry)
  val employees: MongoCollection[Employee] = database.getCollection("employeeData")

}
