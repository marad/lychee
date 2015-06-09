package io.github.marad.lychee.common;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import io.github.marad.lychee.common.net.InboundMessageDecoder;
import io.github.marad.lychee.common.net.OutboundMessageEncoder;
import io.github.marad.lychee.common.net.decoders.Decoders;
import io.github.marad.lychee.common.net.decoders.StateMessageDecoder;
import io.github.marad.lychee.common.net.decoders.StatePatchDecoder;
import io.github.marad.lychee.common.net.decoders.StringMessageDecoder;
import io.github.marad.lychee.common.net.encoders.Encoders;
import io.github.marad.lychee.common.net.encoders.StateMessageEncoder;
import io.github.marad.lychee.common.net.encoders.StatePatchEncoder;
import io.github.marad.lychee.common.net.encoders.StringMessageEncoder;
import io.github.marad.lychee.common.netty.BaseChannelInitializer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.ReplayingDecoder;

public abstract class LycheeModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Encoders.class);
        bind(Decoders.class);
        bind(MessageToByteEncoder.class).to(OutboundMessageEncoder.class);
        bind(ReplayingDecoder.class).to(InboundMessageDecoder.class);
        bind(new TypeLiteral<ChannelInitializer<SocketChannel>>(){}).to(BaseChannelInitializer.class);
        bindMessageEncoders();
        bindMessageDecoders();
    }

    public void bindMessageEncoders() {
        bind(StateMessageEncoder.class);
        bind(StatePatchEncoder.class);
        bind(StringMessageEncoder.class);
    }

    public void bindMessageDecoders() {
        bind(StateMessageDecoder.class);
        bind(StatePatchDecoder.class);
        bind(StringMessageDecoder.class);
    }
}
