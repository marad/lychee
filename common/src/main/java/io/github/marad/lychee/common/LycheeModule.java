package io.github.marad.lychee.common;

import com.google.inject.AbstractModule;
import io.github.marad.lychee.common.net.InboundMessageDecoder;
import io.github.marad.lychee.common.net.OutboundMessageEncoder;
import io.github.marad.lychee.common.net.decoders.*;
import io.github.marad.lychee.common.net.encoders.*;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.ReplayingDecoder;

public class LycheeModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Encoders.class);
        bind(Decoders.class);
        bind(MessageToByteEncoder.class).to(OutboundMessageEncoder.class);
        bind(ReplayingDecoder.class).to(InboundMessageDecoder.class);
        bindMessageEncoders();
        bindMessageDecoders();
    }

    public void bindMessageEncoders() {
        bind(InitialStateMessageEncoder.class);
        bind(StatePatchEncoder.class);
        bind(ConfirmStateMessageEncoder.class);
    }

    public void bindMessageDecoders() {
        bind(InitialStateMessageDecoder.class);
        bind(StatePatchDecoder.class);
        bind(ConfirmStateMessageDecoder.class);
    }
}
