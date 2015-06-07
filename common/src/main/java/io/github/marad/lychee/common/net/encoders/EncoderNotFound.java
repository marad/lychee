package io.github.marad.lychee.common.net.encoders;

public class EncoderNotFound extends RuntimeException {
    private int messageType;

    public EncoderNotFound(int messageType) {
        super(String.format("Encoder for message type %d not found.", messageType));
        this.messageType = messageType;
    }

    public int getMessageType() {
        return messageType;
    }
}

