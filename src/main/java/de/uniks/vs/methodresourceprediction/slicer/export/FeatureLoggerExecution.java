package de.uniks.vs.methodresourceprediction.slicer.export;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.uniks.vs.methodresourceprediction.utils.Utilities;

public class FeatureLoggerExecution implements IFeatureLoggerExecution {
	private final int executionCount;
	private Set<Integer> featureSet;
	private Map<Integer, Double> featureValueMap;
	private long timeEnd, timeStart;
	private Map<Integer, Double> featureDefaultValueMap;

	public FeatureLoggerExecution(int executionCount, Set<Integer> featureSet,
			Map<Integer, Double> featureDefaultValueMap) {
		this.executionCount = executionCount;
		this.featureSet = new HashSet<>(featureSet);
		this.featureValueMap = new HashMap<>();
		this.featureDefaultValueMap = new HashMap<>(featureDefaultValueMap);
	}

	@Override
	public void log(int index, Object value) {
		log(index, value, false);
	}

	@Override
	public void log(int index, Object value, boolean allowValueOverwrite) {
		if (!featureSet.contains(index)) {
			throw new UnsupportedOperationException("Feature with index " + index + " was not initialized");
		}
		if (!allowValueOverwrite && featureValueMap.containsKey(index)) {
			throw new UnsupportedOperationException(
					"Feature with index " + index + " has already a value in this execution");
		}

		Double d = Utilities.convertNumericObjectToDouble(value);
		featureValueMap.put(index, d);
	}

	@Override
	public List<Feature> getFeatures() {
		List<Feature> featureList = new ArrayList<>();
		for (Integer instructionIndex : featureSet) {
			Double featureValue = featureValueMap.get(instructionIndex);
			if (featureValue == null && featureDefaultValueMap.containsKey(instructionIndex)) {
				featureValue = featureDefaultValueMap.get(instructionIndex);
			}
			featureList.add(new Feature(instructionIndex, featureValue));
		}
		return featureList;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(FeatureLoggerExecution.class.getSimpleName());
		builder.append(" [count: ").append(getExecutionCount()).append("]");
		return builder.toString();
	}

	@Override
	public int getExecutionCount() {
		return executionCount;
	}

	@Override
	public void end(long timeStart, long timeEnd) {
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
	}

	@Override
	public long getTimeEnd() {
		return timeEnd;
	}

	@Override
	public long getTimeStart() {
		return timeStart;
	}

	@Override
	public Double getFeatureValue(int index) {
		return featureValueMap.get(index);
	}
}
