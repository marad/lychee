package io.github.marad.lychee.server.state;

import com.google.common.base.Preconditions;
import io.github.marad.lychee.api.State;
import io.github.marad.lychee.common.StateSerializer;

import java.util.HashMap;
import java.util.Map;

public class StateHistory {
    private long nextVersion = 1;
    private final Map<Long, byte[]> versionMap = new HashMap<Long, byte[]>();

    public long createSnapshot(State state) {
        Preconditions.checkNotNull(state);
        long version = getNextVersion();
        byte[] data = StateSerializer.serialize(state);
        versionMap.put(version, data);
        return version;
    }

    public byte[] getSnapshot(long version) {
        byte[] data = versionMap.get(version);
        if (data == null) {
            throw new StateVersionNotFound(version);
        }
        return data;
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
