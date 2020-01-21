package de.rherzog.master.thesis.slicer.instrumenter.export;

import java.util.List;

public class Feature {
	private int instructionIndex;
	private List<Double> values;

	public Feature(int instructionIndex, List<Double> values) {
		this.setInstructionIndex(instructionIndex);
		this.setValues(values);
	}

	public int getInstructionIndex() {
		return instructionIndex;
	}

	public void setInstructionIndex(int instructionIndex) {
		this.instructionIndex = instructionIndex;
	}

	public List<Double> getValues() {
		return values;
	}

	public void setValues(List<Double> values) {
		this.values = values;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Feature [");
		builder.append("instructionIndex: ").append(getInstructionIndex()).append(", ");
		builder.append("values: ").append(getValues().toString()).append("]");
		return builder.toString();
	}
}
