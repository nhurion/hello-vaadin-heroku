import com.bsb.common.vaadin.embed.support.EmbedVaadin;
import com.vaadin.ui.Label;


public class HelloWorld {

    public static final String PORT = "PORT";
    public static final String HELLO_WORLD = "Hello World!";

    public static void main(String[] args) {
        int httpPort = Integer.parseInt(System.getenv(PORT));
        EmbedVaadin.forComponent(
                new Label(HELLO_WORLD))
                .withHttpPort(httpPort)
                .start();
    }
}
