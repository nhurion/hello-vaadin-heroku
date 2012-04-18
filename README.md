Vaadin on Heroku
================

This project is to demonstrate how to easily build a small [Vaadin](https://vaadin.com/home) application to be deployed on [Heroku](http://www.heroku.com).

It is very largely inspired by [this blog post](http://blog.heroku.com/archives/2011/8/25/java/), the only difference being that instead of starting a simple Jetty, it uses
[Embed for Vaadin](https://vaadin.com/directory#addon/embed-for-vaadin) to quickly start an easily configured embedded [tomcat](http://tomcat.apache.org/).

Where tho go from here?
-----------------------

Simply clone this repository and assuming you have heroku correctly configured simply type

    heroku create --stack cedar

Heroku will create your app and give you it's url.
Then push the application like this :

    git push heroku master

And the application is up and running.


As an example, this application is deployed on heroku and accessible at http://hello-vaadin-heroku.herokuapp.com/