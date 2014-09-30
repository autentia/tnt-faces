/**
 * Copyright 2008 Autentia Real Business Solutions S.L.
 * 
 * This file is part of autentia-util.
 * 
 * autentia-util is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 * 
 * autentia-util is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with autentia-util. If not, see <http://www.gnu.org/licenses/>.
 */

package com.autentia.tnt.faces.utils;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class RendererUtils {

	private static final Logger LOG = LoggerFactory.getLogger(RendererUtils.class);
	
	private RendererUtils() {
		// Para cumplir con el patrón singleton.
	}
	
	/**
	 * Borra el árbol de componentes y salta a la fase de render response (la última fase). Este método es útil, por
	 * ejemplo, cuando estamos modificando los valores de un combo en función de un cambio de valor de otro combo. En
	 * estas ocasiones, muchas veces es necesario limpiar el arbol de componentes porque sino, al haber cambiado los
	 * valores del combo, ya no es capaz de pintarlo correctamente.
	 * 
	 * @param component el componente a partir del cual se va a borrar el árbol de componentes.
	 * @param parentLevel el nivel del padre desde donde se quiere borrar. 0 sería sólo a partir del propio
	 *            <code>component</code>, 1 sería a partir de su padre, 2 a partir de su abuelo, ...
	 */
	public static void clearComponentAndRenderResponse(UIComponent component, int parentLevel) {
		// A veces el componente es null, por ejemplo cuando estamos haciendo test
		if (component == null) {
			return;
		}
		
		UIComponent paretnToClear = component;
		for (int i = 0; i < parentLevel && paretnToClear.getParent() != null; i++) {
			paretnToClear = paretnToClear.getParent();
		}
		if (LOG.isDebugEnabled()) {
			final StringBuilder msg = new StringBuilder();
			componentsToClearToString(paretnToClear, msg);
			LOG.debug("Clearing components: " + msg.toString());
		}
		paretnToClear.getChildren().clear();

		facesContextNullSafeRenderResponse();
	}

	public static void facesContextNullSafeRenderResponse() {
		final FacesContext facesContext = FacesContext.getCurrentInstance(); 
		if (facesContext != null) {
			facesContext.renderResponse();
		}
	}
	
	/**
	 * Método para componer una cadena con todos los componentes del árbol de JSF que se van a borrar.
	 * 
	 * @param parent componente padre a partir del cual se va a hacer el clear.
	 * @param msg StringBuilder donde se va componiendo el mensaje.
	 */
	private static void componentsToClearToString(UIComponent parent, StringBuilder msg) {
		for (UIComponent child : parent.getChildren()) {
			msg.append(child.getId());
			if (!child.getChildren().isEmpty()) {
				msg.append('[');
				componentsToClearToString(child, msg);
				msg.append(']');
			}
			msg.append(' ');
		}
	}

	public static String setValue2ValueExpression(final FacesContext context, final String expression) {
		final FacesContext facesContext = FacesContext.getCurrentInstance();
		final ELContext elContext = facesContext.getELContext();
			 
		final ValueExpression targetExpression =
				facesContext.getApplication().getExpressionFactory().createValueExpression(elContext, expression, Object.class);
		return (String) targetExpression.getValue(context.getELContext());
	}
	
}