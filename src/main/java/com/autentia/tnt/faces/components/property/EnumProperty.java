/**
 * Copyright 2008 Autentia Real Business Solutions S.L.
 * 
 * This file is part of Autentia WUIJA.
 * 
 * Autentia WUIJA is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 * 
 * Autentia WUIJA is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Autentia WUIJA. If not, see <http://www.gnu.org/licenses/>.
 */

package com.autentia.tnt.faces.components.property;

import java.util.Arrays;

import com.autentia.tnt.faces.converters.I18NEnumConverter;
import com.autentia.tnt.faces.utils.ReflectionUtils;

public class EnumProperty extends ManyToOneProperty {

	public EnumProperty(Class<?> entityClass, String propertyFullPath, boolean editable) {
		super(entityClass, propertyFullPath, editable);
		Class<?> fieldClass = ReflectionUtils.getPropertyClass(entityClass, getFirstPropertyName());
		setAllowedValues(Arrays.asList(fieldClass.getEnumConstants()));
		setConverter(new I18NEnumConverter());
	}

	@Override
	protected String getInputTemplate() {
		return RENDERER_PATH + "inputEnum.xhtml";
	}
}
