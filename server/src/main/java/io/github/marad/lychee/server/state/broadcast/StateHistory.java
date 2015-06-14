package io.github.marad.lychee.server.state.broadcast;

import com.google.common.base.Preconditions;
import io.github.marad.lychee.api.State;
import io.github.marad.lychee.common.StateSerializer;

import java.util.HashMap;
import java.util.Map;

public class StateHistory {
    private long nextVersion = 1;
    private final Map<Long, StateSnapshot> versionMap = new HashMap<Long, StateSnapshot>();

    public StateSnapshot createSnapshot(State state) {
        Preconditions.checkNotNull(state);
        long version = getNextVersion();
        byte[] data = StateSerializer.serialize(state);
        StateSnapshot stateSnapshot = new StateSnapshot(version, data);
        versionMap.put(version, stateSnapshot);
        return stateSnapshot;
    }

    public StateSnapshot getSnapshot(long version) {
        StateSnapshot stateSnapshot = versionMap.get(version);
        if (stateSnapshot == null) {
            throw new StateVersionNotFound(version);
        }
        return stateSnapshot;
    }

    public void removeSnapshot(long version) {
        versionMap.remove(version);
    }

    private long getNextVersion() {
        long version = nextVersion;
        nextVersion += 1;
        return version;
    }
}
