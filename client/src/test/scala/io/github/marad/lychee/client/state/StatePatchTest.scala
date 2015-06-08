package io.github.marad.lychee.client.state

import com.nothome.delta.Delta
import io.github.marad.lychee.api.State
import io.github.marad.lychee.common.{StateSerializer, UnitTest}

class StatePatchTest extends UnitTest {
  val delta = new Delta

  implicit class StateOps(val state: State) {
    def serialized = StateSerializer.serialize(state)
  }

  it should "create new state from apply" in {
    Given
    val stateBefore = new ExampleState(5)
    val stateAfter = new ExampleState(7)
    val patch = delta.compute(stateBefore.serialized, stateAfter.serialized)
    val patchUnderTest = new StatePatch(1, patch)

    When
    def result = patchUnderTest.apply(new ExampleState(5))

    Then
    result shouldBe stateAfter
  }
}
