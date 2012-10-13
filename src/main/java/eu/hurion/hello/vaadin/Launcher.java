package eu.hurion.hello.vaadin;

import eu.hurion.hello.vaadin.application.HelloHerokuApplication;

import static eu.hurion.vaadin.heroku.VaadinForHeroku.forApplication;
import static eu.hurion.vaadin.heroku.VaadinForHeroku.herokuServer;

public class Launcher {

    public static void main(final String[] args) {
            herokuServer(forApplication(HelloHerokuApplication.class)).start();
    }
}
