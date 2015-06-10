**The current repository is not open for development, it is kept around for historical purposes only.**
====================================================================================================

**Please use the repository https://github.com/geoserver/geofence instead.** 
====================================================================================================

GeoFence
==================================================

**GeoFence** is a java web application that provides an advanced authentication/authorization engine for [GeoServer](http://www.geoserver.org) using the interface 
described in [GSIP 57](http://geoserver.org/display/GEOS/GSIP+57+-+Improving+GeoServer+authorization+framework).

**GeoFence** provides a graphical user interface to administer GeoServer users and authorization rules. Furthermore, a quite complete [REST API](https://github.com/geosolutions-it/geofence/wiki/REST-API) allows the programmatic administration of the rules and their ancillary data.

Once integrated with **GeoFence**, GeoServer will send authorization queries to GeoFence using a configurable internal protocol (by default it uses Spring remoting over HTTP).

License
==================================================
**GeoFence** core modules and GUI are free and Open Source software, released under the [GPL v3](http://www.gnu.org/licenses/gpl.html) license.

The part of **GeoFence** that shall be installed as a module into GeoServer (the **probe**) is released under the same license as GeoServer (which is [GPL v2.0](http://www.gnu.org/licenses/old-licenses/gpl-2.0.html)), as it implements a GeoServer Java API.

Getting GeoFence
==================================================

You will need the **GeoFence** .war file, and the probe module to be installed into your GeoServer instance.
In this list you can find a GeoServer .war file with the GeoFence probe preinstalled.

This is the list of the currently available resources for using **GeoFence**:

* [Latest **GeoFence** .war file](http://build.geo-solutions.it/geofence/nightly/latest/geofence.war)
* [Latest GeoFence **probe** for GeoServer **Master**](http://build.geo-solutions.it/geofence/nightly/latest/master/geoserver-2.7-SNAPSHOT-geofence-plugin.zip)
* [Latest GeoFence **probe** for GeoServer **2.6.x** ](http://build.geo-solutions.it/geofence/nightly/latest/2.6.x/geoserver-2.6-SNAPSHOT-geofence-plugin.zip)
* [Latest GeoFence **probe** for GeoServer **2.5.x** ](http://build.geo-solutions.it/geofence/nightly/latest/2.5.x/geoserver-2.5-SNAPSHOT-geofence-plugin.zip)
* [Latest **GeoServer Master** with GeoFence probe preinstalled ](http://build.geo-solutions.it/geofence/nightly/latest/master/geoserver.war)
* [Latest **GeoServer 2.6.x** with GeoFence probe preinstalled ](http://build.geo-solutions.it/geofence/nightly/latest/2.6.x/geoserver.war)
* [Latest **GeoServer 2.5.x** with GeoFence probe preinstalled ](http://build.geo-solutions.it/geofence/nightly/latest/2.5.x/geoserver.war)

Once you have downloaded the resources you need, please follow the instructions on the [GeoFence installation](https://github.com/geosolutions-it/geofence/wiki/GeoFence-installation) wiki page.


Documentation
==================================================
* [How to install GeoFence](https://github.com/geosolutions-it/geofence/wiki/GeoFence-installation)
* [How to configure GeoFence](https://github.com/geosolutions-it/geofence/wiki/WebApps-configuration)
* [How to build GeoFence](https://github.com/geosolutions-it/geofence/wiki/Building-instructions)
* [Documentation Index](https://github.com/geosolutions-it/geofence/wiki/Documentation-index)

Community
==================================================
* [Users forum](https://groups.google.com/forum/#!forum/geofence-users)
* [Developers forum](https://groups.google.com/forum/#!forum/geofence-developers)
