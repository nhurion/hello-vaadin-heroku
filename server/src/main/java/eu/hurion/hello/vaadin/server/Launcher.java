package eu.hurion.hello.vaadin.server;

import com.bsb.common.vaadin.embed.support.EmbedVaadin;
import eu.hurion.hello.vaadin.application.HelloHerokuApplication;

/**
 * Command line entry point for the application.
 */
public class Launcher {

    public static final String PORT = "PORT";
    public static final int DEFAULT_PORT = 8080;


    public static void main(final String[] args) {
        final int httpPort = getPort();
        EmbedVaadin.forApplication(HelloHerokuApplication.class)
                .withHttpPort(httpPort)
                .withProductionMode(true)
                .start();
    }

    private static int getPort() {
        final String envPort = System.getenv(PORT);
        if (envPort == null) {
            return DEFAULT_PORT;
        }
        return Integer.parseInt(envPort);
    }
}
