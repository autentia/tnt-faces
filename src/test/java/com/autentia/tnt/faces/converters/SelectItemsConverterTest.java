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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SelectItemsConverterTest {

	@Mock
	private UIComponent uiComponent;

	@Mock
	private FacesContext facesContext;

	private static SelectItemsConverter converter;

	@BeforeClass
	public static void setUpBeforeTests() {
		converter = spy(new SelectItemsConverter());
	}
	
	@Test
	public void shoulReturnStringValueWhenGetAsString(){
		final Point fpoint = new Point(1,2);
		final Point spoint = new Point(3,4);
		// doReturn(Arrays.asList(new Point[]{fpoint,spoint})).when(converter).getObjectsFromUISelectItemsComponent(uiComponent);
		String value = converter.getAsString(facesContext, uiComponent, spoint);
		assertEquals("1", value);
		value = converter.getAsString(facesContext, uiComponent, fpoint);
		assertEquals("0", value);
	}
	
	@Test
	public void shoulReturnObjectValueWhenGetAsObject(){
		final Point fpoint = new Point(1,2);
		final Point spoint = new Point(3,4);
		final List<Point> points = Arrays.asList(fpoint, spoint);
		final List<UIComponent> childrens = new ArrayList<UIComponent>();
		final UISelectItems selectItems = mock(UISelectItems.class);
		doReturn(points).when(selectItems).getValue();
		childrens.add(selectItems);
		when(uiComponent.getChildren()).thenReturn(childrens);
		Object value = converter.getAsObject(facesContext, uiComponent, "1");
		assertEquals(spoint, value);
		value = converter.getAsObject(facesContext, uiComponent, "0");
		assertEquals(fpoint, value);
	}
	

	@Test
	public void shoulReturnNullWhenValueIsNegative(){
		Object value = converter.getAsObject(facesContext, uiComponent, "-1");
		assertNull(value);
	}
	
	@Test
	public void shoulReturnVoidWhenValueIsNull(){
		String value = converter.getAsString(facesContext, uiComponent, null);
		assertEquals("",value);
	}
	
	public class Point {

		private int x;
		
		private int y;
		
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}
		
	}
}
