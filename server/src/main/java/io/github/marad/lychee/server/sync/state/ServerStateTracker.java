package io.github.marad.lychee.server.sync.state;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github.marad.lychee.api.State;
import io.github.marad.lychee.common.sync.StateChangeNotifier;
import io.github.marad.lychee.common.sync.StateTracker;
import io.github.marad.lychee.server.annotations.Server;
import io.github.marad.lychee.server.sync.state.broadcast.StateHistory;
import io.github.marad.lychee.server.sync.state.broadcast.StateSnapshot;

@Singleton
public class ServerStateTracker implements StateTracker {
    private final StateChangeNotifier stateChangeNotifier;
    private final StateHistory stateHistory;
    private long stateVersion;
    private State state;

    @Inject
    public ServerStateTracker(
            State initialState,
            @Server StateChangeNotifier stateChangeNotifier,
            StateHistory stateHistory) {
        this.stateChangeNotifier = stateChangeNotifier;
        this.stateHistory = stateHistory;
        this.state = initialState;
        this.stateVersion = 0;
    }

    @Override
    public long getStateVersion() {
        return stateVersion;
    }

    @Override
    public State getState() {
        return state;
    }

    public void update(State state) {
        StateSnapshot stateSnapshot = stateHistory.createSnapshot(state);
        stateVersion = stateSnapshot.getVersion();
        stateChangeNotifier.notifyStateChanged(this.state, state);
        this.state = state;
    }
}
