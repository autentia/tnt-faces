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

package com.autentia.tnt.faces.components.notification;


public class ActionEvent extends WidgetEvent {

	private static final long serialVersionUID = 3458535030540751466L;

	public ActionEvent(Object source) {
		super(source);
	}

	@Override
	public boolean isAppropriateListener(WidgetListener listener) {
		return listener instanceof ActionListener;
	}

	@Override
	public void processListener(WidgetListener listener) {
		((ActionListener)listener).processAction(this);
	}

}
