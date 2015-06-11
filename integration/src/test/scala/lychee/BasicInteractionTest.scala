package lychee

import com.google.inject.Guice
import io.github.marad.lychee.client.{LycheeClientModule, LycheeClientConfig, LycheeClient}
import io.github.marad.lychee.server.{LycheeServerModule, LycheeServerConfig, LycheeServer}

class BasicInteractionTest extends IntegrationTest {

  it should "synchronize state when client connects" in {
    Given
    val serverConfig = new LycheeServerConfig(8080, new ExampleState(5))
    val clientConfig = new LycheeClientConfig("localhost", 8080)

    val injector = Guice.createInjector(new LycheeServerModule(serverConfig), new LycheeClientModule(clientConfig))
    val server = injector.getInstance(classOf[LycheeServer])
    val client = injector.getInstance(classOf[LycheeClient])

    When
    server.start()
    client.connect()

    Then
    server.await(1000)
    client.getState shouldBe new ExampleState(5)
  }
}
