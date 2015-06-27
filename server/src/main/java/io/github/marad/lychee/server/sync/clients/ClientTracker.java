package io.github.marad.lychee.server.sync.clients;

import com.google.inject.Singleton;
import io.netty.channel.Channel;
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

    public void removeByChannel(final Channel channel) {
        Client client = findByChannel(channel);
        clients.remove(client);
    }

    public Set<Client> getClients() {
        return clients;
    }

    public Client findByChannel(Channel channel) {
        for(Client client : clients) {
            if (client.ownsChannel(channel)) {
                return client;
            }
        }
        return null;
    }

    private class RemoveClient implements GenericFutureListener<Future<? super Void>> {
        private final Client client;

        private RemoveClient(Client client) {
            this.client = client;
        }

        @Override
        public void operationComplete(Future<? super Void> future) throws Exception {
            clients.remove(client);
        }
    }
}
