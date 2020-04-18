package ru.yakimov.graphics;

import javafx.scene.Node;
import ru.yakimov.util.PointConsumer;

public final class DragHandlerBuilder {

	private Node node;
	private PointConsumer startDragCallback;
	private PointConsumer dragCallback;
	private PointConsumer endDragCallback;

	private DragHandlerBuilder() {
	}

	public static DragHandlerBuilder aDragHandler() {
		return new DragHandlerBuilder();
	}

	public DragHandlerBuilder withNode(Node node) {
		this.node = node;
		return this;
	}

	public DragHandlerBuilder withStartDragCallback(PointConsumer startDragCallback) {
		this.startDragCallback = startDragCallback;
		return this;
	}

	public DragHandlerBuilder withDragCallback(PointConsumer dragCallback) {
		this.dragCallback = dragCallback;
		return this;
	}

	public DragHandlerBuilder withEndDragCallback(PointConsumer endDragCallback) {
		this.endDragCallback = endDragCallback;
		return this;
	}

	public DragHandler build() {
		return new DragHandler(node, startDragCallback, dragCallback, endDragCallback);
	}
}
