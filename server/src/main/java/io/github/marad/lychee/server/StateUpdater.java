package io.github.marad.lychee.server;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github.marad.lychee.api.State;

@Singleton
public class StateUpdater {
    private final LycheeServer lycheeServer;

    @Inject
    public StateUpdater(LycheeServer lycheeServer) {
        this.lycheeServer = lycheeServer;
    }

    public void update(State state) {

    }
}
