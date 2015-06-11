package io.github.marad.lychee.common.net.decoders;

import com.google.inject.Injector;
import com.google.inject.Key;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;

@Singleton
public class Decoders {
    private HashMap<Integer, MessageDecoder> decoders;

    @Inject
    public Decoders(Injector injector) {
        this.decoders = new HashMap<Integer, MessageDecoder>();
        registerAllMessageDecoders(injector);
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

    private void registerAllMessageDecoders(Injector injector) {
        for(Key<?> key : injector.getAllBindings().keySet()) {
            if (isMessageDecoderImplementation(key)) {
                MessageDecoder encoder = (MessageDecoder) injector.getInstance(key);
                register(encoder);
            }
        }
    }

    private boolean isMessageDecoderImplementation(Key<?> key) {
        return MessageDecoder.class.isAssignableFrom(key.getTypeLiteral().getRawType());
    }
}
