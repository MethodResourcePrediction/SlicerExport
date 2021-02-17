package de.uniks.vs.methodresourceprediction.slicer.export;

public class Feature {
	private int instructionIndex;
	private Double value;

	public Feature(int instructionIndex, Double value) {
		this.setInstructionIndex(instructionIndex);
		this.setValue(value);
	}

	public int getInstructionIndex() {
		return instructionIndex;
	}

	public void setInstructionIndex(int instructionIndex) {
		this.instructionIndex = instructionIndex;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Feature [");
		builder.append("instructionIndex: ").append(getInstructionIndex()).append(", ");
		builder.append("value: ").append(getValue().toString()).append("]");
		return builder.toString();
	}
}
