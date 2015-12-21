package it.geosolutions.geofence.dao.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.geosolutions.geofence.core.dao.impl.LayerDetailsDAOImpl;

/**
 * It extracts the first numeric group found in the provided id 
 * 
 * @author DamianoG
 *
 */
public class IdExtractor implements IdConverter {

    private static final Logger LOGGER = LogManager.getLogger(IdExtractor.class);
    
    @Override
    public long convertId(String id) {
        Pattern p = Pattern.compile("\\d+"); 
        Matcher m = p.matcher(id);
        m.find();
        String group0 = null;
        try{
            group0 = m.group();
        }
        catch(Exception e){
            LOGGER.error("No numeric extraction has been possible on the id: '" + id + "'");
        }
        return Long.parseLong(group0);
    }

}
