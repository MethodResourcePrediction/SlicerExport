package de.rherzog.master.thesis.slicer.instrumenter.export;

import java.util.List;

public interface IFeatureLoggerExecution {
	void log(int index, Object value);

	List<Feature> getFeatures();

	Double getFeatureValue(int index);

	void end(long timeStart, long timeEnd);

	long getTimeEnd();

	long getTimeStart();

	int getExecutionCount();

	void log(int index, Object value, boolean allowValueOverwrite);
}
