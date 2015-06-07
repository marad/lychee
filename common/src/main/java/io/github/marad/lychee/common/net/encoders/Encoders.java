package io.github.marad.lychee.common.net.encoders;

import java.util.HashMap;

public class Encoders {
    private HashMap<Integer, MessageEncoder> encoderMap;

    Encoders() {
        this.encoderMap = new HashMap<Integer, MessageEncoder>();
    }

    public  void register(MessageEncoder encoder) {
        encoderMap.put(encoder.getMessageType(), encoder);
    }

    public MessageEncoder findEncoder(int messageType) {
        MessageEncoder encoder = encoderMap.get(messageType);
        if (encoder != null) {
            return encoder;
        }
        throw new EncoderNotFound(messageType);
    }

    private static Encoders instance = null;
    public static Encoders getInstance() {
        if (instance == null) {
            instance = createInstance();
        }
        return instance;
    }

    private static Encoders createInstance() {
        Encoders instance = new Encoders();
        instance.register(new StatePatchEncoder());
        instance.register(new StringMessageEncoder());
        instance.register(new StateMessageEncoder());
        return instance;
    }
}
