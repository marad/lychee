package io.github.marad.lychee.common.messages;

public class ConfirmStateMessage implements Message {
    private final long stateSeq;

    private  ConfirmStateMessage() {
        stateSeq = 0;
    }

    public ConfirmStateMessage(long stateSeq) {
        this.stateSeq = stateSeq;
    }

    public long getStateSeq() {
        return stateSeq;
    }

    @Override
    public String toString() {
        return String.format("ConfirmStateMessage{stateSeq=%d}", stateSeq);
    }
}
