package io.github.marad.lychee.server.state;

import com.google.common.base.Objects;
import com.google.inject.Singleton;
import io.github.marad.lychee.api.State;

@Singleton
public class ServerStateTracker {
    private State currentState;

    public ServerStateTracker(State currentState) {
        this.currentState = currentState;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerStateTracker that = (ServerStateTracker) o;
        return Objects.equal(currentState, that.currentState);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(currentState);
    }

    @Override
    public String toString() {
        return String.format("ServerStateTracker{currentState=%s}", currentState);
    }
}
