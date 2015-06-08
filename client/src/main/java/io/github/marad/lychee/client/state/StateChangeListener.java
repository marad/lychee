package io.github.marad.lychee.client.state;

import io.github.marad.lychee.api.State;

@FunctionalInterface
public interface StateChangeListener<S extends State> {
    void stateUpdated(S previousState, S currentState);
}
