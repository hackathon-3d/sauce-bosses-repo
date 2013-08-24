package org.saucebosses.pillalarm

import com.twitter.finatra._
import com.twitter.finatra.ContentType._
import com.twitter.ostrich.stats.Stats

import com.codahale.jerkson.Json

object App {
  class PillAlarmApp extends Controller {

    get("/") { request =>
      render.plain("").toFuture
    }

    get("/user") { request =>
      val uuid = request.params.get("uuid").get

      render.json(User(uuid).save.toMap).toFuture
    }

    post("/user/settings") { request =>
      val uuid = request.params.get("uuid").get
      val u = User(uuid).save

      render.json(u.toMap).toFuture
    }

    get("/prescriptions") { request =>
      val uuid = request.params.get("uuid").get
      val u = User(uuid)

      render.json(Prescription.all(u.scripts).map(_.toMap)).toFuture
    }

    post("/prescriptions") { request =>
      val pattr = Json.parse[Map[String, Any]](request.contentString)
      val p = Prescription.fromMap(pattr).save
      val u = User(p.uuid).addPrescription(p).save

      render.json(p.toMap).toFuture
    }

    get("/prescriptions/:pid") { request =>
      var pid = request.routeParams.get("pid").get

      render.json(Prescription(pid).toMap).toFuture
    }

    get("/search/:q") { request =>
      var q = request.routeParams.get("q").getOrElse("")

      render.json(Prescription.search(q))toFuture
    }

    notFound { request =>
      render.status(404).json(Map("error" -> "This page could not be found.")).toFuture
    }

    error { request =>
      request.error match {
        case Some(e: Exception) =>
          render.status(500).json(Map("error" -> e.getMessage)).toFuture
        case _ =>
          render.status(500).json(Map("error" -> "wtf?")).toFuture
      }
    }
  }

  val app = new PillAlarmApp

  def main(args: Array[String]) = {
    FinatraServer.register(app)
    FinatraServer.start()
  }
}
