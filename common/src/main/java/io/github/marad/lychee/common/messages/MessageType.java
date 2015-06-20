package io.github.marad.lychee.common.messages;

public enum MessageType {
    STATE(0),
    STATE_PATCH(StatePatchMessage.class.getCanonicalName().hashCode()),
    CONFIRM_STATE(2);

    private final int code;

    MessageType(int code) {
        this.code =  code;
    }

    public int code() {
        return code;
    }
}
