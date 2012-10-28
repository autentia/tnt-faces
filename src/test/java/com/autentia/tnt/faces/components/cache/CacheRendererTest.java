package com.autentia.tnt.faces.components.cache;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Matchers.any;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.junit.Before;
import org.junit.Test;

public class CacheRendererTest {

	private static final String HOME_CONTENT = "contenido de la cache";

	private static final String KEY_NAME = "news";

	private static final String REGION_NAME = "home";

	private CacheRenderer cacheRenderer;
	
	private CacheFactory cacheFactory;
	
	@Before
	public void init(){
		cacheRenderer = spy(new CacheRenderer());
		assertTrue(cacheRenderer.getRendersChildren());
		cacheFactory = mock(CacheFactory.class);
		doReturn(cacheFactory).when(cacheRenderer).getCacheProvider();
	}
	
	@Test
	public void shouldRenderFromCache() throws IOException{
		final Cache cache = mock(Cache.class);
		when(cache.isEnabled()).thenReturn(true);
		when(cache.getRegion()).thenReturn(REGION_NAME);
		when(cache.getKey()).thenReturn(KEY_NAME);
		final Map<String,Object> keys = mock(Map.class);
		when(keys.containsKey(KEY_NAME)).thenReturn(true);
		when(keys.get(KEY_NAME)).thenReturn(HOME_CONTENT);
		when(cacheFactory.getCache(REGION_NAME)).thenReturn(keys);
		final FacesContext facesContext = mock(FacesContext.class);
		final ResponseWriter responseWriter = mock(ResponseWriter.class);
		when(facesContext.getResponseWriter()).thenReturn(responseWriter);
		cacheRenderer.encodeChildren(facesContext, cache);
		verify(responseWriter).write(HOME_CONTENT);
	}

	@Test
	public void shouldNotRenderFromCacheIfDisabled() throws IOException{
		final Cache cache = mock(Cache.class);
		when(cache.isEnabled()).thenReturn(false);
		final FacesContext facesContext = mock(FacesContext.class);
		final List<UIComponent> childrens = new ArrayList<UIComponent>();
		final UIComponent child = mock(UIComponent.class);
		childrens.add(child);
		when(cache.getChildCount()).thenReturn(1);
		when(cache.getChildren()).thenReturn(childrens);
		cacheRenderer.encodeChildren(facesContext, cache);
		verify(child).encodeAll(facesContext);
	}
	
	@Test
	public void shouldNotRenderFromCacheIfNotFoundIn() throws IOException{
		final Cache cache = mock(Cache.class);
		when(cache.isEnabled()).thenReturn(true);
		when(cache.getRegion()).thenReturn(REGION_NAME);
		when(cache.getKey()).thenReturn(KEY_NAME);
		final Map<String,Object> keys = mock(Map.class);
		when(keys.containsKey(KEY_NAME)).thenReturn(false);
		when(cacheFactory.getCache(REGION_NAME)).thenReturn(keys);
		final FacesContext facesContext = mock(FacesContext.class);
		final List<UIComponent> childrens = new ArrayList<UIComponent>();
		final UIComponent child = mock(UIComponent.class);
		childrens.add(child);
		when(cache.getChildCount()).thenReturn(1);
		when(cache.getChildren()).thenReturn(childrens);
		final ResponseWriter responseWriter = mock(ResponseWriter.class);
		when(facesContext.getResponseWriter()).thenReturn(responseWriter);
		cacheRenderer.encodeChildren(facesContext, cache);
		verify(facesContext,times(2)).setResponseWriter(any(ResponseWriter.class));
		verify(keys).put(KEY_NAME, "");
	}
}
