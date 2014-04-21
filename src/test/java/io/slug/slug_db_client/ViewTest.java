package io.slug.slug_db_client;

import java.util.UUID;
import java.util.logging.Logger;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ViewTest extends CouchDbTest {

    private final static Logger LOG = Logger.getLogger(ViewTest.class.getName());

    private CouchDbSession s;
    private CouchDbClient c;
    private ObjectMapper mapper = new ObjectMapper();

    private static final String TEST_DDOC = "tdoc";
    private static final String TEST_VIEW = "tdoc";

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
    public void canGetView() throws Exception {

        JsonNode doc = mapper.readValue("{\"_id\":\"" + UUID.randomUUID() + "\", \"key\":\"value\"}", JsonNode.class);
        LOG.info("Putting doc: " + doc.toString());
        c.putDoc(doc);

        JsonNode j = c.getView(TEST_DDOC, TEST_VIEW);
        Assert.assertTrue(0 < j.get("total_rows").asInt());
        Assert.assertTrue(j.get("rows").isArray());
    }
}
