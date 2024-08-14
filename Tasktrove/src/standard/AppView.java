package standard;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AppView{
	public final double heightWindow = 768;
	public final double widthWindow = 1024;
	public Scene currentScene;
	private Stage primaryStage;
	private AppModel model;

	public AppView(Stage primaryStage, AppModel model) {
		this.primaryStage = primaryStage;
		this.model = model;
	}
	public void update() {
		primaryStage.setHeight(heightWindow); 		
		primaryStage.setWidth(widthWindow);
		primaryStage.setScene(currentScene);
		primaryStage.show();
	}
	public void setSceneCalendar() {
		//buildingCalendarScene
		int[] calendarData = model.getCalendarInfo();
		AnchorPane rootPane = new AnchorPane();
		GridPane grid = new GridPane();
		List<Tile> tileList = new ArrayList<Tile>(35);
		Node node;
		int y = 0;
		for(int i = 0; i < calendarData[1]; i++) {
			tileList.add(new Tile(i+1));
			node = tileList.get(i).getNode();
			grid.add(node, (i+calendarData[0])%7, y);
			if((i+calendarData[0])%7 > 5) y++;
		}
		rootPane.getChildren().add(grid);
		currentScene = new Scene(rootPane);
	}
	public void setSceneEntry() {
		VBox pane = new VBox();
		//pane.setPrefSize(widthWindow,heightWindow); //noch ändern, da Windowsgröße > Pane	
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
		currentScene = new Scene(pane, 300, 300, Color.BLACK);
	}

}
