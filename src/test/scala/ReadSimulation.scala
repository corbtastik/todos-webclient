import java.util.UUID

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class ReadSimulation extends Simulation {

  val baseUrl = System.getProperty("TARGET_URL")
  val sim_users = System.getProperty("SIM_USERS").toInt

  val httpConf = http.baseURL(baseUrl)

  val headers = Map("Accept" -> """application/json""")

  val readScenario = repeat(30) {
    exec(http("read-simulation")
      .get("/")
      .header("Content-Type", "application/json" )
    ).pause(1 second, 2 seconds)
  }

  val scn = scenario("Todo(s) Read Simulation")
    .exec(readScenario)


  val scn = scenario("My scenario").repeat(10000) {
    exec(
      http("Todo(s) Read")
        .get("http://localhost:8009/")
        .check(status.is(200))
    )
  }

  setUp(scn.users(sim_users).ramp(30))
}