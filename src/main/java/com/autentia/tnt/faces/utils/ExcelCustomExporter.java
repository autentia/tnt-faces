package com.autentia.tnt.faces.utils;

import java.io.IOException;
import java.io.StringWriter;

import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.extensions.component.exporter.ExcelExporter;

/**
 * <code>ExcelCustomExporter</code> component.
 *
 */
public class ExcelCustomExporter extends ExcelExporter {

	@Override
	protected String exportValue(FacesContext context, UIComponent component) {
		if (component instanceof UINamingContainer) {
			
			final StringWriter writer = new StringWriter();
		    context.setResponseWriter(context.getRenderKit().createResponseWriter(writer, "text/html", "UTF-8"));
			try {
				component.encodeAll(context);
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
			
			return writer.getBuffer().toString().replaceAll("\\<.*?>","");
		}
		return super.exportValue(context, component);
		
	}
}