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

package com.autentia.tnt.faces.components.property;

import com.autentia.tnt.faces.components.query.model.Operator;

public class TextProperty extends Property {

	static final Operator[] DEFAULT_TEXT_OPERATORS = new Operator[] { Operator.IS_BLANK, Operator.IS_NOT_BLANK,
			Operator.EQUALS, Operator.NOT_EQUALS, Operator.CONTAINS, Operator.NOT_CONTAIN, Operator.STARTS_WITH,
			Operator.ENDS_WITH, Operator.GREATER, Operator.GREATER_EQUAL, Operator.LESS, Operator.LESS_EQUAL,
			Operator.BETWEEN, Operator.NOT_BETWEEN };

	static final Operator DEFAULT_TEXT_OPERATOR = Operator.CONTAINS;

	public TextProperty(Class<?> entityClass, String propertyFullPath, boolean editable) {
		super(entityClass, propertyFullPath, editable, DEFAULT_TEXT_OPERATORS, DEFAULT_TEXT_OPERATOR);
		escapable = false;
	}

	@Override
	protected String getInputTemplate() {
		return RENDERER_PATH + "inputText.xhtml";
	}
	
}
