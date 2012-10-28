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

package com.autentia.tnt.faces.components.cache;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;

import javax.el.ELContext;
import javax.el.ValueExpression;

@FacesComponent(value="CacheComponent")
public class Cache extends UIComponentBase {
	
	private static final String RENDER_TYPE = "com.autentia.tnt.faces.components.cache.CacheRenderer";

	private static final String FAMILY = "com.autentia.tnt.faces.components";

	private static final String REGION_ATTR = "region";

	private static final String KEY_ATTR = "key";

	private static final String ENABLED_ATTR = "enabled";

	private boolean enabled = true;

	private String key;
	
	private String region;
	
	@Override
	public String getFamily() {
		return FAMILY;
	}
	
    @Override
    public String getRendererType() {
        return RENDER_TYPE;
    }

	public boolean isEnabled() {
	    final ValueExpression valueExpression = getValueExpression(ENABLED_ATTR);
	    if (valueExpression != null) {
	        final Boolean value = (Boolean) valueExpression.getValue(
	            getELContext());
	        return value.booleanValue();
	    } else {
	        return (this.enabled);
	    }
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getKey() {
	    final ValueExpression valueExpression = getValueExpression(KEY_ATTR);
	    if (valueExpression != null) {
	        final String value = (String) valueExpression.getValue(
	            getELContext());
	        return value;
	    } else {
	        return this.key;
	    }
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public String getRegion() {
	    final ValueExpression valueExpression = getValueExpression(REGION_ATTR);
	    if (valueExpression != null) {
	        final String value = (String) valueExpression.getValue(
	            getELContext());
	        return value;
	    } else {
	        return this.region;
	    }
	}

	public void setRegion(String region) {
		this.region = region;
	}


	@Override
	public String toString() {
		final StringBuilder strBuilder = new StringBuilder(30);
		strBuilder.append("Cache [enabled=").append(isEnabled());
		strBuilder.append(", key=").append(getKey());
		strBuilder.append(", region=").append(getRegion());
		strBuilder.append("]");
		return strBuilder.toString();
	}

	protected ELContext getELContext() {
		return getFacesContext().getELContext();
	}
}
