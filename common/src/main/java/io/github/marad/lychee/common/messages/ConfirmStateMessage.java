package io.github.marad.lychee.common.messages;

public class ConfirmStateMessage implements Message {
    private final long stateSeq;

    public ConfirmStateMessage(long stateSeq) {
        this.stateSeq = stateSeq;
    }

    public long getStateSeq() {
        return stateSeq;
    }

    @Override
    public MessageType getType() {
        return MessageType.CONFIRM_STATE;
    }

    @Override
    public String toString() {
        return String.format("ConfirmStateMessage{stateSeq=%d}", stateSeq);
    }
}
