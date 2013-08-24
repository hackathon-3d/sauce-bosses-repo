package org.saucebosses.pillalarm

import com.mongodb.casbah.Imports._

object User {
  lazy val coll = Config.mongoClient("pillalarm")("users")

  def apply(uuid: String) = {
    val um = coll.findOne(Map("_id" -> uuid))

    um match {
      case Some(i: DBObject) =>
        new User(
          uuid,
          i.getAs[MongoDBList]("settings").get.asInstanceOf[Seq[Seq[String]]],
          i.getAs[MongoDBList]("scripts").get.toSeq.asInstanceOf[Seq[String]]
        )
      case _ =>
        new User(uuid)
    }
  }

}

class User(val uuid: String, val settings: Seq[Seq[String]] = Seq(), val scripts: Seq[String] = Seq()) {
  def addPrescription(p: Prescription): User = {
    new User(uuid, settings, (scripts ++ Seq(p.id)))
  }

  def save: User = {
    User.coll.update(Map("_id" -> uuid), toMap, true, false)

    this
  }

  def toMap = Map("_id" -> uuid, "settings" -> settings, "scripts" -> scripts)
}