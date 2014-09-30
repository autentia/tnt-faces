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

import java.util.ArrayList;
import java.util.List;

import com.autentia.tnt.faces.components.query.model.Operator;

public class ManyToOneProperty extends Property {

	static final Operator[] DEFAULT_MANY_TO_ONE_OPERATORS = BooleanProperty.DEFAULT_BOOLEAN_OPERATORS;

	static final Operator DEFAULT_MANY_TO_ONE_OPERATOR = Operator.EQUALS;

	private boolean noItemSelected;
	
	private String itemLabel;
	
	public ManyToOneProperty(Class<?> entityClass, String propertyFullPath, boolean editable) {
		super(entityClass, propertyFullPath, editable, DEFAULT_MANY_TO_ONE_OPERATORS, DEFAULT_MANY_TO_ONE_OPERATOR);
		allowedValues = new ArrayList();
	}
	
	@Override
	public void setAllowedValues(List<?> allowedValues) {
		super.setAllowedValues(allowedValues);
	}

	public void setNoItemSelected(boolean noItemSelected) {
		this.noItemSelected = noItemSelected;
	}

	public boolean isNoItemSelected() {
		return noItemSelected;
	}

	@Override
	protected String getInputTemplate() {
		return RENDERER_PATH + "inputManyToOne.xhtml";
	}
	
	public void setItemLabel(String itemLabel){
		this.itemLabel = itemLabel;
	}

	public String getItemLabel(){
		return itemLabel;
	}

}
