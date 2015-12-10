# Minrva-Estimote-Server

## Development

For those new to Java server development, I've assembled a list of the best articles and tutorials I went through to get ready for this.  
* http://gridlab.dimes.unical.it/lackovic/eclipse-tomcat-ubuntu-jersey/
* http://code.scottshipp.com/learn/tutorial-full-stack-open-source-web-application-development-with-java/
* http://www.codejava.net/java-se/jdbc/connect-to-sqlite-via-jdbc

Also a quick read/watch on REST
* http://www.restapitutorial.com/lessons/whatisrest.html
* http://www.restapitutorial.com/lessons/restquicktips.html
* http://www.restapitutorial.com/lessons/httpmethods.html
* http://www.restapitutorial.com/lessons/restfulresourcenaming.html

-Ray


## REST API

The base url for development is: /minrva-estimote-server/rest/v1.0/

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
