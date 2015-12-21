package it.geosolutions.geofence.dao.utils;

/**
 * Perform an hash of the provided id
 * @author geosolutions
 *
 */
public class IdHasher implements IdConverter {

    @Override
    public long convertId(String id) {
        int hashcode = id.hashCode();
        return (hashcode>=0) ? hashcode : Math.abs(hashcode) * 971; 
    }

}
