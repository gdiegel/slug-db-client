package io.slug.slug_db_client;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Database {

    Log log = LogFactory.getLog(Database.class);
    
    private final String name;

    private Session session;

    private static final String VIEW = "/_view/";
    private static final String DESIGN = "_design/";
    private static final String UPDATE = "/_update/";

    Database(Properties props, Session session) {

        name = (String) props.get("name");

        this.session = session;
    }
}