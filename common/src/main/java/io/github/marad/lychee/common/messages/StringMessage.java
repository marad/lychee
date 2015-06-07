package io.github.marad.lychee.common.messages;

import com.google.common.base.Objects;

public class StringMessage implements Message {
    private final String text;

    public StringMessage(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public MessageType getType() {
        return MessageType.STRING;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringMessage that = (StringMessage) o;
        return Objects.equal(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }

    @Override
    public String toString() {
        return String.format("StringMessage{text='%s'}", text);
    }
}
