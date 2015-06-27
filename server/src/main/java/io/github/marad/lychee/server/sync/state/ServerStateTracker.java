package io.github.marad.lychee.server.sync.state;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github.marad.lychee.api.State;
import io.github.marad.lychee.common.sync.StateChangeNotifier;
import io.github.marad.lychee.common.sync.StateTracker;
import io.github.marad.lychee.server.annotations.Server;

@Singleton
public class ServerStateTracker implements StateTracker {
    private final StateChangeNotifier stateChangeNotifier;
    private State state;

    @Inject
    public ServerStateTracker(State initialState,
            @Server StateChangeNotifier stateChangeNotifier) {
        this.stateChangeNotifier = stateChangeNotifier;
        this.state = initialState;
    }

    @Override
    public State getState() {
        return state;
    }

    public void update(State state) {
        stateChangeNotifier.notifyStateChanged(this.state, state);
        this.state = state;
    }
}
