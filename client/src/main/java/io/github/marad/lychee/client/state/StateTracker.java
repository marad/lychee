package io.github.marad.lychee.client.state;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github.marad.lychee.api.State;

@Singleton
public class StateTracker {
    private final StateChangeNotifier stateChangeNotifier;
    private long stateSeq;
    private State currentState;

    @Inject
    public StateTracker(StateChangeNotifier stateChangeNotifier) {
        this.stateChangeNotifier = stateChangeNotifier;
    }

    public long getStateSeq() {
        return stateSeq;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void update(long stateSeq, State state) {
        State previousState = currentState;
        this.currentState = state;
        this.stateSeq = stateSeq;
        stateChangeNotifier.notifyStateChanged(previousState, currentState);
    }
}
