package io.github.marad.lychee.client.state;

import io.github.marad.lychee.api.State;

public class StateTracker<S extends State> {
    private final StateChangeNotifier<S> stateChangeNotifier;
    private long stateSeq;
    private S currentState;

    public StateTracker(StateChangeNotifier<S> stateChangeNotifier) {
        this.stateChangeNotifier = stateChangeNotifier;
    }

    public long getStateSeq() {
        return stateSeq;
    }

    public S getCurrentState() {
        return currentState;
    }

    public void update(long stateSeq, S state) {
        S previousState = currentState;
        this.currentState = state;
        this.stateSeq = stateSeq;
        stateChangeNotifier.notifyStateChanged(previousState, currentState);
    }
}
