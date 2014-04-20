package io.slug.slug_db_client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class SessionTest extends CouchDbTest {

    protected Log log = LogFactory.getLog(SessionTest.class);

    @Test
    public void canGetDatabaseInfo() {
        
        CouchDbSession s = new CouchDbSession(couchDbProtocol, couchDbHost, couchDbPort, couchDbUsername, couchDbPassword, AuthenticationType.BASIC);
        CouchDbClient c = s.getCouchDbClient(couchDbName);
        c.printDbInfo();
    }

}
