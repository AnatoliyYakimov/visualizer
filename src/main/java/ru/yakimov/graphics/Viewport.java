package ru.yakimov.graphics;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Transform;

public class Viewport {

	private DoubleProperty L;
	private DoubleProperty R;
	private DoubleProperty B;
	private DoubleProperty T;
	private DoubleProperty stepX;
	private DoubleProperty stepY;

	private Affine stretch;
	private Affine move;

	private Pane pane;

	public Viewport(double l, double r, double b, double t) {
		pane = new Pane();
		stretch =  Transform.affine(1, 0, 0, 1, 0, 0);
		move = Transform.affine(1, 0, 0, 1, -l, -t);
		pane.getTransforms().addAll(
			stretch,
			move
		);
		L = new SimpleDoubleProperty(l);
		R = new SimpleDoubleProperty(r);
		B = new SimpleDoubleProperty(b);
		T = new SimpleDoubleProperty(t);
		stepX = new SimpleDoubleProperty();
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
}
