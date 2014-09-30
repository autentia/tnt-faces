/**
 * Copyright 2008 Autentia Real Business Solutions S.L. This file is part of Autentia WUIJA. Autentia WUIJA is free
 * software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, version 3 of the License. Autentia WUIJA is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details. You should have received a copy of
 * the GNU Lesser General Public License along with Autentia WUIJA. If not, see <http://www.gnu.org/licenses/>.
 */

package com.autentia.tnt.faces.components.query.widget;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.event.ValueChangeEvent;

import com.autentia.tnt.faces.components.JsfWidget;
import com.autentia.tnt.faces.components.property.Property;
import com.autentia.tnt.faces.components.query.model.Operator;
import com.autentia.tnt.faces.components.query.model.SimpleExpression;

/**
 * Widget que se encarga de pintar un único {@link SimpleExpression}. Una busqueda podrá estar compuesta por varios widgets de
 * esta clase.
 */
public class SimpleExpressionWidget extends JsfWidget {
	
	private static final long serialVersionUID = 1735052026550772597L;

	/**
	 * Esta clase envuelve el valor de un {@link SimpleExpression} para que se pueda asignar con un widget de edición de una
	 * propiedad. Estos widget esperan recibir un entidad y acceden a su propiedad por EL. Este wrapper tiene un mapa
	 * para simular ese comportamiento.
	 */
	private class CriterionWrapper extends AbstractMap<String, Object> implements Serializable{

		private static final long serialVersionUID = 436367929285676318L;

		private final int valueIndex;

		public CriterionWrapper(int valueIndex) {
			this.valueIndex = valueIndex;
		}

		@Override
		public Set<java.util.Map.Entry<String, Object>> entrySet() {
			final Set<java.util.Map.Entry<String, Object>> items = new HashSet<java.util.Map.Entry<String, Object>>();
			for (Object object : simpleExpression.getValues()) {
				items.add(new SimpleEntry(object,object));
			}
			return items;
		}

		@Override
		public Object get(Object key) {
			return simpleExpression.getValues().get(valueIndex);
		}

		@Override
		public Object put(String key, Object value) {
			simpleExpression.getValues().set(valueIndex, value);
			return null;
		}
	}

	static final int NO_PROPERTY_SELECTED = -1;

	/** Criterio donde se guardan los valores introducidos por le usuario. */
	private final SimpleExpression simpleExpression;

	private final List<Object> criterionValueWrappers = new ArrayList<Object>();

	/** Lista de <code>SelectItem</code> para mostrar al usuario los operadores de la propiedad seleccionada. */
	private List<Operator> operators;

	/** El conjuto de propiedades que el usuario podrá seleccionar con este {@link SimpleExpressionWidget}. */
	private final List<Property> properties;

	/** Nombre de la propiedad seleccionada. */
	private Property selectedProperty;

	public SimpleExpressionWidget(SimpleExpression simpleExpression, Property[] properties) {
		this.simpleExpression = simpleExpression;
		this.properties = Arrays.asList(properties);
		for (int i = 0; i < properties.length; i++) {
			if (properties[i].equals(simpleExpression)) {
				selectedProperty = properties[i];
				break;
			}
		}
		prepareValueWrappers();
		prepareOperators();
	}

	/**
	 * Devuelve una lista de <code>SelectItem</code> con los posibles operadores para este criterio de búsqueda, para
	 * pintarle al usuario un desplegable.
	 * 
	 * @return una lista de <code>SelectItem</code> con los posibles operadores para este criterio de búsqueda.
	 */
	public List<Operator> getOperators() {
		return operators;
	}

	/**
	 * Devuelve la lista de nombres de propiedades por las que se puede buscar, como una lista de
	 * <code>SelectItem</code>.
	 * 
	 * @return la lista de nombres de propiedades por las que se puede buscar, como una lista de <code>SelectItem</code>
	 *         .
	 */
	public List<Property> getProperties() {
		return properties;
	}

	@Override
	public String getRendererPath() {
		return RENDERER_PATH + "simpleExpression.xhtml";
	}

	/**
	 * Devuelve el operador actualmente seleccionado.
	 * 
	 * @return el operador actualmente seleccionado.
	 */
	public Operator getSelectedOperator() {
		return simpleExpression.getOperator();
	}

	public Property getSelectedProperty() {
		return selectedProperty;
	}

	/**
	 * Devuelve una lista con los valores introducidos por el usuario para hacer la busqueda.
	 * 
	 * @return una lista con los valores introducidos por el usuario para hacer la busqueda.
	 */
	public List<Object> getValues() {
		return criterionValueWrappers;
	}

	public void operatorChangeListener(ValueChangeEvent event) {
		final Operator newOperator = (Operator)event.getNewValue();
		simpleExpression.setOperator(newOperator);
		prepareValueWrappers();

		// XXX JsfUtils.facesContextNullSafeRenderResponse();
	}

	/**
	 * Teniendo en cuenta la propiedad seleccionada, calcula los select items para mostrar el desplegable con los
	 * operadores permitidos para el tipo de la propiedad seleccionada.
	 */
	private void prepareOperators() {
		if (selectedProperty != null) {
			operators = Arrays.asList(selectedProperty.getOperators());
		} else {
			operators = null;
		}
	}

	/**
	 * Se preparan los {@link CriterionWrapper} en función del numero de operandos necesarios para el operador
	 * seleccionado
	 */
	private void prepareValueWrappers() {
		if (simpleExpression.getValues().size() != criterionValueWrappers.size()) {
			criterionValueWrappers.clear();
			for (int i = 0; i < simpleExpression.getValues().size(); i++) {
				criterionValueWrappers.add(new CriterionWrapper(i));
			}
		}
	}

	public void propertyChangeListener(ValueChangeEvent event) {
		this.selectedProperty = ((Property) event.getNewValue());

		if (selectedProperty == null) {
			simpleExpression.setProperty(null);
		} else {
			if(selectedProperty.isFindByFullPath()){
				simpleExpression.setProperty(selectedProperty.getFullPath());
			}
			else{
				simpleExpression.setProperty(selectedProperty.getFirstPropertyName());
			}
			simpleExpression.setOperator(selectedProperty.getDefaultCriterionOperator());
		}
		prepareValueWrappers();
		prepareOperators();

		// RendererUtils.clearComponentAndRenderResponse(event.getComponent(), 1);
	}

	/**
	 * Todo el tratamiento se hace en el listener de cambio de valor. El setter no hace nada porque puede haber partial
	 * submit y no nos interesa setera la propiedad constantemente (podría tener efectos secundarios como hacer un clear
	 * de los valores porque el criterio se cree que está cambiado de propiedad).
	 * 
	 * @param operator el nuevo operador seleccionado.
	 */
	public void setSelectedOperator(Operator operator) {
		// Todo el tratamiento se hace en el listener de cambio de valor.
	}

	/**
	 * Asigna la propiedad seleccionada 
	 * @param selectedProperty la propiedad seleccionada.
	 */
	public void setSelectedProperty(Property selectedProperty) {
		this.selectedProperty = selectedProperty;
	}

}