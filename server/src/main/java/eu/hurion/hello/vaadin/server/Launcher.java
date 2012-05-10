package eu.hurion.hello.vaadin.server;

import eu.hurion.hello.vaadin.application.HelloHerokuApplication;

import static eu.hurion.hello.vaadin.server.MemcachedConfigurator.memcachedConfig;

/**
 * Command line entry point for the application.
 */
public class Launcher {

    public static final String PORT = "PORT";
    public static final int DEFAULT_PORT = 8080;
    public static final String MEMCACHE_USERNAME = "MEMCACHE_USERNAME";
    public static final String MEMCACHE_PASSWORD = "MEMCACHE_PASSWORD";
    public static final String MEMCACHE_SERVERS = "MEMCACHE_SERVERS";

    public static void main(final String[] args) {
        EmbedVaadinForHeroku.forApplication(HelloHerokuApplication.class)
                .withMemcachedSessionManager(memcachedConfig()
                        .username(System.getenv(MEMCACHE_USERNAME))
                        .password(System.getenv(MEMCACHE_PASSWORD))
                        .url(getMemcachedUrl()))
                .withHttpPort(getPort())
                .withProductionMode(true)
                .start();
    }

    private static String getMemcachedUrl() {
        final String envMemcacheServer = System.getenv(MEMCACHE_SERVERS);
        if (envMemcacheServer == null) {
            return "127.0.0.1";
        }
        return envMemcacheServer;

    }

    private static int getPort() {
        final String envPort = System.getenv(PORT);
        if (envPort == null) {
            return DEFAULT_PORT;
        }
        return Integer.parseInt(envPort);
    }
}
