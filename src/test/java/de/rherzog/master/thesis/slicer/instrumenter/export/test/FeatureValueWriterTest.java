package de.rherzog.master.thesis.slicer.instrumenter.export.test;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.rherzog.master.thesis.slicer.instrumenter.export.FeatureLogger;
import de.rherzog.master.thesis.slicer.instrumenter.export.SliceWriter;

public class FeatureValueWriterTest {
	private FeatureLogger featureLogger;

	private void setup() {
		featureLogger = FeatureLogger.getInstance();
		featureLogger.reset();
	}

	@Test(expected = NullPointerException.class)
	public void testMissingFeature() throws IOException {
		setup();

		featureLogger.initializeFeature(1);
		featureLogger.log(2, 1337);
	}

	@Test
	public void testWrite() throws IOException {
		setup();

		featureLogger.initializeFeature(1);
		featureLogger.initializeFeature(2);

		featureLogger.log(1, 42);
		featureLogger.log(2, 1337);

		SliceWriter.writeCSV("result.csv", 111, 113, featureLogger);
		SliceWriter.writeXML("result.xml", 111, 112, featureLogger);
	}

	@Test
	public void testMultipleInitializations() throws IOException {
		setup();

		featureLogger.initializeFeature(1);
		featureLogger.initializeFeature(2);
		featureLogger.initializeFeature(1);

		featureLogger.log(1, 42);
		featureLogger.log(2, 1337);
	}

	@Test
	public void testIncrementBy() throws IOException {
		setup();

		featureLogger.initializeFeature(1);

		featureLogger.log(1, 42);
		featureLogger.incrementLastBy(1, 4);
		featureLogger.incrementLastBy(1, 5);

		List<Double> featureValueList = featureLogger.getFeatureValueList(1);
		Assert.assertEquals(1, featureValueList.size());
		Assert.assertEquals(51d, featureValueList.get(0), 0);
	}

	@Test
	public void testIncrementByWithDefault() throws IOException {
		setup();

		featureLogger.initializeFeature(1);
		featureLogger.initializeFeature(2);

		featureLogger.log(1, 0);
		featureLogger.log(2, 15);
		featureLogger.incrementLastBy(1, 4);
		featureLogger.incrementLastBy(1, 5);

		List<Double> featureValueList = featureLogger.getFeatureValueList(1);
		Assert.assertEquals(1, featureValueList.size());
		Assert.assertEquals(9d, featureValueList.get(0), 0);

		featureValueList = featureLogger.getFeatureValueList(2);
		Assert.assertEquals(1, featureValueList.size());
		Assert.assertEquals(15d, featureValueList.get(0), 0);

		SliceWriter.writeXML("result.xml", 111, 113, featureLogger);
	}
}
