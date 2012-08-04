package eu.hurion.hello.vaadin.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static eu.hurion.hello.vaadin.server.VaadinForHeroku.devServer;
import static eu.hurion.hello.vaadin.server.VaadinForHeroku.prodServer;

/**
 * Command line entry point for the application.
 */
public class Launcher {

    private static final Logger LOG = LoggerFactory.getLogger(Launcher.class);

    public static final String DEV_MODE = "DEV_MODE";

    public static void main(final String[] args) {
        if (devMode()) {
            devServer().start();
        } else {
            prodServer().start();
        }
    }

    private static boolean devMode() {
        final String stringDevMode = System.getenv(DEV_MODE);
        if (stringDevMode == null && System.getenv(VaadinForHeroku.PORT) == null) {
            LOG.error("You should either set the DEV_MOD system property to true or set the System properties used " +
                    "on Heroku (port, memcached configuration,...");
        }
        return Boolean.valueOf(stringDevMode);

    }
}
