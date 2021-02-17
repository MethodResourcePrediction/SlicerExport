package de.uniks.vs.methodresourceprediction.slicer.export;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.uniks.vs.methodresourceprediction.utils.Utilities;

public class FeatureLogger implements IFeatureLogger {
	private static FeatureLogger featureLogger;
	private Map<Integer, Double> featureDefaultValueMap;

	private Set<Integer> featureSet;
	private List<FeatureLoggerExecution> executions;
	private static int executionCount = 0;

	private FeatureLogger() {
		featureSet = new HashSet<>();
		executions = new ArrayList<>();
		featureDefaultValueMap = new HashMap<>();
	}

	public static FeatureLogger getInstance() {
		if (featureLogger != null) {
			return featureLogger;
		}
		featureLogger = new FeatureLogger();
		return featureLogger;
	}

	public void initializeFeature(int index) {
		initializeFeature(index, true);
	}

	public void initializeFeature(int index, boolean doThrow) {
		if (doThrow && featureSet.contains(index)) {
			throw new UnsupportedOperationException("Feature with index " + index + " was already initialized");
		}
		featureSet.add(index);
	}

	public void setFeatureDefaultValue(int index, Object value) {
		setFeatureDefaultValue(index, value, true);
	}

	public void setFeatureDefaultValue(int index, Object value, boolean doThrow) {
		if (doThrow && featureDefaultValueMap.containsKey(index)) {
			throw new UnsupportedOperationException(
					"Feature deafult value with index " + index + " was already initialized");
		}

		Double d = Utilities.convertNumericObjectToDouble(value);
		featureDefaultValueMap.put(index, d);
	}

	public FeatureLoggerExecution createExecution() {
		FeatureLoggerExecution execution = new FeatureLoggerExecution(executionCount++, featureSet,
				featureDefaultValueMap);
		executions.add(execution);
		return execution;
	}

	@Override
	public List<FeatureLoggerExecution> getExecutions() {
		return executions;
	}

	public FeatureLoggerExecution getLastExecution() {
		int size = executions.size();
		return executions.get(size - 1);
	}

	public void reset() {
		featureSet.clear();
		executions.clear();
		executionCount = 0;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(FeatureLogger.class.getSimpleName() + " [");
		builder.append("Executions: " + getExecutions().size());
		builder.append("]");
		return builder.toString();
	}

}
