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

package com.autentia.tnt.faces.components.cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doReturn;

import javax.el.ELContext;
import javax.el.ValueExpression;

import org.junit.Before;
import org.junit.Test;


public class CacheTest {
	
	private static final String REGION_NAME = "regionName";
	private static final String KEY_NAME = "keyName";
	private Cache cache;
	
	@Before
	public void init(){
		cache = spy(new Cache());
	}
	
	@Test
	public void shouldSetFamilyAndRenderType(){
		assertNotNull(cache.getFamily());
		assertNotNull(cache.getRendererType());
	}
	
	@Test
	public void shouldHasGettersAndSetters(){
		cache.setEnabled(true);
		assertTrue(cache.isEnabled());
		cache.setKey(KEY_NAME);
		assertEquals(KEY_NAME,cache.getKey());
		cache.setRegion(REGION_NAME);
		assertEquals(REGION_NAME,cache.getRegion());
	}

	@Test
	public void enabledMethodShouldEvaluateExpressions(){
		final ValueExpression valueExpression = mock(ValueExpression.class);
		when(cache.getValueExpression("enabled")).thenReturn(valueExpression);
		doReturn(mock(ELContext.class)).when(cache).getELContext();
		when(valueExpression.getValue(any(ELContext.class))).thenReturn(false);
		assertFalse(cache.isEnabled());
		
	}
	
	@Test
	public void regionMethodShouldEvaluateExpressions(){
		final ValueExpression valueExpression = mock(ValueExpression.class);
		when(cache.getValueExpression("region")).thenReturn(valueExpression);
		doReturn(mock(ELContext.class)).when(cache).getELContext();
		when(valueExpression.getValue(any(ELContext.class))).thenReturn("regionName");
		assertEquals("regionName",cache.getRegion());
	}
	
	@Test
	public void keyMethodShouldEvaluateExpressions(){
		final ValueExpression valueExpression = mock(ValueExpression.class);
		when(cache.getValueExpression("key")).thenReturn(valueExpression);
		doReturn(mock(ELContext.class)).when(cache).getELContext();
		when(valueExpression.getValue(any(ELContext.class))).thenReturn("keyName");
		assertEquals("keyName",cache.getKey());
	}
	
	@Test
	public void shouldHasADescriptiveToStringMethod(){
		cache.setEnabled(false);
		cache.setKey(KEY_NAME);
		cache.setRegion(REGION_NAME);
		assertEquals("Cache [enabled=false, key=keyName, region=regionName]", cache.toString());
	}
	
}
