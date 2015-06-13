package io.github.marad.lychee.client.state;

import com.google.inject.Inject;
import io.github.marad.lychee.api.State;
import io.github.marad.lychee.common.Patcher;
import io.github.marad.lychee.common.StateSerializer;
import io.github.marad.lychee.common.net.StateInfo;

public class StatePatchApplier {
    private final StateInfo stateInfo;

    @Inject
    public StatePatchApplier(StateInfo stateInfo) {
        this.stateInfo = stateInfo;
    }

    public State apply(State oldState, byte[] patch) {
        byte[] oldStateData = StateSerializer.serialize(oldState);
        byte[] patched = Patcher.patch(oldStateData, patch);
        return StateSerializer.deserialize(patched, stateInfo.getStateType());
    }
}
