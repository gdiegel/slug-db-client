package io.slug.slug_db_client;

import java.util.UUID;
import java.util.logging.Logger;

import org.junit.Assert;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DocumentTest extends CouchDbTest {

    private final static Logger LOG = Logger.getLogger(DocumentTest.class.getName());

    CouchDbSession s;
    CouchDbClient c;
    ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() {

        s = new CouchDbSession(couchDbProtocol, couchDbHost, couchDbPort, couchDbUsername, couchDbPassword, AuthenticationType.BASIC);
        c = s.getCouchDbClient(couchDbName);
    }

    @After
    public void tearDown() {

        s.invalidate();
    }

    @Test
    public void canCreateDocument() throws Exception {

        JsonNode doc = mapper.readValue("{\"_id\":\"" + UUID.randomUUID() + "\", \"key\":\"value\"}", JsonNode.class);
        LOG.info("Putting doc: " + doc.toString());
        c.putDoc(doc);
    }

    @Test
    public void canReadDocument() throws Exception {

        String id = UUID.randomUUID().toString();
        JsonNode doc = mapper.readValue("{\"_id\":\"" + id + "\", \"key\":\"value\"}", JsonNode.class);
        String rev = c.putDoc(doc);

        JsonNode j = c.getDoc(id);
        Assert.assertEquals(rev, j.get("_rev").asText());
    }

    @Test
    public void canDeleteDocument() throws Exception {

        String id = UUID.randomUUID().toString();
        JsonNode doc = mapper.readValue("{\"_id\":\"" + id + "\", \"key\":\"value\"}", JsonNode.class);
        String rev = c.putDoc(doc);

        c.deleteDoc(id, rev);
    }

}
