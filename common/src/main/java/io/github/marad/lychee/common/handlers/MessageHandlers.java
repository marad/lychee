package io.github.marad.lychee.common.handlers;

import java.util.ArrayList;
import java.util.List;

public class MessageHandlers {
    private final List<MessageHandler> handlers = new ArrayList<MessageHandler>();

    public void register(MessageHandler handler) {
        handlers.add(handler);
    }

    public void unregister(MessageHandler handler) {
        handlers.remove(handler);
    }

    public List<MessageHandler> toList() {
        return handlers;
    }
}
