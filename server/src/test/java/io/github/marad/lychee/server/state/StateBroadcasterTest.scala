package io.github.marad.lychee.server.state

import io.github.marad.lychee.common.{ExampleState, UnitTest}
import io.github.marad.lychee.server.ClientTracker
import org.scalamock.scalatest.MockFactory

class StateBroadcasterTest extends UnitTest with MockFactory {

  it should "broadcast patches to client states" in {
    Given
    val stateHistory = new StateHistory
    stateHistory.createSnapshot(new ExampleState(1))
    stateHistory.createSnapshot(new ExampleState(2))
    val clientTrackerStub = stub[ClientTracker]
    val stateBroadcaster = new StateBroadcaster(stateHistory, clientTrackerStub)

    When
    stateBroadcaster.broadcast(new ExampleState(3))
  }
}
