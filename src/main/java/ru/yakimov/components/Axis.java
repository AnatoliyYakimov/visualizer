package ru.yakimov.components;

import javafx.scene.shape.ClosePath;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import ru.yakimov.graphics.Viewport;

public class Axis extends Path {
	private Viewport viewport;

	public Axis(Viewport viewport) {
		this.viewport = viewport;
		viewport.widthProperty().addListener(
			((observable, oldValue, newValue) -> createAxis())
		);
		viewport.heightProperty().addListener(
			((observable, oldValue, newValue) -> createAxis())
		);
	}

	private void createAxis() {
		getElements().clear();
		getElements().addAll(
			new MoveTo(viewport.getL(), 0),
			new LineTo(viewport.getR(), 0),
			new MoveTo(0, viewport.getT()),
			new LineTo(0, viewport.getB()),
			new ClosePath()
		);
	}
}
