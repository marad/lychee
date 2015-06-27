package io.github.marad.lychee.server.sync.state.broadcast

import io.github.marad.lychee.common.{ExampleState, StateSerializer, UnitTest}

class StateHistoryTest extends UnitTest {
  it should "create binary snapshot from state" in {
    Given
    val s1 = new ExampleState(1)
    val s2 = new ExampleState(2)
    val stateHistory = new StateHistory(s1)

    When
    val v2 = stateHistory.createSnapshot(s2).getVersion

    Then
    v2 shouldBe 1
    stateHistory.getSnapshot(0).getData shouldBe StateSerializer.serialize(s1)
    stateHistory.getSnapshot(v2).getData shouldBe StateSerializer.serialize(s2)
  }

  it should "not create new snapshot if state hasn't changed" in {
    Given
    val state = new ExampleState(2)
    val stateHistory = new StateHistory(new ExampleState(1))
    val expectedSnapshot = stateHistory.createSnapshot(state)

    When
    val snapshot = stateHistory.createSnapshot(state)

    Then
    snapshot shouldBe expectedSnapshot
  }

  it should "fail when creating snapshot from null" in {
    Given
    val stateHistory = new StateHistory(new ExampleState(1))

    Expect
    intercept[NullPointerException] {
      stateHistory.createSnapshot(null)
    }
  }

  it should "fail when asked for not existing version" in {
    Given
    val stateHistory = new StateHistory(new ExampleState(1))

    Expect
    intercept[StateVersionNotFound] {
      stateHistory.getSnapshot(123)
    }
  }

  it should "remove given version" in {
    Given
    val s1 = new ExampleState(1)
    val stateHistory = new StateHistory(s1)
    val v1 = stateHistory.createSnapshot(s1).getVersion

    When
    stateHistory.removeSnapshot(v1)

    Then
    intercept[StateVersionNotFound] {
      stateHistory.getSnapshot(v1)
    }
  }

  it should "not fail when trying to remove not existing version" in {
    Given
    val stateHistory = new StateHistory(new ExampleState(1))

    When
    stateHistory.removeSnapshot(100)
  }

}
