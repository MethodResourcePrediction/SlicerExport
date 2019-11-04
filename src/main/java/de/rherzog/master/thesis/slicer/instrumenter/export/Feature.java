package de.rherzog.master.thesis.slicer.instrumenter.export;

public class Feature {
	private int instructionIndex;
	private Object value;

	public Feature(int instructionIndex, Object value) {
		this.setInstructionIndex(instructionIndex);
		this.setValue(value);
	}

	public int getInstructionIndex() {
		return instructionIndex;
	}

	public void setInstructionIndex(int instructionIndex) {
		this.instructionIndex = instructionIndex;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Feature [");
		builder.append("instructionIndex: ").append(getInstructionIndex()).append(", ");
		builder.append("value: ").append(getValue()).append("]");
		return builder.toString();
	}
}
