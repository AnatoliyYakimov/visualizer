package ru.yakimov;

import static java.lang.Math.pow;
import static java.lang.Math.sin;

import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yakimov.components.Axis;
import ru.yakimov.components.Plot;
import ru.yakimov.graphics.Viewport;
import ru.yakimov.settings.AxisSettings;
import ru.yakimov.settings.LineSettings;

public class Visualizer extends Application {
	private Stage primaryStage;

	private static final Logger log = LoggerFactory.getLogger(Visualizer.class);

	public static void main(String[] args) {
		Visualizer.launch(args);
	}

	Double k = 1d;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Hello world Application");
		Viewport vp = new Viewport(-10, 10, -10, 10);
		Plot p = new Plot((x) -> sin(2*x) , vp);
		Plot p2 = new Plot((x) -> 0.3*pow(x,k) , vp);
		AxisSettings as = new AxisSettings();
		LineSettings ls = new LineSettings();
		ls.setStroke(Paint.valueOf("black"));
		ls.setStrokeWidth(0.2);
		as.setAxisLines(ls);
		RotateTransition rt = new RotateTransition();
		rt.setNode(p2);
		rt.setDuration(Duration.seconds(10 * 4));
		rt.setByAngle(10 * 360d);
		rt.setAxis(new Point3D(0, 0 ,1));
		rt.setAutoReverse(true);
		rt.play();
		Axis axis = new Axis(vp, as);
		p.setStroke(Paint.valueOf("magenta"));
		p.setStrokeWidth(0.2);
		p2.setStrokeWidth(0.2);
		p2.setStroke(Paint.valueOf("yellow"));
		vp.getPane().getChildren().addAll(axis, p, p2);
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
