package de.uniks.vs.methodresourceprediction.slicer.export.test;

import java.io.IOException;

import de.uniks.vs.methodresourceprediction.slicer.export.SliceWriter;
import org.junit.Assert;
import org.junit.Test;

import de.uniks.vs.methodresourceprediction.slicer.export.FeatureLogger;
import de.uniks.vs.methodresourceprediction.slicer.export.FeatureLoggerExecution;

public class FeatureValueWriterTest {
	private FeatureLogger featureLogger;

	private void setup() {
		featureLogger = FeatureLogger.getInstance();
		featureLogger.reset();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testMissingFeature() throws IOException {
		setup();

		featureLogger.initializeFeature(1);
		FeatureLoggerExecution execution = featureLogger.createExecution();

		execution.log(2, 1337);

		Assert.assertEquals(1, execution.getFeatures().size());
		Assert.assertEquals(1337d, execution.getFeatureValue(2), 0d);
	}

	@Test
	public void testWrite() throws IOException {
		setup();

		featureLogger.initializeFeature(1);
		featureLogger.initializeFeature(2);

		FeatureLoggerExecution execution = featureLogger.createExecution();

		execution.log(1, 42);
		execution.log(2, 1337);

		Assert.assertEquals(2, execution.getFeatures().size());
		Assert.assertEquals(42d, execution.getFeatureValue(1), 0d);
		Assert.assertEquals(1337d, execution.getFeatureValue(2), 0d);

		execution.end(133, 155);

		SliceWriter.writeCSV("result.csv", featureLogger);
		SliceWriter.writeXML("result.xml", featureLogger);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testMultipleInitializations() throws IOException {
		setup();

		featureLogger.initializeFeature(1);
		featureLogger.initializeFeature(2);
		featureLogger.initializeFeature(1);
	}

	@Test
	public void testExecutions() throws IOException {
		setup();

		FeatureLoggerExecution execution1 = featureLogger.createExecution();
		FeatureLoggerExecution execution2 = featureLogger.createExecution();

		Assert.assertEquals(0, execution1.getExecutionCount());
		Assert.assertEquals(1, execution2.getExecutionCount());
	}
}
