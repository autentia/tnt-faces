/**

 * Copyright 2014 Autentia Real Business Solutions S.L.
 * 
 * This file is part of the code that develops in Autentia.
 * 
 * This code is a free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 * 
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 */

package com.autentia.tnt.faces.utils;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class SecurityContext {

	public static boolean isUserInAllRoles(String... roles) {
		return matchUserRoles(true, roles);
	}

	public static boolean isUserInRole(String... roles) {
		return matchUserRoles(false, roles);
	}

	/**
	 * Internal function to check if the current user is in one or all roles listed
	 * <p>
	 * If the list of roles is null or empty, this method returns true.
	 * 
	 * @param inclusive if <code>true</code>, the user must to be in all the roles, if <code>false</code> the user must
	 *            be in at least one of the roles
	 * @param roles the list of roles to check
	 * @return <code>true</code> if the user match the condition or the list of roles is empty, <code>false</code> in
	 *         other case.
	 */
	private static boolean matchUserRoles(boolean inclusive, String... roles) {
		if (roles == null || roles.length == 0) {
			return true;
		}
		boolean isInRole = false;
		final ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
		for (String role : roles) {
			isInRole = ctx.isUserInRole(role);
			if ((inclusive && !isInRole) || (!inclusive && isInRole)) {
				break;
			}
		}
		return isInRole;
	}
	
}
