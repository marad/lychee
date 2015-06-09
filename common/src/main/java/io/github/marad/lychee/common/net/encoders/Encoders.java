package io.github.marad.lychee.common.net.encoders;

import com.google.inject.Binding;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Singleton
public class Encoders {
    private HashMap<Integer, MessageEncoder> encoderMap;

    @Inject
    public Encoders(Injector injector) {
        this.encoderMap = new HashMap<Integer, MessageEncoder>();

        Map<Key<?>, Binding<?>> allBindings = injector.getAllBindings();
        for(Map.Entry<Key<?>, Binding<?>> entry : allBindings.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }


        List<Binding<MessageEncoder>> bindings = injector.findBindingsByType(new TypeLiteral<MessageEncoder>(){});
        for(Binding<MessageEncoder> binding : bindings) {
            register(binding.getProvider().get());
        }
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
}
