package io.github.marad.lychee.client.sync;

import com.google.inject.AbstractModule;

public class ClientSyncModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ClientSyncMessageHandler.class).asEagerSingleton();
    }
}
