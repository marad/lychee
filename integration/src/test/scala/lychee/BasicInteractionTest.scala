package lychee

import com.google.inject.Guice
import io.github.marad.lychee.client.sync.state.ClientStateTracker
import io.github.marad.lychee.client.{LycheeClient, LycheeClientConfig, LycheeClientModule}
import io.github.marad.lychee.common.ExampleState
import io.github.marad.lychee.server.sync.clients.ClientTracker
import io.github.marad.lychee.server.sync.state.ServerStateTracker
import io.github.marad.lychee.server.sync.state.broadcast.StateHistory
import io.github.marad.lychee.server.{LycheeServer, LycheeServerConfig, LycheeServerModule}

class BasicInteractionTest extends IntegrationTest {
  import BasicInteractionTest._

  val initialState = new ExampleState(5)
  val serverConfig = new LycheeServerConfig(8080, initialState)
  val clientConfig = new LycheeClientConfig("localhost", 8080)

  def setupApp = new {
    val injector = Guice.createInjector(new LycheeServerModule(serverConfig), new LycheeClientModule(clientConfig))
    val server = injector.getInstance(classOf[LycheeServer])
    val client = injector.getInstance(classOf[LycheeClient])
    val clientStateTracker = injector.getInstance(classOf[ClientStateTracker])
    val serverStateTracker = injector.getInstance(classOf[ServerStateTracker])
    server.start()
    client.connect()
    
    def closeAndWait = {
      server.closeAndWait(1000)
      client.closeAndWait(1000)
    }
  }

  it should "synchronize state when client connects" in {
    Given
    val app = setupApp

    When
    Thread.sleep(100)

    Then
    app.clientStateTracker.getState shouldBe initialState
    app.closeAndWait
  }

  it should "broadcast state updates on server and handle changes on client" in {
    Given
    val app = setupApp
    val clientTracker = app.injector.getInstance(classOf[ClientTracker])

    When
    Thread.sleep(100)
    app.serverStateTracker.update(new ExampleState(20))
    Thread.sleep(100)
    app.serverStateTracker.update(new ExampleState(30))
    Thread.sleep(100)

    Then
    clientTracker.getClients.iterator.next.getStateVersion shouldBe 2
    app.clientStateTracker.getState shouldBe new ExampleState(30)
    app.closeAndWait
  }
}

object BasicInteractionTest {
  implicit class AsyncClosableOps[A <: {def close():Unit; def await(timeout: Long): Unit}](val closable: A) {
    def closeAndWait(millis: Long) = {
      closable.close()
      closable.await(millis)
    }
  }
}
