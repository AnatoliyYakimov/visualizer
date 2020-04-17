package ru.yakimov.graphics;

import java.util.function.Consumer;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;

public class DragHandler {

	private boolean isDragging;
	private Point2D last;

	private Consumer<Point2D> startDragCallback;
	private Consumer<Point2D> dragCallback;
	private Consumer<Point2D> endDragCallback;

	private double acceleration = 1d;

	DragHandler(
		Shape node,
		Consumer<Point2D> startDragCallback,
		Consumer<Point2D> dragCallback,
		Consumer<Point2D> endDragCallback
	) {
		this.startDragCallback = startDragCallback;
		this.dragCallback = dragCallback;
		this.endDragCallback = endDragCallback;

		node.addEventFilter(MouseEvent.MOUSE_DRAGGED, event -> {
			Point2D p = new Point2D(event.getX(), event.getY());
			drag(p);
		});

		node.addEventFilter(
			MouseEvent.MOUSE_RELEASED, event -> {
				if (isDragging) {
					endDragging(new Point2D(event.getX(), event.getY()));
				}
			}
		);
	}


	private void drag(Point2D p) {
		if (last == null) {
			if (startDragCallback != null) {
				startDragCallback.accept(p);
			}
		} else if (dragCallback != null) {
			dragCallback.accept(p.subtract(last).multiply(acceleration));
		}
		last = p;
		isDragging = true;
	}

	private void endDragging(Point2D p) {
		last = null;
		isDragging = false;
		if (endDragCallback != null) {
			endDragCallback.accept(p);
		}
	}

	public double getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(double acceleration) {
		this.acceleration = acceleration;
	}
}
