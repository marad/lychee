package lychee

import com.google.inject.Guice
import com.nothome.delta.Delta
import io.github.marad.lychee.client.{LycheeClientModule, LycheeClientConfig, LycheeClient}
import io.github.marad.lychee.common.{ExampleState, StateSerializer}
import io.github.marad.lychee.common.messages.StatePatchMessage
import io.github.marad.lychee.server.{LycheeServerModule, LycheeServerConfig, LycheeServer}

class BasicInteractionTest extends IntegrationTest {
  import BasicInteractionTest._

  val initialState = new ExampleState(5)
  val serverConfig = new LycheeServerConfig(8080, initialState)
  val clientConfig = new LycheeClientConfig("localhost", 8080, classOf[ExampleState])

  def setupApp = new {
    val injector = Guice.createInjector(new LycheeServerModule(serverConfig), new LycheeClientModule(clientConfig))
    val server = injector.getInstance(classOf[LycheeServer])
    val client = injector.getInstance(classOf[LycheeClient])
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
    app.closeAndWait
    app.client.getState shouldBe initialState
  }

  "Client" should "handle state patch message broadcasted by server" in {
    Given
    val app = setupApp

    When
    val patch = calcDelta(initialState, new ExampleState(10))
    val future = app.server.sendTCP(new StatePatchMessage(1, currentStateVersion, patch))
    future.await(1000)

    Then
    app.closeAndWait
    app.client.getState shouldBe new ExampleState(10)
  }

}

object BasicInteractionTest {
  implicit class AsyncClosableOps[A <: {def close():Unit; def await(timeout: Long): Unit}](val closable: A) {
    def closeAndWait(millis: Long) = {
      closable.close()
      closable.await(millis)
    }
  }

  def calcDelta(previous: ExampleState, current: ExampleState) = {
    val previousData = StateSerializer.serialize(previous)
    val currentData = StateSerializer.serialize(current)
    val delta = new Delta
    delta.compute(previousData, currentData)
  }
}
