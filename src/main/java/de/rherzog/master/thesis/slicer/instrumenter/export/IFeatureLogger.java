package de.rherzog.master.thesis.slicer.instrumenter.export;

import java.util.List;

public interface IFeatureLogger {
	void initializeFeature(int index);

	List<FeatureLoggerExecution> getExecutions();
}
