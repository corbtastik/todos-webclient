import java.util.UUID

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class UpdateSimulation extends Simulation {

  val baseUrl = System.getProperty("TARGET_URL")
  val sim_users = System.getProperty("SIM_USERS").toInt

  val httpConf = http.baseURL(baseUrl)

  val headers = Map("Accept" -> """application/json""")

  val test1 = repeat(30) {
    exec(http("webclient-test")
      .post("/")
      .header("Content-Type", "application/json" )
      .body(StringBody(
        s"""
           | {
           |   "title": "howdy todo ${UUID.randomUUID().toString}"
           | }
        """.stripMargin)))
      .pause(1 second, 2 seconds)
  }

  val scn = scenario("WebClient Test Page")
    .exec(test1)

  setUp(scn.inject(rampUsers(sim_users).over(30 seconds)).protocols(httpConf))
}