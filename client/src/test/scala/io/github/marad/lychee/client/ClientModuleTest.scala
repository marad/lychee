package io.github.marad.lychee.client

import com.google.inject.Guice
import io.github.marad.lychee.common.{ExampleState, UnitTest}

class ClientModuleTest extends UnitTest {
  it should "start the module" in {
    Given
    val config = new LycheeClientConfig("localhost", 8080, classOf[ExampleState])
    val injector = Guice.createInjector(new LycheeClientModule(config))

    When
    val instance = injector.getInstance(classOf[LycheeClient])
    instance.connect()

    Then
    instance should not be null
  }
}
