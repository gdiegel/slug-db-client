package io.slug.slug_db_client;

import java.io.IOException;
import java.util.Base64;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.MediaType;

public class BasicAuthenticationFilter implements ClientRequestFilter {

    private String toEncode;

    public BasicAuthenticationFilter(String username, String password) {

        StringBuffer sb = new StringBuffer();
        sb.append(username).append(":").append(password);
        toEncode = sb.toString();
    }

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {

        requestContext.getHeaders().add("Accept", MediaType.APPLICATION_JSON);
        requestContext.getHeaders().add("Authorization", "Basic " + Base64.getEncoder().encodeToString(toEncode.getBytes()));
    }

}
