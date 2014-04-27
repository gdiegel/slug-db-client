package io.slug.slug_db_client;

import java.util.UUID;
import java.util.logging.Logger;

import org.junit.Assert;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

public class DocumentTest extends CouchDbTest {

    private final static Logger LOG = Logger.getLogger(DocumentTest.class.getName());

    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void canCreateDocument() throws Exception {

        JsonNode doc = mapper.readValue("{\"_id\":\"" + UUID.randomUUID() + "\", \"test_key\":\"test_value\"}", JsonNode.class);
        LOG.info("Putting doc: " + doc.toString());
        client.putDoc(doc);
    }

    @Test
    public void canReadDocument() throws Exception {

        String id = UUID.randomUUID().toString();
        JsonNode doc = mapper.readValue("{\"_id\":\"" + id + "\", \"test_key\":\"test_value\"}", JsonNode.class);
        String rev = client.putDoc(doc);

        JsonNode j = client.getDoc(id);
        Assert.assertEquals(rev, j.get("_rev").asText());
    }

    @Test
    public void canDeleteDocument() throws Exception {

        String id = UUID.randomUUID().toString();
        JsonNode doc = mapper.readValue("{\"_id\":\"" + id + "\", \"test_key\":\"test_value\"}", JsonNode.class);
        String rev = client.putDoc(doc);

        client.deleteDoc(id, rev);
    }

}
