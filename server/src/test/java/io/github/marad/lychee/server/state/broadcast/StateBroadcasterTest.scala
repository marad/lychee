package io.github.marad.lychee.server.state.broadcast

import com.nothome.delta.Delta
import io.github.marad.lychee.common.messages.StatePatchMessage
import io.github.marad.lychee.common.{ExampleState, StateSerializer, UnitTest}
import StateHistory
import io.github.marad.lychee.server.{Client, ClientTracker}
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar

import scala.collection.JavaConversions._

class StateBroadcasterTest extends UnitTest with MockitoSugar {

  val state = new {
    val A = new ExampleState(1)
    val B = new ExampleState(2)
    val C = new ExampleState(3)
  }

  it should "broadcast patches to client states" in {
    Given
    val stateHistory = new StateHistory
    stateHistory.createSnapshot(state.A)
    stateHistory.createSnapshot(state.B)
    val clientMock = mock[Client]
    val clientTrackerStub = mock[ClientTracker]
    when(clientMock.getStateVersion) thenReturn 1
    when(clientTrackerStub.getClients) thenReturn Set(clientMock)
    val stateBroadcaster = new StateBroadcaster(stateHistory, clientTrackerStub)

    When
    stateBroadcaster.broadcast(state.C)

    Then
    verify(clientMock).sendTcpMessage(new StatePatchMessage(1, 3, calcPatch(state.A, state.C)))
  }

  def calcPatch(before: ExampleState, after: ExampleState) = {
    val delta = new Delta
    val beforeData = StateSerializer.serialize(before)
    val afterData = StateSerializer.serialize(after)
    delta.compute(beforeData, afterData)
  }
}
