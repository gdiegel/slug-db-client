package io.slug.slug_db_client;

import java.util.UUID;
import java.util.logging.Logger;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

public class ViewTest extends CouchDbTest {

    private final static Logger LOG = Logger.getLogger(ViewTest.class.getName());

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void canGetView() throws Exception {

        JsonNode doc = mapper.readValue("{\"_id\":\"" + UUID.randomUUID() + "\", \"key\":\"value\"}", JsonNode.class);
        LOG.info("Putting doc: " + doc.toString());
        client.putDoc(doc);

        JsonNode j = client.getView(TEST_DDOC, TEST_VIEW);
        Assert.assertTrue(0 < j.get("total_rows").asInt());
        Assert.assertTrue(j.get("rows").isArray());
    }
}
