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

The base url is: /minrva-estimote-server/rest/

##### Version Resources
- [`GET` /versions](./api.md)
- [`PUT` /versions](./api.md)
- [`GET` /versions/production](./api.md)
- [`GET` /versions/{versionId}](./api.md)
- [`GET` /versions/{versionId}/beacons](./api.md)
- [`POST` /versions/{versionId}/beacons](./api.md)
- [`GET` /versions/{versionId}/beacons/{beaconId}](./api.md)
- [`PUT` /versions/{versionId}/beacons/{beaconId}](./api.md)
- [`DELETE` /versions/{versionId}/beacons/{beaconId}](./api.md)
