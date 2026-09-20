package gnoolson.saturday.broker.application;

import gnoolson.saturday.broker.model.ClientSession;
import gnoolson.saturday.common.time.TimeProvider;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class ClientSessionStorage {

    private final List<ClientSession> clientSessions = new ArrayList<>();
    private final TimeProvider timeProvider;

    /*
    *
    *
    * */
    public synchronized void add(String clientId, String username) {
        clientSessions.add(new ClientSession(clientId,username, timeProvider.now().getValue()));
    }

    public synchronized void clear() {
        clientSessions.clear();
    }

    public synchronized Collection<ClientSession> getAll() {
        return clientSessions;
    }

    public synchronized void remove(String clientId) {
        clientSessions.removeIf((clientSession) -> {
            return clientSession.getClientName().equals(clientId);
        });
    }

}
