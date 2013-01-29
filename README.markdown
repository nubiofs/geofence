GeoFence
==================================================

**GeoFence** is a java web application that handles authorization rules, 
integrating with [GeoServer](http://www.geoserver.org) using the interface 
described in [GSIP 57](http://geoserver.org/display/GEOS/GSIP+57+-+Improving+GeoServer+authorization+framework).

How to interact with GeoFence
--------------------------------------------------
**GeoFence** provides a _GWT-based_ user interface to administer the basic objects and the authorization rules.

Furthermore, some _REST API_ has already been implemented that allows the administration of some ancillary data.

GeoServer will send authorization queries to GeoFence using a configurable internal protocol (by default it uses Spring remoting over HTTP).

High level arch
--------------------------------------------------
**GeoFence** is split in 3 main modules:
* The Core module contains the model, the persistence logic and the service logic
* The GUI module contains the GWT GUI used to administer the authorization rules and the related objects
* The bridge/probe module is the wrapper that converts the Rule logic into the authorization objects required by GeoServer.
This last module provides a jar file that will be plugged into GeoServer. 
The GeoFence code base includes a module that will build a GeoServer .war file ready to be integrated with GeoFence.

Download
==================================================
* [**GeoFence** .war file](http://maven.geo-solutions.it/it/geosolutions/geofence/web) **TODO**
* [Latest **GeoServer** .war file (2.2.x)](http://geo-solutions.it) **TODO**
* [Latest **GeoServer** .war file (2.1.x)](http://geo-solutions.it) **TODO**


Documentation
==================================================
* [How to build GeoFence](https://github.com/geosolutions-it/geofence/wiki/Building-instructions)
* [How to configure GeoFence](https://github.com/geosolutions-it/geofence/wiki/WebApps-configuration)

