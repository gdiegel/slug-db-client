package io.slug.slug_db_client;

import java.util.logging.Logger;

import org.junit.Test;

public class SessionTest extends CouchDbTest {

    private final static Logger LOG = Logger.getLogger(SessionTest.class.getName());

    @Test
    public void canGetDatabaseInfo() {

        CouchDbSession s = new CouchDbSession(couchDbProtocol, couchDbHost, couchDbPort, couchDbUsername, couchDbPassword, AuthenticationType.BASIC);
        CouchDbClient c = s.getCouchDbClient(couchDbName);
        c.printDbInfo();
        s.invalidate();
    }

}
