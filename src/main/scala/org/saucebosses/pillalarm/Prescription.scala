package org.saucebosses.pillalarm

import com.mongodb.casbah.Imports._
import java.util.UUID;

object Prescription {
  lazy val coll = Config.mongoClient("pillalarm")("prescriptions")

  def all(ids: Seq[String]): Seq[Prescription] = {
    ids.map(id => apply(id))
  }

  def apply(id: String): Prescription = {
    val p = coll.findOne(Map("_id" -> id))

    println(p.isInstanceOf[MongoDBObject])
    println(p)

    p match {
      case Some(p: DBObject) =>
        fromMap(p)
      case _ =>
        throw new Exception("Prescription does not exist")
    }
  }

  def fromMap(attr: DBObject): Prescription = {
    val dbobj = wrapDBObj(attr)

    fromMap(Map(
      "_id" -> attr.getAsOrElse[String]("_id", ""),
      "name" -> attr.getAsOrElse[String]("name", "No Name"),
      "drug_id" -> attr.getAsOrElse[String]("drug_id", ""),
      "tags" -> attr.getAs[MongoDBList]("tags").get.asInstanceOf[Seq[String]],
      "schedule" -> wrapDBObj(attr.getAs[DBObject]("schedule").getOrElse(MongoDBObject())).toMap[String, Any],
      "notes" -> attr.getAs[MongoDBList]("notes").get.asInstanceOf[Seq[String]],
      "qty" -> attr.getOrElse("qty", 0).asInstanceOf[Int],
      "uuid" -> attr.getOrElse("uuid", "").asInstanceOf[String],
      "num_refills" -> attr.getOrElse("num_refills", 0).asInstanceOf[Int]
    ))
  }

  def fromMap(attr: Map[String, Any]): Prescription = {
    new Prescription(
      attr.getOrElse("_id", "").asInstanceOf[String],
      attr.getOrElse("name", "No Name").asInstanceOf[String],
      attr.getOrElse("drug_id", "").asInstanceOf[String],
      attr.getOrElse("tags", Seq[String]()).asInstanceOf[Seq[String]],
      attr.getOrElse("schedule", Map[String, Any]()).asInstanceOf[Map[String, Any]],
      attr.getOrElse("qty", 0).asInstanceOf[Int],
      attr.getOrElse("notes", Seq[String]()).asInstanceOf[Seq[String]],
      attr.getOrElse("uuid", "").asInstanceOf[String],
      attr.getOrElse("num_refills", 0).asInstanceOf[Int]
    )
  }

  def search(name: String): Map[String, String] = {
    val k = Data.data.find(_("name").toLowerCase == name.toLowerCase)

    if (k.isDefined)
      k.get
    else
      throw new Exception("No prescription found with that name: " + name)
  }
}


class Prescription(
    val id: String,
    val name: String,
    val drug_id: String,
    val tags: Seq[String],
    val schedule: Map[String, Any],
    val qty: Int,
    val notes: Seq[String],
    val uuid: String,
    val num_refills: Int)
{

  def save = {
    val p = toMap

    Prescription.coll.save(p)
    Prescription.fromMap(p)
  }

  def toMap = Map(
    "_id" -> (if (id equals "") UUID.randomUUID.toString else id),
    "name" -> name,
    "tags" -> tags,
    "schedule" -> schedule,
    "qty" -> qty,
    "notes" -> notes,
    "uuid" -> uuid,
    "num_refills" -> num_refills)

}