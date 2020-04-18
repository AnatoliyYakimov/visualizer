package ru.yakimov.settings;

public class AxisSettings {
	private LineSettings axisLines;
	private int markValue = 0;

	public AxisSettings() {
		axisLines = new LineSettings();
	}

	public int getMarkValue() {
		return markValue;
	}

	public void setMarkValue(int markValue) {
		this.markValue = markValue;
	}

	public LineSettings getAxisLines() {
		return axisLines;
	}

	public void setAxisLines(LineSettings axisLines) {
		this.axisLines = axisLines;
	}
}
