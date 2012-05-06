Vaadin on Heroku
================

The goal of this project is to demonstrate how to easily build a small [Vaadin](https://vaadin.com/home) application and deploy it on [Heroku](http://www.heroku.com).

It is very largely inspired by [this blog post](http://blog.heroku.com/archives/2011/8/25/java/), the only difference being that instead of starting a simple Jetty, it uses
[Embed for Vaadin](https://vaadin.com/directory#addon/embed-for-vaadin) to quickly start an easily configured embedded [tomcat](http://tomcat.apache.org/) and the vaadin servlet.

What is heroku specific in there?
---------------------------------

Not much really.

* Procfile: It's the file containing the command Heroku will launch once the application is build and deployed.
* maven-dependency-plugin configured in the pom to copy the dependencies.
* HelloHeroku class that will launch Tomcat is taking the port from an environment variable. This is how it works on heroku.

Limitations
-----------

This project doesn't have anything to deal with sessions management and is only constituted of a single module.
It's working fine for playing around and do some testing, but it's not enough if you plan to use multiple dynos or do a real life application.

If you want something with a little more beaf, check out [vaadin-heroku-multi](https://github.com/nhurion/vaadin-heroku-multi).

Where tho go from here?
-----------------------

If you never used heroku before, install the [toolbelt](https://toolbelt.heroku.com/) and [create an account](http://heroku.com/signup).

Just clone this repository and type

    heroku create --stack cedar

Heroku will create your app and give you it's url. You can change it later.
Then push the application like this :

    git push heroku master

And the application is up and running.


As an example, this application is deployed on heroku and accessible at http://hello-vaadin-heroku.herokuapp.com/
