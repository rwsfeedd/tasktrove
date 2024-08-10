package standard;


import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class WindowsFactory {
	public static final double heightWindow = 768;
	public static final double widthWindow = 1024;
	Group g;
	Scene sceneEntry;
	Scene sceneCalendar;
	public Canvas canvas;
	
	public void initEntry() {
		VBox pane = new VBox();
		pane.setPrefSize(widthWindow,heightWindow); //noch ändern, da Windowsgröße > Pane	
		Label label = new Label("Hier die Angaben eintragen:");
		TextField text = new TextField("Eingaben");
		text.addEventHandler(KeyEvent.KEY_TYPED,
				new EventHandler<KeyEvent>() {
					public void handle(KeyEvent b) {System.out.println("Textänderung");}
				}
		);
		Button button = new Button("bitte funktioniere");
		button.addEventHandler(ActionEvent.ACTION, 
				new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {System.out.println("afikhafoiö");};
				});
		//text.setEventHandler(eventType, controller);
		pane.getChildren().addAll(label, text, button);
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
		sceneEntry = new Scene(pane, 300, 300, Color.BLACK);
	}
	public void initCalendar() {
		//für die Randleiste der Szene
		Border border = new Border(new BorderStroke(null, BorderStrokeStyle.DOTTED, null, new BorderWidths(2)));
		AnchorPane rootPane = new AnchorPane();
		rootPane.setBorder(border);
		
		//für das Zentrum der Scene
		GridPane grid = new GridPane();
		List<Tile> tileList = new ArrayList<Tile>(35);
		Node node;
		int y = 0;
		for(int i = 0; i < 35; i++) {
			tileList.add(new Tile(i));
			node = tileList.get(i).getNode();
			grid.add(node, i%5, y);
			if(i%5 > 3) y++;
		}
		rootPane.getChildren().add(grid);
		sceneCalendar = new Scene(rootPane, WindowsFactory.widthWindow, WindowsFactory.heightWindow);

	}
	public WindowsFactory() {
		initEntry();
		initCalendar();
	}
	public Scene getScene(int typ) {
		if(typ == 1) {
			return sceneEntry;
		}else {
			return sceneCalendar;
		}
		
	}

	
	
}
