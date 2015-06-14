package io.github.marad.lychee.server.state;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nothome.delta.Delta;
import io.github.marad.lychee.api.State;
import io.github.marad.lychee.common.messages.StatePatchMessage;
import io.github.marad.lychee.server.Client;
import io.github.marad.lychee.server.ClientTracker;

import java.io.IOException;

@Singleton
public class StateBroadcaster {
    private final StateHistory stateHistory;
    private final ClientTracker clientTracker;

    @Inject
    public StateBroadcaster(StateHistory stateHistory, ClientTracker clientTracker) {
        this.stateHistory = stateHistory;
        this.clientTracker = clientTracker;
    }

    public void broadcast(State state) {
        Delta delta = new Delta();
        long version = stateHistory.createSnapshot(state);
        byte[] currentState = stateHistory.getSnapshot(version);

        for(Client client : clientTracker.getClients()) {
            long oldVersion = client.getStateVersion();
            byte[] clientState = stateHistory.getSnapshot(oldVersion);
            try {
                byte[] patch = delta.compute(clientState, currentState);
                StatePatchMessage statePatchMessage = new StatePatchMessage(oldVersion, version, patch);
                client.sendTcpMessage(statePatchMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
