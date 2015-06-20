package io.github.marad.lychee.common.sync.messages;

import com.google.common.base.Objects;
import io.github.marad.lychee.common.Message;

import java.util.Arrays;

public class StatePatchMessage implements Message {
    private final long previousStateVersion;
    private final long currentStateVersion;
    private final byte[] patch;

    private StatePatchMessage() {
        previousStateVersion = 0;
        currentStateVersion = 0;
        patch = null;
    }

    public StatePatchMessage(long previousStateVersion, long currentStateVersion, byte[] patch) {
        this.previousStateVersion = previousStateVersion;
        this.currentStateVersion = currentStateVersion;
        this.patch = patch;
    }

    public long getPreviousStateVersion() {
        return previousStateVersion;
    }

    public long getCurrentStateVersion() {
        return currentStateVersion;
    }

    public byte[] getPatch() {
        return patch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatePatchMessage that = (StatePatchMessage) o;
        return Objects.equal(previousStateVersion, that.previousStateVersion) &&
                Objects.equal(currentStateVersion, that.currentStateVersion) &&
                Arrays.equals(patch, that.patch);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(previousStateVersion, currentStateVersion, patch);
    }

    @Override
    public String toString() {
        return String.format("StatePatchMessage{previousStateVersion=%d, currentStateVersion=%d, patch=%s}",
                previousStateVersion, currentStateVersion, Arrays.toString(patch));
    }
}
