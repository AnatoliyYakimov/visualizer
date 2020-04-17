package ru.yakimov.graphics;

import java.util.function.Consumer;
import javafx.geometry.Point2D;
import javafx.scene.shape.Shape;

public final class DragHandlerBuilder {

	private Shape node;
	private Consumer<Point2D> startDragCallback;
	private Consumer<Point2D> dragCallback;
	private Consumer<Point2D> endDragCallback;

	private DragHandlerBuilder() {
	}

	public static DragHandlerBuilder aDragHandler() {
		return new DragHandlerBuilder();
	}

	public DragHandlerBuilder withNode(Shape node) {
		this.node = node;
		return this;
	}

	public DragHandlerBuilder withStartDragCallback(Consumer<Point2D> startDragCallback) {
		this.startDragCallback = startDragCallback;
		return this;
	}

	public DragHandlerBuilder withDragCallback(Consumer<Point2D> dragCallback) {
		this.dragCallback = dragCallback;
		return this;
	}

	public DragHandlerBuilder withEndDragCallback(Consumer<Point2D> endDragCallback) {
		this.endDragCallback = endDragCallback;
		return this;
	}

	public DragHandler build() {
		return new DragHandler(node, startDragCallback, dragCallback, endDragCallback);
	}
}
