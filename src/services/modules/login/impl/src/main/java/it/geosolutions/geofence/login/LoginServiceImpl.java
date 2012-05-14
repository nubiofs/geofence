/*
 *  Copyright (C) 2007 - 2010 GeoSolutions S.A.S.
 *  http://www.geo-solutions.it
 *
 *  GPLv3 + Classpath exception
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package it.geosolutions.geofence.login;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import it.geosolutions.geofence.api.AuthProvider;
import it.geosolutions.geofence.api.dto.Authority;
import it.geosolutions.geofence.api.dto.GrantedAuths;
import it.geosolutions.geofence.api.exception.AuthException;
import it.geosolutions.geofence.login.util.GrantAll;
import it.geosolutions.geofence.login.util.SessionManager;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;


/**
 * @author ETj (etj at geo-solutions.it)
 */
public class LoginServiceImpl implements LoginService, InitializingBean, DisposableBean
{

    private static final Logger LOGGER = Logger.getLogger(LoginServiceImpl.class);

    // private List<String> authorizedRoles;

    private AuthProvider authProvider = new GrantAll(); // this provider should be overridden by
                                                        // injecting a true implementation.

    private SessionManager sessionManager;

    public LoginServiceImpl()
    {
        LOGGER.info("Creating " + getClass().getSimpleName() + " instance");
    }

    @Override
    public void afterPropertiesSet()
    {
        LOGGER.debug("afterPropertiesSet()");
    }

    @Override
    public void destroy() throws Exception
    {
        LOGGER.debug("destroy()");
    }

    @PostConstruct
    public void postConstruct()
    {
        LOGGER.debug("postConstruct()");
    }

    @PreDestroy
    public void preDestroy()
    {
        LOGGER.debug("preDestroy()");

    }

    // ==========================================================================
    // Service methods
    // ==========================================================================

    @Override
    public String login(String username, String password, String encryptedPassword) throws AuthException
    {
        LOGGER.info("LOGIN REQUEST FOR " + username);

        // MessageContext msgCtxt = webServiceContext.getMessageContext();
        // HttpServletRequest req = (HttpServletRequest)msgCtxt.get(MessageContext.SERVLET_REQUEST);
        // String clientIP = req.getRemoteAddr();
        //
        // LOGGER.info("LOGIN REQUEST FOR " + username + " @ " + clientIP);

        if (username == null)
        {
            throw new AuthException("Null username");
        }
        else
        {
            try
            {
                GrantedAuths ga = authProvider.login(username, password, encryptedPassword);
                if (!ga.getAuthorities().contains(Authority.LOGIN))
                {
                    LOGGER.warn("Login not granted to user [" + username + "]");
                    throw new AuthException("User " + username + " can't log in");
                }

                String token = sessionManager.createSession(username, ga);

                return token;
            }
            catch (AuthException ex)
            {
                LOGGER.warn("Authentication Failed for user [" + username + "]: " +
                    ex.getLocalizedMessage());
                throw new AuthException("Authentication error", ex);
            }
        }
    }

    @Override
    public void logout(String token)
    {
        LOGGER.info("LOGOUT:" + token);
        sessionManager.closeSession(token);
    }

    @Override
    public GrantedAuths getGrantedAuthorities(String token)
    {
        LOGGER.info("getGrantedAuthorities:" + token);

        return sessionManager.getGrantedAuthorities(token);
    }

    // ==========================================================================
    // Setters
    // ==========================================================================

    public void setAuthProvider(AuthProvider authProvider)
    {
        LOGGER.info("Setting AuthProvider: " + authProvider.getClass());
        this.authProvider = authProvider;
    }

    public void setSessionManager(SessionManager sessionManager)
    {
        this.sessionManager = sessionManager;
    }
}
