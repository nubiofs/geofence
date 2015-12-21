package it.geosolutions.geofence.ldap.dao.impl;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttributes;

import org.junit.Before;
import org.junit.Test;

import it.geosolutions.geofence.core.model.GSUser;

public class GSUserAttributeMapperExtractorTest extends BaseDAOTest{

    @Before
    public void init(){
        setExtractorIdCoverter();
    }
    
    @Test
    public void gsUserAttributesMapperTest1() throws NamingException{
        Attributes attributes = new BasicAttributes();
        attributes.put("telephoneNumber", "hjkh6767kk");
        attributes.put("username", "username");
        attributes.put("mail", "email");
        attributes.put("name", "name");
        attributes.put("surname", "surname");
        attributes.put("password", "password");
        
        Object user = gsUserAttributesMapper.mapFromAttributes(attributes);
        assertTrue(user instanceof GSUser);
        assertEquals(true, ((GSUser)user).getId() instanceof Long);
        assertEquals(new Long(6767), ((GSUser)user).getId());
    }
    
    @Test
    public void gsUserAttributesMapperTest2() throws NamingException{
        Attributes attributes = new BasicAttributes();
        attributes.put("telephoneNumber", "hjkh6.767kk");
        attributes.put("username", "username");
        attributes.put("mail", "email");
        attributes.put("name", "name");
        attributes.put("surname", "surname");
        attributes.put("password", "password");
        
        Object user = gsUserAttributesMapper.mapFromAttributes(attributes);
        assertTrue(user instanceof GSUser);
        assertEquals(true, ((GSUser)user).getId() instanceof Long);
        assertEquals(new Long(6), ((GSUser)user).getId());
    }
    
    @Test
    public void gsUserAttributesMapperTest3() throws NamingException{
        Attributes attributes = new BasicAttributes();
        attributes.put("telephoneNumber", "hjkh6767khuhuk87987hhj");
        attributes.put("username", "username");
        attributes.put("mail", "email");
        attributes.put("name", "name");
        attributes.put("surname", "surname");
        attributes.put("password", "password");
        
        Object user = gsUserAttributesMapper.mapFromAttributes(attributes);
        assertTrue(user instanceof GSUser);
        assertEquals(true, ((GSUser)user).getId() instanceof Long);
        assertEquals(new Long(6767), ((GSUser)user).getId());
    }
}