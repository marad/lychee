package io.github.marad.lychee.common.messages;

public enum MessageType {
    STATE(0),
    STATE_PATCH(1),
    CONFIRM_STATE(2),
    STRING(3);

    private final short code;

    MessageType(int code) {
        this.code = (short) code;
    }

    public short code() {
        return code;
    }
}
