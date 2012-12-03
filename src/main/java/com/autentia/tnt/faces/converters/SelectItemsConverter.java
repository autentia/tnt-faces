/**
 * Copyright 2012 Autentia Real Business Solutions S.L.
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

package com.autentia.tnt.faces.converters;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesConverter("selectItemsConverter")
public class SelectItemsConverter implements Converter {
	private static final Logger LOG = LoggerFactory.getLogger(SelectItemsConverter.class);
	
	private static final String KEY_PREFIX = "com.autentia.tnt.faces.converters.SelectItemsConverter";
	private static final String EMPTY = "";

	@SuppressWarnings("unchecked")
	private Map<String, Object> getViewMap(FacesContext facesContext, UIComponent component) {
	    final Map<String, Object> viewMap = getViewMapFromViewRoot(facesContext);
	    final String key = getUniqueKey(component);
	    Map<String, Object> idMap = (Map<String,Object>) viewMap.get(key);
	    if (idMap == null) {
	        idMap = new HashMap<String, Object>();
	        viewMap.put(key, idMap);
	    }
	    LOG.trace("viewMap {}", idMap);
	    return idMap;
	}

	Map<String, Object> getViewMapFromViewRoot(FacesContext facesContext) {
		return facesContext.getViewRoot().getViewMap();
	}
	
	String getUniqueKey(UIComponent component) {
		final StringBuilder key = new StringBuilder(KEY_PREFIX);
		key.append(".").append(component.getClientId());
		return key.toString();
	}

	public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
		LOG.trace("String value: {}", value);
		if (value == null) {
			return null;
		}
		final Object result = getViewMap(facesContext, component).get(value);
		LOG.trace("Object result: {}", result);
	    return result;
	}

	public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
		LOG.trace("Object value: {}", value);
	    if (value == null) {
	        return EMPTY;
	    }
	    final Serializable id = UUID.randomUUID().toString();
	    getViewMap(facesContext, component).put(id.toString(), value);
	    
	    LOG.trace("String result: {}", id);
	    return id.toString();
	}

}
