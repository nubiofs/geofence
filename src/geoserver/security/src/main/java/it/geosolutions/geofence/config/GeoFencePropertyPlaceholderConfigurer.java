/* Copyright (c) 2011 GeoSolutions - http://www.geo-solutions.it/.  All rights reserved.
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */
package it.geosolutions.geofence.config;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.geoserver.config.GeoServerDataDirectory;
import org.geoserver.config.GeoServerPropertyConfigurer;
import org.geotools.util.logging.Logging;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

public class GeoFencePropertyPlaceholderConfigurer extends GeoServerPropertyConfigurer {

    static Logger LOGGER = Logging.getLogger("it.geosolutions.geofence.config");

    GeoServerDataDirectory dataDirectory;
    File configFile;

    public GeoFencePropertyPlaceholderConfigurer(GeoServerDataDirectory data) {
        super(data);
        this.dataDirectory = data;
    }
    
    public Properties[] getProperties() {
        return localProperties;
    }
    
    public Properties getMergedProperties() throws IOException {
        return mergeProperties();
    }
    
    /**
     * @return the configLocation
     */
    public File getConfigFile() {
        return configFile;
    }
    
    @Override
    public void setLocation(Resource location) {
        super.setLocation(location);
        try {
            configFile = location.getFile();
        } catch (IOException e) {
            LOGGER.log(Level.FINER, e.getMessage(), e);
        }
    }

    @Override
    public void setLocations(Resource[] locations) {
        for(Resource location : locations) {
            try {
                File f = location.getFile();
                if (f != null && !f.isAbsolute()) {
                    //make relative to data directory
                    f = new File(dataDirectory.root(), f.getPath());
                }
                location = new UrlResource(f.toURI());
                if(f.exists()) {
                    configFile = f;
                    super.setLocation(location);
                }
            }
            catch(IOException e) {
                LOGGER.log(Level.WARNING, "Error reading resource " + location, e);
            }
        }
        
    }

	
}
