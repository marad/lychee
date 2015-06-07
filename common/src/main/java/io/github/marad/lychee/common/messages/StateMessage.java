package io.github.marad.lychee.common.messages;

import com.google.common.base.Objects;
import io.github.marad.lychee.api.State;

public class StateMessage implements Message {
    private final State state;

    public StateMessage(State state) {
        this.state = state;
    }

    @Override
    public MessageType getType() {
        return MessageType.STATE;
    }

    public State getState() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StateMessage that = (StateMessage) o;
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
