package ru.yakimov.graphics;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Transform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yakimov.util.Action;

public class Viewport {

	private static final Logger logger = LoggerFactory.getLogger(Viewport.class);

	private DoubleProperty L;
	private DoubleProperty R;
	private DoubleProperty B;
	private DoubleProperty T;
	private DoubleProperty stepX;
	private DoubleProperty stepY;
	private ResizingStrategy resizingStrategy;
	private ObjectProperty<Object> bordersChange;

	private Affine stretch;
	private Affine move;
	private Affine drag;

	Double offsetX = 0d;
	Double offsetY = 0d;

	private Pane pane;

	public Viewport(double l, double r, double b, double t) {
		pane = new Pane();
		stretch = Transform.affine(1, 0, 0, 1, 0, 0);
		move = Transform.affine(1, 0, 0, 1, -l, -t);
		drag = Transform.affine(1, 0, 0, 1, 0, 0);
		pane.getTransforms().addAll(
			stretch,
			move,
			drag
		);
		L = new SimpleDoubleProperty(l);
		R = new SimpleDoubleProperty(r);
		B = new SimpleDoubleProperty(b);
		T = new SimpleDoubleProperty(t);
		this.resizingStrategy = ResizingStrategy.HEIGHT_FIXED;
		bordersChange = new SimpleObjectProperty<>();
		stepX = new SimpleDoubleProperty();
		pane.widthProperty().addListener(
			(observable, oldValue, newValue) -> resizingStrategy
				.resize(this, (double) oldValue, (double) newValue, pane.getHeight(), pane.getHeight())
		);
		pane.heightProperty().addListener(
			(observable, oldValue, newValue) -> resizingStrategy
				.resize(this, pane.getWidth(), pane.getWidth(), (double) oldValue, (double) newValue)
		);
		stepX.bind(Bindings.subtract(R, L).divide(pane.widthProperty()));
		stepY = new SimpleDoubleProperty();
		stepY.bind(Bindings.subtract(T, B).divide(pane.heightProperty()));
		stepX.addListener(
			(observable, oldValue, newValue) -> stretch.setMxx(1.0 / (double) newValue)
		);
		stepY.addListener(
			(observable, oldValue, newValue) -> stretch.setMyy(-1.0 / (double) newValue)
		);
		L.addListener(
			(observable, oldValue, newValue) -> move.setTx(-1.0 * (double) newValue)
		);
		T.addListener(
			(observable, oldValue, newValue) -> move.setTy(-1.0 * (double) newValue)
		);



		pane.setOnMousePressed(
			event -> {
				offsetX = event.getX();
				offsetY = event.getY();
			}
		);
		DragHandler dragHandler = DragHandlerBuilder
			.aDragHandler()
			.withNode(pane)
			.withDragCallback(
				(x, y) -> {
					T.set(getT() - y);
					B.set(getB() - y);
					R.set(getR() - x);
					L.set(getL() - x);
					bordersChange.set(new Object());
				}
			)
			.build();
	}

	public void addBordersChangeListener(Action action) {
		ChangeListener<Object> changeListener =
			(observable, oldValue, newValue) -> action.perform();
		bordersChange.addListener(changeListener);
	}

	public double getL() {
		return L.get();
	}

	public void setL(double l) {
		this.L.set(l);
	}

	public DoubleProperty lProperty() {
		return L;
	}

	public double getR() {
		return R.get();
	}

	public void setR(double r) {
		this.R.set(r);
	}

	public DoubleProperty rProperty() {
		return R;
	}

	public double getB() {
		return B.get();
	}

	public void setB(double b) {
		this.B.set(b);
	}

	public DoubleProperty bProperty() {
		return B;
	}

	public double getT() {
		return T.get();
	}

	public void setT(double t) {
		this.T.set(t);
	}

	public DoubleProperty tProperty() {
		return T;
	}

	public ReadOnlyDoubleProperty widthProperty() {
		return pane.widthProperty();
	}

	public ReadOnlyDoubleProperty heightProperty() {
		return pane.heightProperty();
	}

	public Pane getPane() {
		return pane;
	}

	public interface ResizingStrategy {

		ResizingStrategy WIDTH_FIXED =
			(v, oldWidth, newWidth, oldHeight, newHeight) -> {
				if (oldHeight != newHeight && oldHeight != 0) {
					double d = (newHeight - oldHeight) / 2;
					double step = (v.getT() - v.getB()) / oldHeight;
					v.setB(v.getB() - d * step);
					v.setT(v.getT() + d * step);
					v.bordersChange.setValue(new Object());
				}
			};

		ResizingStrategy HEIGHT_FIXED =
			(v, oldWidth, newWidth, oldHeight, newHeight) -> {
				if (oldWidth != newWidth && oldWidth != 0) {
					double d = (newWidth - oldWidth) / 2;
					double step = (v.getR() - v.getL()) / oldWidth;
					v.setL(v.getL() - d * step);
					v.setR(v.getR() + d * step);
					v.bordersChange.setValue(new Object());
				}
			};

		void resize(Viewport v, double oldWidth, double newWidth, double oldHeight, double newHeight);
	}
}
