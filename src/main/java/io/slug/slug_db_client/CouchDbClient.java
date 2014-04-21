package io.slug.slug_db_client;

import java.util.logging.Logger;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.codehaus.jackson.JsonNode;

@SuppressWarnings("unused")
public class CouchDbClient {

    private final static Logger LOG = Logger.getLogger(CouchDbClient.class.getName());

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

    public JsonNode getDoc(String id) {

        LOG.info("_id: \"" + id + "\"");
        Response res = couchDbTarget.path(id).request().get();
        res.bufferEntity();
        LOG.info(res.readEntity(String.class));
        if (HttpStatus.SC_OK != res.getStatus())
            throw new RuntimeException(res.getStatusInfo().getReasonPhrase());
        JsonNode j = res.readEntity(JsonNode.class);
        res.close();

        return j;
    }

    public String putDoc(JsonNode doc) throws CouchDbDocumentCreationException {

        String id = doc.get("_id").getTextValue();
        LOG.info("_id: \"" + id + "\"");
        Entity<JsonNode> entity = Entity.entity(doc, MediaType.APPLICATION_JSON_TYPE);
        Response res = couchDbTarget.path(id).request().put(entity);
        res.bufferEntity();
        LOG.info("Created document: " + res.readEntity(String.class));
        String rev = res.readEntity(JsonNode.class).get("rev").getTextValue();
        LOG.info("_rev: " + rev);
        if (HttpStatus.SC_CREATED != res.getStatus())
            throw new CouchDbDocumentCreationException("Couldn't create document: " + res.getStatusInfo().getReasonPhrase());
        res.close();

        return rev;
    }

    public void deleteDoc(String id, String rev) throws CouchDbDocumentDeletionException {

        LOG.info("_id: \"" + id + "\"");
        Response res = couchDbTarget.path(id).queryParam("rev", rev).request().delete();
        LOG.info(res.readEntity(JsonNode.class).toString());
        if (!(HttpStatus.SC_OK == res.getStatus() || HttpStatus.SC_ACCEPTED != res.getStatus()))
            throw new CouchDbDocumentDeletionException("Couldn't delete document: " + res.getStatusInfo().getReasonPhrase());
        res.close();
    }

    public JsonNode getView(String ddoc, String view) {

        LOG.info("ddoc: " + ddoc + ", view: " + view);
        Response res = couchDbTarget.path(DESIGN).path(ddoc).path(VIEW).path(view).request().get();
        res.bufferEntity();
        LOG.info(res.readEntity(String.class));
        if (HttpStatus.SC_OK != res.getStatus())
            throw new RuntimeException(res.getStatusInfo().getReasonPhrase());
        JsonNode j = res.readEntity(JsonNode.class);
        res.close();

        return j;
    }
}