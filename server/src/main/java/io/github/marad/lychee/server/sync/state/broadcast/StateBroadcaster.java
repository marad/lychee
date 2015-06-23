package io.github.marad.lychee.server.sync.state.broadcast;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nothome.delta.Delta;
import io.github.marad.lychee.api.State;
import io.github.marad.lychee.common.sync.StateChangeListener;
import io.github.marad.lychee.common.sync.StateChangeNotifier;
import io.github.marad.lychee.common.sync.messages.StatePatchMessage;
import io.github.marad.lychee.server.annotations.Server;
import io.github.marad.lychee.server.sync.clients.Client;
import io.github.marad.lychee.server.sync.clients.ClientTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Singleton
public class StateBroadcaster implements StateChangeListener {
    private final StateHistory stateHistory;
    private final ClientTracker clientTracker;

    @Inject
    public StateBroadcaster(
            @Server StateChangeNotifier stateChangeNotifier,
            StateHistory stateHistory,
            ClientTracker clientTracker) {
        this.stateHistory = stateHistory;
        this.clientTracker = clientTracker;
        stateChangeNotifier.addListener(this);
    }

    @Override
    public void stateUpdated(State previousState, State currentState) {
        broadcast(currentState);
    }

    private void broadcast(State state) {
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
