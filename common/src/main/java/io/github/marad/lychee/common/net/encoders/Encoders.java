package io.github.marad.lychee.common.net.encoders;

import com.google.inject.Binding;
import com.google.inject.Injector;
import com.google.inject.Key;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;

@Singleton
public class Encoders {
    private HashMap<Integer, MessageEncoder> encoderMap;

    @Inject
    public Encoders(Injector injector) {
        this.encoderMap = new HashMap<Integer, MessageEncoder>();
        registerAllMessageDecoders(injector);
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

    private void registerAllMessageDecoders(Injector injector) {
        for(Key<?> key : injector.getAllBindings().keySet()) {
            if (isMessageEncoderImplementation(key)) {
                MessageEncoder encoder = (MessageEncoder) injector.getInstance(key);
                register(encoder);
            }
        }
    }
    private boolean isMessageEncoderImplementation(Key<?> key) {
        return MessageEncoder.class.isAssignableFrom(key.getTypeLiteral().getRawType());
    }
}
