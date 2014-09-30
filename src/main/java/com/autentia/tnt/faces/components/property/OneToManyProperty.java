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

import java.util.Collections;
import java.util.List;

import com.autentia.tnt.faces.components.query.model.Operator;


public class OneToManyProperty<T> extends Property {

	protected OneToManyProperty(Class<?> entityClass, String propertyFullPath,
			boolean editable, Operator[] operators, Operator defaultOperator) {
		super(entityClass, propertyFullPath, editable, operators, defaultOperator);
		throw new UnsupportedOperationException();
	}

/* XXX
	private final WidgetBar widgetBar = new WidgetBar();

	private final PagedDataTable<T> dataTable;

	private final Property[] properties;

	public OneToManyProperty(Class<T> entityClass, String propertyFullPath, String... propertyNames) {
		this(entityClass, propertyFullPath, false, propertyNames);
	}

	public OneToManyProperty(Class<T> entityClass, String propertyFullPath, boolean editable, String... propertyNames) {
		super(entityClass, propertyFullPath, editable, null, null);

		properties = PropertyFactory.build(entityClass, editable, propertyNames);
		final List<T> emptyList = Collections.emptyList();
		dataTable = new PagedDataTable<T>(properties, emptyList);
	}
	
	public void addButton(Button button) {
		widgetBar.addWidget(button);
	}

	public void addSelecionListener(SelectionListener selectionListener) {
		dataTable.addListener(selectionListener);
	}

	public WidgetBar getWidgetBar() {
		return widgetBar;
	}

	public PagedDataTable<T> getDataTable() {
		return dataTable;
	}


	@SuppressWarnings("unchecked")
	@Override
	public void setAllowedValues(List<?> allowedValues) {
		this.allowedValues = allowedValues;
		dataTable.setEntities((List<T>)allowedValues);
	}

	public Property[] getProperties() {
		return properties;
	}

	@Override
	String getOutputTemplate() {
		return getInputTemplate();
	}
*/

	@Override
	protected String getInputTemplate() {
		return RENDERER_PATH + "inputOneToMany.xhtml";
	}

}
