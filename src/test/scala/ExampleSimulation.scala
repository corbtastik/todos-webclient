

class ExampleSimulation extends Simulation {
  val scn = scenario("My scenario").repeat(10000) {
    exec(
      http("Todo(s) Read")
        .get("http://localhost:8009/")
        .check(status.is(200))
    )
  }

  setUp(scn.users(200).ramp(100))
}