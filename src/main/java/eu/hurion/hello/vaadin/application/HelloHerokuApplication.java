package eu.hurion.hello.vaadin.application;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloHerokuApplication extends UI {

    private static final Logger LOG = LoggerFactory.getLogger(HelloHerokuApplication.class);

    public static final String HELLO_WORLD = "Hello from Heroku, ";
    public static final String BUTTON_CAPTION = "Click me";
    public static final String NAME_LABEL = "What is your name?";
    public static final String NAME = "name";

    @Override
    public void init(final VaadinRequest vaadinRequest) {
        setContent(buildContent());
    }

    private ComponentContainer buildContent() {
        final FormLayout formLayout = new FormLayout();
        formLayout.setSpacing(true);
        formLayout.setSizeUndefined();

        final TextField nameInput = new TextField();
        nameInput.setCaption(NAME_LABEL);
        final String storedName = (String) getUI().getSession().getAttribute(NAME);
        if (storedName != null){
            nameInput.setValue(storedName);
        }
        formLayout.addComponent(nameInput);

        final Button showButton = new Button(BUTTON_CAPTION, new Button.ClickListener() {
            @Override
            public void buttonClick(final Button.ClickEvent clickEvent) {
                getUI().getSession().setAttribute(NAME, nameInput.getValue());
                final String greeting = HELLO_WORLD + nameInput.getValue() + " !";
                LOG.debug(greeting);
                Notification.show(greeting);
            }
        });
        formLayout.addComponent(showButton);
        final VerticalLayout vl = new VerticalLayout();
        vl.addComponent(formLayout);
        vl.setComponentAlignment(formLayout, Alignment.MIDDLE_CENTER);
        vl.setSizeFull();
        return vl;
    }

}
