package io.github.marad.lychee.server.sync.state.broadcast;

import com.google.common.base.Preconditions;
import com.google.inject.Singleton;

import io.github.marad.lychee.api.State;
import io.github.marad.lychee.common.StateSerializer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class StateHistory {
    private long nextVersion = 0;
    private StateSnapshot lastSnapshot;
    private final Map<Long, StateSnapshot> versionMap = new HashMap<Long, StateSnapshot>();

    public StateHistory(State initialState) {
        storeSnapshot(
            getNextVersion(),
            StateSerializer.serialize(initialState)
        );
    }

    public StateSnapshot createSnapshot(State state) {
        Preconditions.checkNotNull(state);
        byte[] data = StateSerializer.serialize(state);
        if (sameAsPreviousState(data)) {
            return lastSnapshot;
        } else {
            return storeSnapshot(getNextVersion(), data);
        }
    }

    private boolean sameAsPreviousState(byte[] data) {
        return Arrays.equals(lastSnapshot.getData(), data);
    }

    private StateSnapshot storeSnapshot(long version, byte[] data) {
        lastSnapshot = new StateSnapshot(version, data);
        versionMap.put(version, lastSnapshot);
        return lastSnapshot;
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
