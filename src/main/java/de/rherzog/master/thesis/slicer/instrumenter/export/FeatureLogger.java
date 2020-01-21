package de.rherzog.master.thesis.slicer.instrumenter.export;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import de.rherzog.master.thesis.utils.Utilities;

public class FeatureLogger implements IFeatureLogger {
	private static FeatureLogger featureLogger;

	// TODO feature index as a key or feature instruction index?????
	private HashMap<Integer, List<Double>> featureMap;

	private FeatureLogger() {
		featureMap = new LinkedHashMap<>();
	}

	public static FeatureLogger getInstance() {
		if (featureLogger != null) {
			return featureLogger;
		}
		featureLogger = new FeatureLogger();
		return featureLogger;
	}

	public void initializeFeature(int instructionIndex) {
		initializeFeature(instructionIndex, null);
	}

	public void initializeFeature(int instructionIndex, Object initialValue) {
		List<Double> list = featureMap.get(instructionIndex);
		if (list != null) {
			return;
		}

		list = new ArrayList<>();
		if (initialValue != null) {
			Double value = Utilities.convertNumericObjectToDouble(initialValue);
			list.add(value);
		}
		featureMap.put(instructionIndex, list);
	}

	public void log(int instructionIndex, Object value) {
		List<Double> list = featureMap.get(instructionIndex);
		Objects.requireNonNull(list, "Feature with index " + instructionIndex + " was not initialized");

		Double doubleValue = Utilities.convertNumericObjectToDouble(value);
		list.add(doubleValue);
	}

	public void incrementLastBy(int instructionIndex, Object value) {
		List<Double> list = featureMap.get(instructionIndex);
		Objects.requireNonNull(list, "Feature with index " + instructionIndex + " was not initialized");

		Double doubleValue = Utilities.convertNumericObjectToDouble(value);
		int index = list.size() - 1;
		Double storedValue = list.get(index);
		storedValue += doubleValue;

		list.add(index, storedValue);
		list.remove(index + 1);
	}

	@Override
	public List<Feature> getFeatures() {
		return featureMap.entrySet().stream().map(entry -> new Feature(entry.getKey(), entry.getValue()))
				.collect(Collectors.toList());
	}

	@Override
	public List<Double> getFeatureValueList(int instructionIndex) {
		return featureMap.get(instructionIndex);
	}

	public void reset() {
		featureMap.clear();
	}
}
