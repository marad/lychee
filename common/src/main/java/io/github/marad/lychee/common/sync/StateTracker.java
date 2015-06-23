package io.github.marad.lychee.common.sync;

import io.github.marad.lychee.api.State;

public interface StateTracker {
    long getStateVersion();
    State getState();
}
