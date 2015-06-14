package io.github.marad.lychee.server.state.broadcast;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nothome.delta.Delta;
import io.github.marad.lychee.api.State;
import io.github.marad.lychee.common.messages.StatePatchMessage;
import io.github.marad.lychee.server.Client;
import io.github.marad.lychee.server.ClientTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        StateSnapshot currentState = stateHistory.createSnapshot(state);

        for(Client client : clientTracker.getClients()) {
            long oldVersion = client.getStateVersion();
            StateSnapshot clientStateSnapshot = stateHistory.getSnapshot(oldVersion);
            try {
                byte[] patch = delta.compute(clientStateSnapshot.getData(), currentState.getData());
                StatePatchMessage statePatchMessage = new StatePatchMessage(oldVersion, currentState.getVersion(), patch);
                client.sendTcpMessage(statePatchMessage);
            } catch (IOException e) {
                logger.error("Error while sending patch to client", e);
            }
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(StateBroadcaster.class);
}
