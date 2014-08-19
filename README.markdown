GeoFence
==================================================

**GeoFence** is a java web application that provides an authentication/authorization engine to [GeoServer](http://www.geoserver.org).

**GeoFence** integrates with [GeoServer](http://www.geoserver.org) using the interface 
described in [GSIP 57](http://geoserver.org/display/GEOS/GSIP+57+-+Improving+GeoServer+authorization+framework).

How to interact with GeoFence
--------------------------------------------------
**GeoFence** provides a _GWT-based_ user interface to administer the basic objects and the authorization rules.

Furthermore, a quite complete [REST API](https://github.com/geosolutions-it/geofence/wiki/REST-API) allows the programmatic administration of the rules and their ancillary data.

GeoServer will send authorization queries to GeoFence using a configurable internal protocol (by default it uses Spring remoting over HTTP).

License
==================================================
GeoFence is free and Open Source software, released under the [GPL v3](http://www.gnu.org/licenses/gpl.html) license.

The part of GeoFence that shall be installed as a module into GeoServer is released under the same license as GeoServer (which is [GPL v2.0](http://www.gnu.org/licenses/old-licenses/gpl-2.0.html)), as it implements a GeoServer Java API.

Installing and Running GeoFence
==================================================
* [Latest **GeoFence** .war file](http://build.geo-solutions.it/geofence/nightly/latest/geofence.war)
* [Latest GeoFence **probe** for GeoServer **Master**](http://build.geo-solutions.it/geofence/nightly/latest/geofence-security-2.2-SNAPSHOT.jar) (TODO)
* [Latest GeoFence **probe** for GeoServer **2.5.x** ](http://build.geo-solutions.it/geofence/nightly/latest/geofence-security-2.2-SNAPSHOT.jar) (TODO)
* [Latest GeoServer 2.5.x with GeoFence probe preinstalled ](http://build.geo-solutions.it/geofence/nightly/latest/geofence-security-2.2-SNAPSHOT.jar) (TODO)


Documentation
==================================================
* [How to build GeoFence](https://github.com/geosolutions-it/geofence/wiki/Building-instructions)
* [How to configure GeoFence](https://github.com/geosolutions-it/geofence/wiki/WebApps-configuration)

Community
==================================================
* [Users forum](https://groups.google.com/forum/#!forum/geofence-users)
* [Developers forum](https://groups.google.com/forum/#!forum/geofence-developers)
