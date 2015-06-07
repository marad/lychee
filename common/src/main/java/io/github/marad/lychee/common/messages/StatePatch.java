package io.github.marad.lychee.common.messages;

import com.google.common.base.Objects;

import java.util.Arrays;

public class StatePatch implements Message {
    private final long stateToPatchSeq;
    private final byte[] patch;

    public StatePatch(long stateToPatchSeq, byte[] patch) {
        this.stateToPatchSeq = stateToPatchSeq;
        this.patch = patch;
    }

    @Override
    public MessageType getType() {
        return MessageType.STATE_PATCH;
    }

    public long getStateToPatchSeq() {
        return stateToPatchSeq;
    }

    public byte[] getPatch() {
        return patch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatePatch that = (StatePatch) o;
        return Objects.equal(stateToPatchSeq, that.stateToPatchSeq) &&
                Arrays.equals(patch, that.patch);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(stateToPatchSeq, patch);
    }

    @Override
    public String toString() {
        return String.format("StatePatch{stateToPatchSeq=%d, patch=%s}", stateToPatchSeq, Arrays.toString(patch));
    }
}
