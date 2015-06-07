package io.github.marad.lychee.common.net.decoders;

public class DecoderNotFound extends RuntimeException {
    private int messageType;

    public DecoderNotFound(int messageType) {
        super(String.format("Decoder for message type %d not found.", messageType));
        this.messageType = messageType;
    }

    public int getMessageType() {
        return messageType;
    }
}
