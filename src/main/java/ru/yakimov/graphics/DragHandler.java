package ru.yakimov.graphics;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import ru.yakimov.util.PointConsumer;

public class DragHandler {
	private boolean isDragging = false;
	private PointConsumer startDragCallback;
	private PointConsumer dragCallback;
	private PointConsumer endDragCallback;

	private double offsetX = 0;
	private double offsetY = 0;

	private double acceleration = 1d;

	DragHandler(
		Node node,
		PointConsumer startDragCallback,
		PointConsumer dragCallback,
		PointConsumer endDragCallback
	) {
		this.startDragCallback = startDragCallback;
		this.dragCallback = dragCallback;
		this.endDragCallback = endDragCallback;

		node.setOnMousePressed(
			event -> {
				offsetX = event.getX();
				offsetY = event.getY();
				if (startDragCallback != null) {
					startDragCallback.accept(offsetX, offsetY);
				}
				isDragging = true;
			}
		);

		node.addEventFilter(MouseEvent.MOUSE_DRAGGED, event -> {
			Point2D p = new Point2D(event.getX(), event.getY());
			drag(p);
			event.consume();
		});

		node.addEventFilter(
			MouseEvent.MOUSE_RELEASED, event -> {
				if (isDragging) {
					endDragging(event.getX(), event.getY());
					isDragging = false;
				}
			}
		);
	}


	private void drag(Point2D p) {
		if (dragCallback != null) {
			dragCallback.accept((p.getX() - offsetX) * acceleration, (p.getY() - offsetY) * acceleration);
		}
	}

	private void endDragging(double x, double y) {
		if (endDragCallback != null) {
			endDragCallback.accept(x, y);
		}
	}

	public double getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(double acceleration) {
		this.acceleration = acceleration;
	}
}
