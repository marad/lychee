package io.github.marad.lychee.common.sync.messages;

import com.google.common.base.Objects;
import io.github.marad.lychee.api.State;
import io.github.marad.lychee.common.Message;

public class InitialStateMessage implements Message {
    private final State state;

    private InitialStateMessage() {
        state = null;
    }

    public InitialStateMessage(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InitialStateMessage that = (InitialStateMessage) o;
        return Objects.equal(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(state);
    }

    @Override
    public String toString() {
        return String.format("StateMessage{state=%s}", state);
    }
}
