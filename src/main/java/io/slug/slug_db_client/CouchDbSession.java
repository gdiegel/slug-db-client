package io.slug.slug_db_client;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.UriBuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings("unused")
public class CouchDbSession {

    private Log LOG = LogFactory.getLog(CouchDbSession.class);

    private final String couchDbProtocol;
    private final String couchDbHost;
    private final int couchDbPort;
    private final String username;
    private final String password;
    private final URI BASE_URI;
    private final AuthenticationType authenticationType;
    private Client client;

    public CouchDbSession(String protocol, String host, int port, String username, String password, AuthenticationType authenticationType) {

        this.couchDbProtocol = protocol;
        this.couchDbHost = host;
        this.couchDbPort = port;
        this.username = username;
        this.password = password;
        this.authenticationType = authenticationType;
        
        StringBuffer sb = new StringBuffer();
        BASE_URI = UriBuilder.fromUri(sb.append(couchDbProtocol).append("://").append(couchDbHost).append(":").append(couchDbPort).toString()).build();
    }

    public CouchDbSession(String protocol, String host, int port, String username, String password) {

        this(protocol, host, port, username, password, AuthenticationType.BASIC);
    }

    protected Client getClient() {
        
        return client;
    }
    
    public CouchDbClient getCouchDbClient(String name) {
        
        client = ClientBuilder.newClient();
        client.register(new BasicAuthenticationFilter(username, password));
        return new CouchDbClient(name, client.target(UriBuilder.fromUri(BASE_URI).path(name).build()));
    }

    public void invalidate() {

        client.close();
    }
}
