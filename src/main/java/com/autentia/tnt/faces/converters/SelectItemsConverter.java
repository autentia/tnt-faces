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

import java.util.Collections;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesConverter("selectItemsConverter")
public class SelectItemsConverter implements Converter {

	private static final Logger LOG = LoggerFactory.getLogger(SelectItemsConverter.class);

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		LOG.trace("String value: {}", value);
		final int index = Integer.parseInt(value);
		if (index == -1) {
			return null;
		}

		return getObjectsFromUISelectItemsComponent(component).get(index);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String string;
		LOG.trace("Object value: {}", value);
		if(value == null){
			string="";
		}else{
			final List<?> objects = getObjectsFromUISelectItemsComponent(component);
			string = String.valueOf(objects.indexOf(value));
		}
		return string;
	}

	List<?> getObjectsFromUISelectItemsComponent(UIComponent component) {
		List<?> objects = Collections.emptyList();
		for (UIComponent child : component.getChildren()) {
			if (UISelectItems.class.isAssignableFrom(child.getClass())) {
				objects = (List<?>)((UISelectItems)child).getValue();
			}
		}
		LOG.trace("Objects: {}", objects);
		return objects;
	}

}
