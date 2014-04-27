package io.slug.slug_db_client;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Logger;

import javax.ws.rs.core.UriBuilder;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;

public class CouchDbTest {

    private final static Logger LOG = Logger.getLogger(CouchDbTest.class.getName()); 

    protected static final String TEST_DDOC = "tdoc";
    protected static final String TEST_VIEW = "tdoc";
    
    static Properties props = new Properties();

    static UriBuilder uriBuilder;
    static URI BASE_URI;

    static String couchDbName;
    static String couchDbProtocol;
    static String couchDbHost;
    static int couchDbPort;
    static String couchDbUsername;
    static String couchDbPassword;

    protected static CouchDbSession session;
    protected static CouchDbClient client;

    @BeforeClass
    public static void initialize() {

        InputStream input = null;

        try {
            input = Thread.currentThread().getContextClassLoader().getResourceAsStream("couchdb.properties");
            props.load(input);
        } catch (NullPointerException e) {
            LOG.warning("Couldn't load properties");
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            LOG.warning(e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    LOG.warning(e.getMessage());
                }
            }
        }

        couchDbName = props.getProperty("couchdb.name");
        couchDbProtocol = props.getProperty("couchdb.protocol");
        couchDbHost = props.getProperty("couchdb.host");
        couchDbPort = Integer.parseInt(props.getProperty("couchdb.port"));
        couchDbUsername = props.getProperty("couchdb.username");
        couchDbPassword = props.getProperty("couchdb.password");

        session = new CouchDbSession(couchDbProtocol, couchDbHost, couchDbPort, couchDbUsername, couchDbPassword, AuthenticationType.BASIC);
        client = session.getCouchDbClient(couchDbName);
        
    }
    
    @AfterClass
    public static void cleanUp() throws CouchDbDocumentDeletionException{
        
        JsonNode j = client.getView(TEST_DDOC, TEST_VIEW);
        if (j.get("rows").isArray()) {
            ArrayNode a = (ArrayNode) j.get("rows");
            Iterator<JsonNode> elements = a.getElements();
            while (elements.hasNext()){
                JsonNode next = elements.next();
                client.deleteDoc(next.get("id").getTextValue(), next.get("value").get("doc_rev").getTextValue());
            }
        }
        
        Assert.assertTrue(0 == client.getView(TEST_DDOC, TEST_VIEW).get("total_rows").asInt());
    }
}