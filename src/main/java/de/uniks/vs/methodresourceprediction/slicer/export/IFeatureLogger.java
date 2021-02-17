package de.uniks.vs.methodresourceprediction.slicer.export;

import java.util.List;

public interface IFeatureLogger {
	void initializeFeature(int index);

	List<FeatureLoggerExecution> getExecutions();
}
