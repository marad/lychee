package io.github.marad.lychee.client.sync.state;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github.marad.lychee.api.State;
import io.github.marad.lychee.client.annotations.Client;
import io.github.marad.lychee.common.sync.StateChangeNotifier;
import io.github.marad.lychee.common.sync.StateTracker;

@Singleton
public class ClientStateTracker implements StateTracker {
    private final StateChangeNotifier stateChangeNotifier;
    private long stateVersion;
    private State state;

    @Inject
    public ClientStateTracker(@Client StateChangeNotifier stateChangeNotifier) {
        this.stateChangeNotifier = stateChangeNotifier;
    }

    @Override
    public long getStateVersion() {
        return stateVersion;
    }

    @Override
    public State getState() {
        return state;
    }

    public void update(long stateVersion, State state) {
        State previousState = this.state;
        this.state = state;
        this.stateVersion = stateVersion;
        stateChangeNotifier.notifyStateChanged(previousState, state);
    }
}
