package io.github.marad.lychee.common.messages;

public interface Message {
    // TODO: moze message type moglby byc robiony z klasy message: InitialStateMessage.class.hashCode()?
    public MessageType getType();
}
