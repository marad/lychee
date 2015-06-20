package io.github.marad.lychee.common;

import com.google.inject.AbstractModule;
import io.github.marad.lychee.common.netty.InboundMessageDecoder;
import io.github.marad.lychee.common.netty.OutboundMessageEncoder;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.ReplayingDecoder;

public class LycheeModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(MessageToByteEncoder.class).to(OutboundMessageEncoder.class);
        bind(ReplayingDecoder.class).to(InboundMessageDecoder.class);
    }
}
