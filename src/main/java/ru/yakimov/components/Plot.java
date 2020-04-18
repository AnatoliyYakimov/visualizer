package ru.yakimov.components;

import java.util.function.Function;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yakimov.graphics.Viewport;

public class Plot extends Path {

	private static final Logger logger = LoggerFactory.getLogger(Plot.class);

	private Function<Double, Double> function;
	private Viewport viewport;

	public Plot(Function<Double, Double> function, Viewport viewport) {
		this.function = function;
		this.viewport = viewport;
		viewport.addBordersChangeListener(this::createPath);
	}

	private void createPath() {
		getElements().clear();
		int numPoints = 150;
		double step = (viewport.getR() - viewport.getL()) / numPoints;
		double x = viewport.getL();
		double y = function.apply(x);
		this.getElements().add(new MoveTo(x, y));
		while (x < viewport.getR()) {
			x = x + step;
			y = function.apply(x);
			this.getElements().add(new LineTo(x, y));
		}
		this.getElements().add(new MoveTo(x, y));
		getElements().add(new ClosePath());
	}

	public Function<Double, Double> getFunction() {
		return function;
	}

	public void setFunction(Function<Double, Double> function) {
		this.function = function;
		createPath();
	}

	public Viewport getViewport() {
		return viewport;
	}

}
