package io.slug.slug_db_client;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings("unused")
public class CouchDbClient {

    Log LOG = LogFactory.getLog(CouchDbClient.class);

    private final String couchDbName;

    private CouchDbSession couchDbSession;
    private WebTarget couchDbTarget;

    private static final String VIEW = "/_view/";
    private static final String DESIGN = "_design/";
    private static final String UPDATE = "/_update/";

    
    private UriBuilder uriBuilder;

    public CouchDbClient(String name, CouchDbSession session) {

        this.couchDbName = name;
        this.couchDbSession = session;
    }

    public CouchDbClient(String name, WebTarget target) {

        this.couchDbName = name;
        this.couchDbTarget = target;
    }

    public void printDbInfo() {

        Response res = couchDbTarget.request().get();
        LOG.info(res.readEntity(String.class));
        res.close();
    }
}