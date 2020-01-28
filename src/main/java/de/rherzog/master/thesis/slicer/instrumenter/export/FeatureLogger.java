package de.rherzog.master.thesis.slicer.instrumenter.export;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FeatureLogger implements IFeatureLogger {
	private static FeatureLogger featureLogger;

	private Set<Integer> featureSet;
	private List<FeatureLoggerExecution> executions;
	private static int executionCount = 0;

	private FeatureLogger() {
		featureSet = new HashSet<>();
		executions = new ArrayList<>();
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

	public FeatureLoggerExecution createExecution() {
		FeatureLoggerExecution execution = new FeatureLoggerExecution(executionCount++, featureSet);
		executions.add(execution);
		return execution;
	}

	@Override
	public List<FeatureLoggerExecution> getExecutions() {
		return executions;
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
