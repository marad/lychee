package io.github.marad.lychee.common.sync;

import io.github.marad.lychee.api.State;

@FunctionalInterface
public interface StateChangeListener {
    void stateUpdated(State previousState, State currentState);
}
