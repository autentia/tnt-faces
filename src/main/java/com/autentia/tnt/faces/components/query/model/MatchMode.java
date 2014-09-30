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

package com.autentia.tnt.faces.components.query.model;

/**
 * Indica como se de beben 'concatenar' los criterios de búsqueda a la hora de hacer la búsqueda. Es decir el resultado
 * debe cumplir con todos los criterios (ALL/AND) o el resultado debe cumplir con alguno de los criterios (ANY/OR).
 */
public enum MatchMode {
	ALL(" and "), ANY(" or ");
	
	private final String toHql;
	
	private MatchMode(String toHql) {
		this.toHql = toHql;
	}
	
	public String toHql() {
		return toHql;
	}
}
