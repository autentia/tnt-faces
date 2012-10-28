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

import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesRenderer(componentFamily="com.autentia.tnt.faces.components", rendererType="com.autentia.tnt.faces.components.cache.CacheRenderer")
public class CacheRenderer extends Renderer {

	private static final Logger LOG = LoggerFactory.getLogger(CacheRenderer.class);

	protected CacheFactory getCacheProvider() {
		return FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{cacheFactory}",CacheFactory.class);
	}
	
	@Override
	public boolean getRendersChildren() {
		return true;
	}

	@Override
	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
		
		final CacheFactory cacheProvider = getCacheProvider();
		LOG.trace("Using {} as CacheProvider implementation.", cacheProvider);
		
        final Cache cache = (Cache) component;
        LOG.trace("rendering component '{}'", cache);
		
        if (!cache.isEnabled()) {
        	renderChildren(context, component);
        	return;
        }
        	
		final ResponseWriter writer = context.getResponseWriter();

		final String key = cache.getKey();
		
		if (isKeyCached(cacheProvider, cache, key)) {
			renderFromCache(cacheProvider, cache, writer, key);
			return;
		}
		
		renderFromEncodeChildrenAndCache(context, component, cacheProvider,
				cache, writer, key);
	}

	private boolean isKeyCached(final CacheFactory cacheProvider,
			final Cache cache, final String key) {
		return cacheProvider.getCache(cache.getRegion()).containsKey(key);
	}
	
	private void renderFromCache(final CacheFactory cacheProvider,
			final Cache cache, final ResponseWriter writer, final String key)
			throws IOException {
		LOG.trace("rendering from cache for key: {}", key);
		final String cachedContent = (String) cacheProvider.getCache(cache.getRegion()).get(key);
		writer.write(cachedContent);
	}
	
	private void renderFromEncodeChildrenAndCache(FacesContext context,
			UIComponent component, final CacheFactory cacheProvider,
			final Cache cache, final ResponseWriter writer, final String key)
			throws IOException {
		LOG.trace("rendering contents for key: {}", key);
		final StringWriter stringWriter = new StringWriter();
		final ResponseWriter cachingResponseWriter = writer
				.cloneWithWriter(stringWriter);
		context.setResponseWriter(cachingResponseWriter);
		renderChildren(context, component);
		context.setResponseWriter(writer);
		final String output = stringWriter.getBuffer().toString();
		LOG.trace("writing contents {}", output);
		writer.write(output);
		cacheProvider.getCache(cache.getRegion()).put(key, output);
	}

    private void renderChildren(FacesContext facesContext,
			UIComponent component) throws IOException {
		if (component.getChildCount() > 0) {
			for (Iterator<UIComponent> it = component.getChildren().iterator(); it.hasNext();) {
				final UIComponent child = (UIComponent) it.next();
				child.encodeAll(facesContext);
			}
		}
	}
    
}
