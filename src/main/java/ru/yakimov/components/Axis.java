package ru.yakimov.components;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import ru.yakimov.graphics.Viewport;
import ru.yakimov.settings.AxisSettings;

public class Axis extends Parent {

	private Viewport viewport;
	private AxisSettings settings;
	private Path axis;
	private Path markLines;
	private Group markNums;

	public Axis(Viewport viewport) {
		this(viewport, new AxisSettings());
	}

	public Axis(Viewport viewport, AxisSettings settings) {
		this.viewport = viewport;
		axis = new Path();
		axis.setStrokeWidth(settings.getAxisLines().getStrokeWidth());
		axis.setStroke(settings.getAxisLines().getStroke());
		markLines = new Path();
		markLines.setStrokeWidth(settings.getAxisLines().getStrokeWidth() / 4);
		markLines.setStroke(settings.getAxisLines().getStroke());
		markNums = new Group();
		getChildren().addAll(axis, markLines, markNums);
		viewport.addBordersChangeListener(
			() -> {
				createAxis();
				createMarks();
			}
		);
	}

	private void createAxis() {
		axis.getElements().clear();
		axis.getElements().addAll(
			new MoveTo(viewport.getL(), 0),
			new LineTo(viewport.getR(), 0),
			new MoveTo(0, viewport.getT()),
			new LineTo(0, viewport.getB()),
			new ClosePath()
		);
	}

	private void createMarks() {
		markLines.getElements().clear();
		markNums.getChildren().clear();
		double markSize = markLines.getStrokeWidth() * 3;
		int x = 0;
		Font font = new Font(markSize * 3);
		markLines.getElements().addAll(
			new MoveTo(0, markSize)
		);
		while (x++ < viewport.getR()) {
			createMarkX(markSize, x, font);
		}
		markLines.getElements().addAll(
			new MoveTo(0, markSize)
		);
		x = 0;
		while (x-- > viewport.getL()) {
			createMarkX(markSize, x, font);
		}
		markLines.getElements().addAll(
			new MoveTo(markSize, 0)
		);
		int y = 0;
		while (y++ < viewport.getT()) {
			createMarkY(markSize, font, y);
		}
		markLines.getElements().addAll(
			new MoveTo(markSize, 0)
		);
		y = 0;
		while (y-- > viewport.getB()) {
			createMarkY(markSize, font, y);
		}
		markLines.getElements().add(new ClosePath());
	}

	private void createMarkX(double markSize, int x, Font font) {
		markLines.getElements().addAll(
			new MoveTo(x, markSize),
			new LineTo(x, -markSize)

		);
		Text t = new Text(x, -markSize, String.valueOf(x));
		t.setFont(font);
		t.setScaleY(-1);
		t.setTextAlignment(TextAlignment.CENTER);
		markNums.getChildren().add(t);
	}

	private void createMarkY(double markSize, Font font, int y) {
		markLines.getElements().addAll(
			new MoveTo(markSize, y),
			new LineTo(-markSize, y)
		);
		Text t = new Text(markSize * 1.1, y, String.valueOf(y));
		t.setFont(font);
		t.setScaleY(-1);
		markNums.getChildren().add(t);
	}

	public AxisSettings getSettings() {
		return settings;
	}

	public void setSettings(AxisSettings settings) {
		this.settings = settings;
	}
}
