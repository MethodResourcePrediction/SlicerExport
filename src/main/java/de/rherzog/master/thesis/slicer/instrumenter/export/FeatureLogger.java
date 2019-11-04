package de.rherzog.master.thesis.slicer.instrumenter.export;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import de.rherzog.master.thesis.utils.Utilities;

public class FeatureLogger implements IFeatureLogger {
	// TODO feature index as a key or feature instruction index?????
	private static HashMap<Integer, Double> featureMap = new LinkedHashMap<>();

	public void initializeFeature(int instructionIndex) {
		featureMap.put(instructionIndex, 0d);
	}

	public void log(int instructionIndex, Object value) {
		Double d = Utilities.convertNumericObjectToDouble(value);
		featureMap.put(instructionIndex, d);
	}

	@Override
	public List<Feature> getFeatures() {
		return featureMap.entrySet().stream().map(entry -> new Feature(entry.getKey(), entry.getValue()))
				.collect(Collectors.toList());
	}

	@Override
	public Double getFeatureValue(int instructionIndex) {
		return featureMap.get(instructionIndex);
	}
}
