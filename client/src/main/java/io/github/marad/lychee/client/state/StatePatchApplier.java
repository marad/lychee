package io.github.marad.lychee.client.state;

import io.github.marad.lychee.api.State;
import io.github.marad.lychee.common.Patcher;
import io.github.marad.lychee.common.StateSerializer;

public class StatePatchApplier {
    public static State apply(State oldState, byte[] patch) {
        byte[] oldStateData = StateSerializer.serialize(oldState);
        byte[] patched = Patcher.patch(oldStateData, patch);
        return StateSerializer.deserialize(patched);
    }
}
