package io.github.marad.lychee.common.net.decoders;

import com.google.inject.Binding;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;

@Singleton
public class Decoders {
    private HashMap<Integer, MessageDecoder> decoders;

    @Inject
    public Decoders(Injector injector) {
        this.decoders = new HashMap<Integer, MessageDecoder>();

        List<Binding<MessageDecoder>> bindings = injector.findBindingsByType(new TypeLiteral<MessageDecoder>(){});
        for(Binding<MessageDecoder> binding : bindings) {
            register(binding.getProvider().get());
        }
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
}
