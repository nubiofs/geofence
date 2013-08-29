In order to run the tests in this module you need the georepository
tests running with a specific set of data.
To get there:

createdb grtest -U postgis_template (or template_postgis)
psql grtest -f georepo-services/distribution/sql/001_setup_db.sql 
cd georepo-services/core/webtest
mvn jetty:run

At this point you should have the georepo services running on port
9191 and the tests for GeorepositoryAccessManager should be working