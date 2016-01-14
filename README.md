# Minrva-Estimote-Server

___
### Table of Contents  
1. [A Tech Introduction](#tech_intro)
2. [Creating Development Servers](#dev_servers)
3. [REST Api](#rest_api)

___

<a name="tech_intro"/>
## A Tech Introduction  

This project built using Maven and handles Java server development using Jersey 2.22.1 and Jackson Jaxrs .The server is meant to expose a REST api to interact with Estimote Beacons for the Minrva Wayfinder app. The project is deployed to a Tomcat 7 server. 

For our database we use MySQL through Java's JDBC. We also plan to move over to using Google's Guice injection to inject our code and then use some testing framework so that the code is well tested. 


<a name="dev_servers"/>
## Creating Development Servers

There are several resources online on how to setup Eclipse with Tomcat 7 and build and start a Maven project from Eclipse that are easily Googlable. However, this project communicates to a MySQL DB which means that the project needs to have a URL, username, and password for the database connection. These variables can't be hardcoded in the source code (security issues) so must be passed into the project as it runs. The solution used in this project is enviornment variables which creates a simple, cross-platform way of setting these variables. The project then can read these variables in via System.getenv().

These variables are: 
* minrva_db_url
* minrva_db_username
* minrva_db_password

To recieve the correct credentials contact a developer who already has them.  

As for setting these variables, there are several methods for each OS. Yet, although I have yet to try this method, you may simply wish to use [Eclipse to set the variables](http://help.eclipse.org/luna/index.jsp?topic=%2Forg.eclipse.cdt.doc.user%2Ftasks%2Fcdt_t_run_env.htm) and keep your developer enviornment boxed together. 

For a Linux system you can simply export the correct variables within .profile or set them within the shell that runs the project test server. If you wish to set a system wide variable you can export the variable within /etc/profile or similar. Read more [here](https://www.digitalocean.com/community/tutorials/how-to-read-and-set-environmental-and-shell-variables-on-a-linux-vps).

A Mac OS system should be similiar enough to a Linux one where you can export them within .profile. However, some syntax and system files may be different. 

For a Windows system there should be an interface to set enviornment variables. ([see here](http://www.computerhope.com/issues/ch000549.htm)). 



<a name="rest_api"/>
## REST API

The base url for development is: /minrva-estimote-server/rest/v1.0/
The base url for production is: /rest/v1.0/

##### Version Resource
- [`GET` /version](./api.md#get-version)

##### Beacons Resources
- [`GET` /beacons](./api.md#get-beacons)
- [`GET` /beacons/\[uuid\]](./api.md#get-beaconsuuid)
- [`GET` /beacons/\[uuid\]/\[major\]](./api.md#get-beaconsuuidmajor)
- [`GET` /beacons/\[uuid\]/\[major\]/\[minor\]](./api.md#get-beaconsuuidmajorminor)
- [`POST` /beacons](./api.md#post-beacons)
- [`PUT` /beacons/\[uuid\]/\[major\]/\[minor\]](./api.md#put-beaconsuuidmajorminor)
- [`DELETE` /beacons/\[uuid\]/\[major\]/\[minor\]](./api.md#delete-beaconsuuidmajorminor)
