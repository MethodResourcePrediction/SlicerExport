package de.rherzog.master.thesis.slicer.instrumenter.export;

import java.util.List;

public interface IFeatureLogger {
	public void initializeFeature(int index);

	public void log(int index, Object value);

	public List<Feature> getFeatures();

	Double getFeatureValue(int instructionIndex);
}
