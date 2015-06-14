package io.github.marad.lychee.server.state

import io.github.marad.lychee.common.{StateSerializer, ExampleState, UnitTest}

class StateHistoryTest extends UnitTest {
  it should "create binary snapshot from state" in {
    Given
    val stateHistory = new StateHistory
    val s1 = new ExampleState(1)
    val s2 = new ExampleState(2)

    When
    val v1 = stateHistory.createSnapshot(s1)
    val v2 = stateHistory.createSnapshot(s2)

    Then
    v1 shouldBe 1
    v2 shouldBe 2
    stateHistory.getSnapshot(v1) shouldBe StateSerializer.serialize(s1)
    stateHistory.getSnapshot(v2) shouldBe StateSerializer.serialize(s2)
  }

  it should "fail when creating snapshot from null" in {
    Given
    val stateHistory = new StateHistory

    Expect
    intercept[NullPointerException] {
      stateHistory.createSnapshot(null)
    }
  }

  it should "fail when asked for not existing version" in {
    Given
    val stateHistory = new StateHistory

    Expect
    intercept[StateVersionNotFound] {
      stateHistory.getSnapshot(123)
    }
  }

  it should "remove given version" in {
    Given
    val stateHistory = new StateHistory
    val s1 = new ExampleState(1)
    val v1 = stateHistory.createSnapshot(s1)

    When
    stateHistory.removeSnapshot(v1)

    Then
    intercept[StateVersionNotFound] {
      stateHistory.getSnapshot(v1)
    }
  }

  it should "not fail when trying to remove not existing version" in {
    Given
    val stateHistory = new StateHistory

    When
    stateHistory.removeSnapshot(1)
  }
}
