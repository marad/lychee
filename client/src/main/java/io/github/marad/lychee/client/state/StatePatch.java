package io.github.marad.lychee.client.state;

import io.github.marad.lychee.api.State;
import io.github.marad.lychee.common.Patcher;
import io.github.marad.lychee.common.StateSerializer;

public class StatePatch {
    private final long stateSeq;
    private final byte[] patch;

    public StatePatch(long stateSeq, byte[] patch) {
        this.stateSeq = stateSeq;
        this.patch = patch;
    }

    public State apply(State oldState) {
        byte[] oldStateData = StateSerializer.serialize(oldState);
        byte[] patched = Patcher.patch(oldStateData, patch);
        return StateSerializer.deserialize(patched);
    }

    public long getStateSeq() {
        return stateSeq;
    }
}
