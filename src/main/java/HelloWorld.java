import com.bsb.common.vaadin.embed.EmbedVaadinServer;
import com.bsb.common.vaadin.embed.support.EmbedVaadin;
import com.vaadin.ui.Label;


public class HelloWorld {

    public static void main(String[] args) {
        int httpPort = Integer.parseInt(System.getenv("PORT"));
        final EmbedVaadinServer server = EmbedVaadin.forComponent(new Label("hello world")).withHttpPort(httpPort).start();
    }
}
