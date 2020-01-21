/*
 * Copyright (c) 2020-present Revolute. All Rights Reserved.
 *
 * Licensed Material - Property of Revolute.
 */
package com.revolut.webservices;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;

import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.net.httpserver.HttpServer;

/**
 * main class to start embedded server
 * **/

@SuppressWarnings("restriction")
public class Main {
    private static HttpServer hTTPServer;
    
    public static void main(String[] args) throws IOException {
        try{
            serverStartup();
        } catch (Exception e) {
           System.out.println("Error in Starting embedded Server" + e.getMessage());
        }
    }
    
    public static void serverStartup() throws IOException {
        System.out.println("Starting Revolut Embedded Jersey HTTPServer...\n");
        hTTPServer = createHttpServer();
        hTTPServer.start();
        System.out.println(String.format("\nJersey Application Server started on " + "%s\n", getURI()));
        System.out.println("Started Revolut Embedded Jersey HTTPServer Successfully !!!");
    }

    private static HttpServer createHttpServer() throws IOException {
        ResourceConfig resourceConfig = new PackagesResourceConfig("com.revolut.webservices");
        return HttpServerFactory.create(getURI(), resourceConfig);
    }

    private static URI getURI() {
        return UriBuilder.fromUri("http://" + getHostName() + "/").port(8080).build();
    }

    private static String getHostName() {
        String hostName = "localhost";
        try {
            hostName = InetAddress.getLocalHost().getCanonicalHostName();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return hostName;
    }
}
