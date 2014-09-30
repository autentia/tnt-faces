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
package com.autentia.tnt.faces.components;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;

import com.autentia.tnt.faces.components.notification.EventProcessor;
import com.autentia.tnt.faces.components.notification.WidgetEvent;
import com.autentia.tnt.faces.components.notification.WidgetListener;
import com.autentia.tnt.faces.utils.SecurityContext;

public abstract class JsfWidget implements Serializable {

	protected static final String RENDERER_PATH = "../../widgets/";

	private boolean disabled = false;

	private final EventProcessor eventProcessor = new EventProcessor();

	private String id;

	private String[] renderOnUserRoles;

	private boolean visible = true;

	public JsfWidget() {
		id = "id" + ObjectUtils.identityToString(this);
	}

	public void addListener(WidgetListener widgetListener) {
		eventProcessor.addListener(widgetListener);
	}

	protected void fireEvent(WidgetEvent widgetEvent) {
		eventProcessor.fireEvent(widgetEvent);
	}

	public String getId() {
		return id;
	}

	protected List<WidgetListener> getListeners() {
		return eventProcessor.getListeners();
	}

	public abstract String getRendererPath();

	public boolean isDisabled() {
		return disabled;
	}

	/**
	 * Indica su el widget es visible o no. La visibilidad del widget también depende de si el usuario que quiere
	 * mostrarlo está en un rol determiando.
	 * 
	 * @return <code>true</code> si el widget es visible para el usuario que pretende mostrarlo, <code>false</code> en
	 *         otro caso.
	 */
	public boolean isVisible() {
		return visible && SecurityContext.isUserInRole(renderOnUserRoles);
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setRenderOnUserRole(String... renderOnUserRoles) {
		this.renderOnUserRoles = renderOnUserRoles;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	protected boolean someoneIsListening() {
		return eventProcessor.someoneIsListening();
	}

	protected boolean someoneIsListening(Class<? extends WidgetListener> listenerClass) {
		return eventProcessor.someoneIsListening(listenerClass);
	}
}
