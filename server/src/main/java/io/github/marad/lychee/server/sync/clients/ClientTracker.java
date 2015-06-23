package io.github.marad.lychee.server.sync.clients;

import com.google.inject.Singleton;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.HashSet;
import java.util.Set;

@Singleton
public class ClientTracker {
    private final Set<Client> clients = new HashSet<Client>();

    public void add(final Client client) {
        clients.add(client);
        client.getTcpChannel()
                .closeFuture()
                .addListener(new RemoveClient(client));
    }

    public Set<Client> getClients() {
        return clients;
    }

    private class RemoveClient implements GenericFutureListener<Future<? super Void>> {
        private final Client client;

        private RemoveClient(Client client) {
            this.client = client;
        }

        @Override
        public void operationComplete(Future future) throws Exception {
            clients.remove(client);
        }
    }
}
