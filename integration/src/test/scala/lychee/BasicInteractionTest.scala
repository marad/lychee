package lychee

import io.github.marad.lychee.client.LycheeClient
import io.github.marad.lychee.server.LycheeServer

class BasicInteractionTest extends IntegrationTest {

  it should "create the server" in {
    Given
    val server = new LycheeServer(8080)
    val client = new LycheeClient("localhost", 8080)

    When
    server.start(new ExampleState(5))
    client.connect()

    Then
    server.await()
    // TODO: request some resources
  }
}
