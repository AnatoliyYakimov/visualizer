package ru.yakimov.util;

@FunctionalInterface
public interface PointConsumer {
	void accept(double x, double y);
}
