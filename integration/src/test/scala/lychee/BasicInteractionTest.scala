package lychee

import com.google.inject.Guice
import io.github.marad.lychee.client.{LycheeClientModule, LycheeClientConfig, LycheeClient}
import io.github.marad.lychee.server.{LycheeServerModule, LycheeServerConfig, LycheeServer}

class BasicInteractionTest extends IntegrationTest {

  it should "synchronize state when client connects" in {
    Given
    val serverConfig = new LycheeServerConfig(8080, new ExampleState(5))
    val serverInjector = Guice.createInjector(new LycheeServerModule(serverConfig))
    val server = serverInjector.getInstance(classOf[LycheeServer])

    val clientConfig = new LycheeClientConfig("localhost", 8080)
    val clientInjector = Guice.createInjector(new LycheeClientModule(clientConfig))
    val client = clientInjector.getInstance(classOf[LycheeClient])

    When
    server.start()
    client.connect()

    Then
    server.await(1000)
    client.getState shouldBe new ExampleState(5)
  }
}
