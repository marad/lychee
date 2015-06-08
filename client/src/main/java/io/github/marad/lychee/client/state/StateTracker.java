package io.github.marad.lychee.client.state;

import io.github.marad.lychee.api.State;

public class StateTracker {
    private final StateChangeNotifier stateChangeNotifier;
    private long stateSeq;
    private State currentState;

    public StateTracker(StateChangeNotifier stateChangeNotifier) {
        this.stateChangeNotifier = stateChangeNotifier;
    }

    public void applyPatch(StatePatch statePatch) {
        State previousState = currentState;
        stateSeq = statePatch.getStateSeq();
        currentState = statePatch.apply(currentState);
        stateChangeNotifier.notifyStateChanged(previousState, currentState);
    }

    public long getStateSeq() {
        return stateSeq;
    }

    public State getCurrentState() {
        return currentState;
    }
}
