package io.github.marad.lychee.common.sync.messages;

import io.github.marad.lychee.common.Message;

public class ConfirmStateMessage implements Message {
    private final long stateVersion;

    private ConfirmStateMessage() {
        stateVersion = 0;
    }

    public ConfirmStateMessage(long stateVersion) {
        this.stateVersion = stateVersion;
    }

    public long getStateVersion() {
        return stateVersion;
    }

    @Override
    public String toString() {
        return String.format("ConfirmStateMessage{stateVersion=%d}", stateVersion);
    }
}
