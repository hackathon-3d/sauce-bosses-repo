package org.saucebosses.pillalarm

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import com.twitter.finatra.test._
import org.saucebosses.pillalarm._

class AppSpec extends SpecHelper {

  val app = new App.PillAlarmApp


  "GET /notfound" should "respond 404" in {
    get("/notfound")
    response.body   should equal ("not found yo")
    response.code   should equal (404)
  }

}
