package io.slug.slug_db_client;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Properties;
import java.util.logging.Logger;

import javax.ws.rs.core.UriBuilder;

import org.junit.BeforeClass;

public class CouchDbTest {

    private final static Logger LOG = Logger.getLogger(CouchDbTest.class.getName()); 

    static Properties props = new Properties();

    static UriBuilder uriBuilder;
    static URI BASE_URI;

    static String couchDbName;
    static String couchDbProtocol;
    static String couchDbHost;
    static int couchDbPort;
    static String couchDbUsername;
    static String couchDbPassword;

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

    }
}