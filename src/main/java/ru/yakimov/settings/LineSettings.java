package ru.yakimov.settings;

import javafx.scene.paint.Paint;

public class LineSettings {
	private double strokeWidth;
	private Paint stroke;

	public LineSettings() {
		strokeWidth = 1;
		stroke = Paint.valueOf("black");
	}

	public double getStrokeWidth() {
		return strokeWidth;
	}

	public void setStrokeWidth(double strokeWidth) {
		this.strokeWidth = strokeWidth;
	}

	public Paint getStroke() {
		return stroke;
	}

	public void setStroke(Paint stroke) {
		this.stroke = stroke;
	}
}
