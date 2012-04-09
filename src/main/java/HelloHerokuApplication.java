import com.vaadin.Application;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class HelloHerokuApplication extends Application {

    public static final String HELLO_WORLD = "Hello World!";

    @Override
    public void init() {
        final Window window = new Window();
        setMainWindow(window);
        final VerticalLayout mainLayout = new VerticalLayout();
        window.addComponent(mainLayout);
        mainLayout.addComponent(new Label(HELLO_WORLD));
    }
}
