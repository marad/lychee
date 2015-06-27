package io.github.marad.lychee.server.sync.state.broadcast;

import io.github.marad.lychee.api.State;
import io.github.marad.lychee.server.sync.state.ServerStateTracker;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;

import java.util.concurrent.TimeUnit;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class StateBroadcastSchedule {
    private final ServerStateTracker serverStateTracker;
    private final BroadcastTask broadcastTask;
    private Timer timer;
    private Timeout currentTaskTimeout;

    @Inject
    public StateBroadcastSchedule(
            ServerStateTracker serverStateTracker,
            StateBroadcaster stateBroadcaster) {
        this.serverStateTracker = serverStateTracker;
        this.timer = new HashedWheelTimer(defaultTicksPerSecond, TimeUnit.MILLISECONDS);
        this.broadcastTask = new BroadcastTask(stateBroadcaster);
    }

    public void start() {
        currentTaskTimeout = timer.newTimeout(broadcastTask, 0, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        currentTaskTimeout.cancel();
    }

    private class BroadcastTask implements TimerTask {
        private final StateBroadcaster stateBroadcaster;

        public BroadcastTask(StateBroadcaster stateBroadcaster) {
            this.stateBroadcaster = stateBroadcaster;
        }

        @Override
        public void run(Timeout timeout) throws Exception {
            State currentState = serverStateTracker.getState();
            this.stateBroadcaster.broadcast(currentState);
            currentTaskTimeout = timer.newTimeout(this, tickDurationInMillis, TimeUnit.MILLISECONDS);
        }

    }

    private static final long defaultTicksPerSecond = 30;
    private static final long tickDurationInMillis = 1000 / defaultTicksPerSecond;
}
