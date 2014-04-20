package io.slug.slug_db_client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Session {

    protected Log log = LogFactory.getLog(Session.class);
    
    protected final String host;
    protected final int port;
    protected final String username;
    protected final String password;
    protected final AuthenticationType authenticationType;

    public Session(String host, int port, String username, String password, AuthenticationType authenticationType){
        
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.authenticationType = authenticationType;
    }
 
    public Session(String host, int port, String username, String password) {
        
        this(host, port, username, password, AuthenticationType.BASIC);
    }
    
    public Database getDataBase(String name){
        
        return null;
    }
}
