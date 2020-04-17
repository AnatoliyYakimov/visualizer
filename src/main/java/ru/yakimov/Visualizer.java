package ru.yakimov;

import static java.lang.Math.pow;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yakimov.components.Axis;
import ru.yakimov.components.Plot;
import ru.yakimov.graphics.Viewport;

public class Visualizer extends Application {
	private Stage primaryStage;

	private static final Logger log = LoggerFactory.getLogger(Visualizer.class);

	public static void main(String[] args) {
		Visualizer.launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Hello world Application");
		Viewport vp = new Viewport(-10, 10, -2, 8);
		Plot p = new Plot((x) -> 1 * pow(x,2) , vp);
		Axis axis = new Axis(vp);
		axis.setStrokeWidth(0.1);
		p.setStroke(Paint.valueOf("magenta"));
		p.setStrokeWidth(0.2);
		vp.getPane().getChildren().addAll(p, axis);
		Scene s = new Scene(vp.getPane(), 800, 600, true, SceneAntialiasing.BALANCED);
		primaryStage.setScene(s);
		primaryStage.setWidth(800d);
		primaryStage.setHeight(600d);
		primaryStage.setOnCloseRequest(
			event -> System.exit(0)
		);
		primaryStage.show();
	}
}
