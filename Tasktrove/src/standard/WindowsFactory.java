package standard;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class WindowsFactory {
	public static final double heightWindow = 768;
	public static final double widthWindow = 1024;
	Group g;
	Scene scene;
	public Canvas canvas;
	
	public WindowsFactory() {
		VBox pane = new VBox();
		pane.setPrefSize(widthWindow,heightWindow); //noch ändern, da Windowsgröße > Pane	
		Label label = new Label("Hier die Angaben eintragen:");
		TextField text = new TextField("Eingaben");
		pane.getChildren().addAll(label, text);
		
		/*
		canvas = new Canvas(widthWindow,heightWindow);
		GraphicsContext graphics = canvas.getGraphicsContext2D();
		graphics.setFill(Color.BLACK);
		double point0[]= {0,0};
		double point1[]= {0,1000};
		double point2[]= {1000,1000};
		double point3[]= {1000,0};
		double polyX[] = {point0[0], point1[0], point2[0], point3[0]};
		double polyY[] = {point0[1], point1[1], point2[1], point3[1]};
		graphics.fillPolygon(polyX, polyY, 4);
		pane.setCenter(canvas);
		*/
		scene = new Scene(pane, 300, 300, Color.BLACK);
	}

	
	
}
