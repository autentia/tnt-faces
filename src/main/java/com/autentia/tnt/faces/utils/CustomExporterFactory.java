package com.autentia.tnt.faces.utils;

import org.primefaces.extensions.component.exporter.Exporter;
import org.primefaces.extensions.component.exporter.ExporterFactory;
import org.primefaces.extensions.component.exporter.PDFExporter;

public class CustomExporterFactory implements ExporterFactory {

	static public enum ExporterType {
		PDF, XLS
	}

	public Exporter getExporterForType(String type) {

		ExporterType exporterType = ExporterType.valueOf(type.toUpperCase());

		switch (exporterType) {

		case XLS: {
			return new ExcelCustomExporter();
		}
		
		default: {
			return new PDFExporter();
			}

		}

	}

}