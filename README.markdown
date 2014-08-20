GeoFence
==================================================

**GeoFence** is a java web application that provides an advanced authentication/authorization engine to [GeoServer](http://www.geoserver.org).

**GeoFence** integrates with [GeoServer](http://www.geoserver.org) using the interface 
described in [GSIP 57](http://geoserver.org/display/GEOS/GSIP+57+-+Improving+GeoServer+authorization+framework).

**GeoFence** provides a graphical user interface to administer the basic objects and the authorization rules. Furthermore, a quite complete [REST API](https://github.com/geosolutions-it/geofence/wiki/REST-API) allows the programmatic administration of the rules and their ancillary data.

GeoServer will send authorization queries to GeoFence using a configurable internal protocol (by default it uses Spring remoting over HTTP).

License
==================================================
GeoFence is free and Open Source software, released under the [GPL v3](http://www.gnu.org/licenses/gpl.html) license.

The part of GeoFence that shall be installed as a module into GeoServer is released under the same license as GeoServer (which is [GPL v2.0](http://www.gnu.org/licenses/old-licenses/gpl-2.0.html)), as it implements a GeoServer Java API.

Installing GeoFence
==================================================
This is the list of the currently available resources for using GeoFence.

You will need the Geofence .war file, and the probe module to be installed into your GeoServer instance.
In this list you can find a GeoServer .war file with the GeoFence probe preinstalled.

* [Latest **GeoFence** .war file](http://build.geo-solutions.it/geofence/nightly/latest/geofence.war)
* [Latest GeoFence **probe** for GeoServer **Master**](http://build.geo-solutions.it/geofence/nightly/latest/geofence-security-2.2-SNAPSHOT.jar) (TODO)
* [Latest GeoFence **probe** for GeoServer **2.5.x** ](http://build.geo-solutions.it/geofence/nightly/latest/geofence-security-2.2-SNAPSHOT.jar) (TODO)
* [Latest GeoServer 2.5.x with GeoFence probe preinstalled ](http://build.geo-solutions.it/geofence/nightly/latest/geofence-security-2.2-SNAPSHOT.jar) (TODO)

Once you have downloaded the resources you need, please follow the instructions on the [GeoFence installation](https://github.com/geosolutions-it/geofence/wiki/GeoFence-installation) wiki page.


Documentation
==================================================
* [How to build GeoFence](https://github.com/geosolutions-it/geofence/wiki/Building-instructions)
* [How to install GeoFence](https://github.com/geosolutions-it/geofence/wiki/GeoFence-installation)
* [How to configure GeoFence](https://github.com/geosolutions-it/geofence/wiki/WebApps-configuration)

Community
==================================================
* [Users forum](https://groups.google.com/forum/#!forum/geofence-users)
* [Developers forum](https://groups.google.com/forum/#!forum/geofence-developers)
