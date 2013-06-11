/*
 * $ Header: it.geosolutions.geofence.gui.server.service.SecurityManager,v. 0.1 25-gen-2011 11.23.49 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 25-gen-2011 11.23.49 $
 *
 * ====================================================================
 *
 * Copyright (C) 2007 - 2011 GeoSolutions S.A.S.
 * http://www.geo-solutions.it
 *
 * GPLv3 + Classpath exception
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.
 *
 * ====================================================================
 *
 * This software consists of voluntary contributions made by developers
 * of GeoSolutions.  For more information on GeoSolutions, please see
 * <http://www.geo-solutions.it/>.
 *
 */
package it.geosolutions.geofence.gui.server.service;

//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.GrantedAuthorityImpl;

/**
 * The Class SecurityManager.
 */
public class SecurityManager
{

    // Logger LOGGER = LogManager.getLogger(SecurityManager.class.getName());
    //
    // private List<String> authorizedRoles;
    //
    // private MemberServiceInternal memberService;
    //
    // /**
    // * @param authorizedRoles the authorizedRoles to set
    // */
    // public void setAuthorizedRoles(List<String> authorizedRoles) {
    // this.authorizedRoles = authorizedRoles;
    // }
    //
    // /**
    // * @return the authorizedRoles
    // */
    // public List<String> getAuthorizedRoles() {
    // return authorizedRoles;
    // }
    //
    // /**
    // * @param memberService the memberService to set
    // */
    // public void setMemberService(MemberServiceInternal memberService) {
    // this.memberService = memberService;
    // }
    //
    // /**
    // * @return the memberService
    // */
    // public MemberServiceInternal getMemberService() {
    // return memberService;
    // }
    //
    // public Collection<GrantedAuthority> attemptAuthentication(String username, String password)
    // throws Exception {
    // /**
    // * Spring Security
    // */
    // /*AuthenticationManager am = (AuthenticationManager)
    // ctx.getBean("OpenSDIAuthenticationManager");
    //
    // try {
    // UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username,
    // password);
    // Authentication res = am.authenticate(auth);
    // return res.getAuthorities();
    // }catch(Exception e) {
    // log.warn("Authentication Failed: " + e.getLocalizedMessage());
    // return new GrantedAuthority[]{new GrantedAuthorityImpl("BAD_CREDENTIALS")};
    // } */
    //
    // /**
    // * DG Member Service
    // */
    // CredentialDto credentials = new CredentialDto();
    // credentials.setUsername(username);
    // credentials.setPassword(password);
    // // Do not set connect id - needs to be empty for Member Services
    // //credentials.setConnectId(username);
    //
    // final Collection<GrantedAuthority> memberRoles = new ArrayList<GrantedAuthority>();
    // try {
    // boolean authorized = false;
    //
    // MemberDto member = memberService.login(credentials);
    // for (Role role : member.getRoles()) {
    // LOGGER.info("Checking role " + role.getName().toUpperCase() + " for member " +
    // member.getUsername());
    // if (authorized = getAuthorizedRoles().contains(role.getName().toUpperCase())) {
    // break;
    // }
    // }
    //
    // if (authorized) {
    // LOGGER.info("Authorized member " + member.getUsername());
    // for (Role role : member.getRoles()) {
    // memberRoles.add(new GrantedAuthorityImpl(role.getName().toUpperCase()));
    // }
    //
    // return memberRoles;
    // } else {
    // LOGGER.info("Invalid role for member " + member.getUsername());
    // memberRoles.add(new GrantedAuthorityImpl("BAD CREDENTIALS"));
    // return memberRoles;
    // }
    // }
    // catch (Exception e) {
    // LOGGER.severe("invalid login- memberService: " + e);
    // LOGGER.warning("Authentication Failed: " + e.getLocalizedMessage());
    // memberRoles.add(new GrantedAuthorityImpl("BAD CREDENTIALS"));
    // return memberRoles;
    // }
    // }
}
