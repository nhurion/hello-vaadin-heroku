import com.bsb.common.vaadin.embed.support.EmbedVaadin;
import com.vaadin.ui.Label;


public class HelloWorld {

    public static final String PORT = "PORT";
    public static final int DEFAULT_PORT = 8080;
    public static final String HELLO_WORLD = "Hello World!";

    public static void main(String[] args) {
        int httpPort = getPort();
        EmbedVaadin.forComponent(
                new Label(HELLO_WORLD))
                .withHttpPort(httpPort)

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
