package io.github.marad.lychee.client.sync.state;

import io.github.marad.lychee.api.State;
import io.github.marad.lychee.common.StateSerializer;

import java.io.IOException;

import com.nothome.delta.GDiffPatcher;

public class StatePatchApplier {
    public State apply(State oldState, byte[] patch) {
        byte[] oldStateData = StateSerializer.serialize(oldState);
        byte[] patched = patch(oldStateData, patch);
        return StateSerializer.deserialize(patched);
    }

    public static byte[] patch(byte[] oldState, byte[] patch) {
        try {
            GDiffPatcher patcher = new GDiffPatcher();
            return patcher.patch(oldState, patch);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
