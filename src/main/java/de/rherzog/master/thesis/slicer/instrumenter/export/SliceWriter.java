package de.rherzog.master.thesis.slicer.instrumenter.export;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.xml.sax.SAXException;

public class SliceWriter {
	public static final String XML_NAMESPACE = "http://rherzog.de/thesis/master/Slicer";

	public enum ExportFormat {
		CSV, XML
	}

	public static void writeCSV(String filePath, FeatureLogger featureLogger) throws IOException {
		System.out.println("Write");

		File file = new File(filePath);
		OutputStream outputStream = new FileOutputStream(file);

		Encoder encoder = Base64.getEncoder();
		StringBuilder builder = new StringBuilder();

		final String[] columns = new String[] { "execution_count", "instruction_index", "value" };
		// CSV header
		builder.append(String.join(",", columns)).append("\n");

		for (FeatureLoggerExecution execution : featureLogger.getExecutions()) {
			for (Feature feature : execution.getFeatures()) {
				int instructionIndex = feature.getInstructionIndex();
				int executionCount = execution.getExecutionCount();

				// execution_count
				builder.append(encoder.encodeToString(String.valueOf(executionCount).getBytes())).append(",");

				// instruction_index
				String instructionIndexStr = String.valueOf(instructionIndex);
				builder.append(encoder.encodeToString(instructionIndexStr.getBytes())).append(",");

				// value
				String valuesStr = String.valueOf(feature.getValue());
				builder.append(encoder.encodeToString(valuesStr.getBytes()));

				builder.append("\n");
			}
		}

		IOUtils.write(builder.toString(), outputStream);
		outputStream.close();
	}

	public static void writeXML(String filePath, FeatureLogger featureLogger) throws IOException {
		System.out.println("Write");

		Namespace namespace = Namespace.get("app", XML_NAMESPACE);
		Document document = DocumentHelper.createDocument();

		// Create default document structure elements
		Element slicerElement = document.addElement(namespace.getPrefix() + ":Slicer");
		slicerElement.add(namespace);

		long duration = exportExecutions(namespace, slicerElement, featureLogger.getExecutions());

		slicerElement.addAttribute("durationMS", String.valueOf(duration));

		// Write XML DOM to file
		File xmlFile = null;
		try {
			xmlFile = new File(filePath);
			FileOutputStream fos = new FileOutputStream(xmlFile);
			OutputFormat of = OutputFormat.createPrettyPrint();
			of.setOmitEncoding(true);
			XMLWriter writer = new XMLWriter(fos, of);
			writer.write(document);
			writer.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		// Read the previously written XML file and validate it against the XSD
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		File xsdFile = new File("Slicer.xsd");
		try {
			factory.newSchema(xsdFile).newValidator().validate(new StreamSource(xmlFile));
		} catch (SAXException | IOException e) {
			e.printStackTrace();
			return;
		}
	}

	private static long exportExecutions(Namespace namespace, Element root, List<FeatureLoggerExecution> executions) {
		Long duration = null;

		Element executionsElement = root.addElement(namespace.getPrefix() + ":Executions");
		for (FeatureLoggerExecution execution : executions) {
			Element executionElement = executionsElement.addElement(namespace.getPrefix() + ":Execution");
			executionElement.addAttribute("executionCount", String.valueOf(execution.getExecutionCount()));
			executionElement.addAttribute("startMS", String.valueOf(execution.getTimeStart()));
			executionElement.addAttribute("endMS", String.valueOf(execution.getTimeEnd()));

			long durationMs = execution.getTimeEnd() - execution.getTimeStart();
			if (duration == null) {
				duration = durationMs;
			}

			executionElement.addAttribute("durationMS", String.valueOf(durationMs));

			exportFeatures(namespace, executionElement, execution.getFeatures());
		}
		if (duration == null) {
			duration = 0L;
		}
		return duration;
	}

	private static void exportFeatures(Namespace namespace, Element root, List<Feature> features) {
		Element featuresElement = root.addElement(namespace.getPrefix() + ":Features");

		for (Feature feature : features) {
			int instructionIndex = feature.getInstructionIndex();
			Double value = feature.getValue();

			Element featureElement = featuresElement.addElement(namespace.getPrefix() + ":Feature");

			featureElement.addAttribute("instructionIndex", String.valueOf(instructionIndex));
			Element valueElement = featureElement.addElement(namespace.getPrefix() + ":Value");
			valueElement.setText(String.valueOf(value));
		}
	}
}
