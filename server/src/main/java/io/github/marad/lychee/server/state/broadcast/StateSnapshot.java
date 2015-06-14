package io.github.marad.lychee.server.state.broadcast;

import com.google.common.base.Objects;

import java.util.Arrays;

public class StateSnapshot {
    private final long version;
    private final byte[] data;

    public StateSnapshot(long version, byte[] data) {
        this.version = version;
        this.data = data;
    }

    public long getVersion() {
        return version;
    }

    public byte[] getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StateSnapshot that = (StateSnapshot) o;
        return Objects.equal(version, that.version) &&
                Objects.equal(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(version, data);
    }

    @Override
    public String toString() {
        return String.format("StateSnapshot{version=%d, data=%s}", version, Arrays.toString(data));
    }
}
