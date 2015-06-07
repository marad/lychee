package io.github.marad.lychee.common.net.decoders;

import java.util.HashMap;

public class Decoders {
    private HashMap<Integer, MessageDecoder> decoders;

    Decoders() {
        this.decoders = new HashMap<Integer, MessageDecoder>();
    }

    public void register(MessageDecoder decoder) {
        decoders.put(decoder.getMessageType(), decoder);
    }

    public MessageDecoder findDecoder(int messageType) {
        MessageDecoder decoder = decoders.get(messageType);
        if (decoder != null) {
            return decoder;
        }
        throw new DecoderNotFound(messageType);
    }

    private static Decoders instance = null;
    public static Decoders getInstance() {
        if (instance == null) {
            instance = createInstance();
        }
        return instance;
    }

    private static Decoders createInstance() {
        Decoders instance = new Decoders();
        instance.register(new StatePatchDecoder());
        instance.register(new StringMessageDecoder());
        instance.register(new StateMessageDecoder());
        return instance;
    }
}
