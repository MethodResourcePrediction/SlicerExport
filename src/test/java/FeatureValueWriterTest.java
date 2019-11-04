import java.io.IOException;

import org.junit.Test;

import de.rherzog.master.thesis.slicer.instrumenter.export.FeatureLogger;
import de.rherzog.master.thesis.slicer.instrumenter.export.SliceWriter;

public class FeatureValueWriterTest {
	@Test
	public void test_log() throws IOException {
		FeatureLogger featureLogger = new FeatureLogger();
		featureLogger.initializeFeature(1);
		
//		featureLogger.log(1, 42);
		featureLogger.log(2, 1337);

		SliceWriter.writeCSV("result.csv", 111, 113, featureLogger);
		SliceWriter.writeXML("result.xml", 111, 112, featureLogger);
	}
}
