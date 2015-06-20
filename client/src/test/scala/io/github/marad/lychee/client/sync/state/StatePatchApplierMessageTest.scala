package io.github.marad.lychee.client.sync.state

import com.nothome.delta.Delta
import io.github.marad.lychee.api.State
import io.github.marad.lychee.common.{UnitTest, ExampleState, StateSerializer}

class StatePatchApplierMessageTest extends UnitTest {
  val delta = new Delta

  implicit class StateOps(val state: State) {
    def serialized = StateSerializer.serialize(state)
  }

  it should "create new state from apply" in {
    Given
    val stateBefore = new ExampleState(5)
    val stateAfter = new ExampleState(7)
    val patch = delta.compute(stateBefore.serialized, stateAfter.serialized)

    val applier = new StatePatchApplier

    When
    def result = applier.apply(new ExampleState(5), patch)

    Then
    result shouldBe stateAfter
  }
}
