package eu.hurion.hello.vaadin;

import com.bsb.common.vaadin.embed.support.EmbedVaadin;


public class HelloHeroku {

    public static final String PORT = "PORT";
    public static final int DEFAULT_PORT = 8080;


    public static void main(String[] args) {
        int httpPort = getPort();
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
