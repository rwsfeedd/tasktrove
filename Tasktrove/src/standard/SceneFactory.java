package standard;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import javafx.application.Platform;
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

public class SceneFactory {
	public static final double heightWindow = 768;
	public static final double widthWindow = 1024;
	public Stage primaryStage;
	Group g;
	public Canvas canvas;
	
	public SceneFactory(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	public Scene sceneEntry() {
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
		return new Scene(pane, 300, 300, Color.BLACK);
	}
	public Scene sceneCalendar() {
		TimeZone timezone = TimeZone.getDefault();
		GregorianCalendar calendar = new GregorianCalendar(timezone);
		
		//calculating days in month for Calendargrid
		int year = 2023;
		int month = 2;
		int daysInMonth = 0;
		if(month <1 | month >12) {
			System.err.println("In SceneFactory ist Int month nicht valide(Wert außerhalb des Bereichs 1-12)!");
			Platform.exit();
		}
		if(month !=2 && ((month-1)%7)%2 == 0) daysInMonth = 31;
		if(month !=2 && ((month-1)%7)%2 == 1) daysInMonth = 30;
		if(month == 2 && calendar.isLeapYear(year) == true) daysInMonth = 29;
		if(month == 2 && calendar.isLeapYear(year) == false) daysInMonth = 28;
		
		//Weekday of first day in month for offset to establish order in View of month
		GregorianCalendar tempCalendar = (GregorianCalendar) calendar.clone();
		tempCalendar.set(Calendar.DAY_OF_MONTH, 1);
		tempCalendar.set(Calendar.MONTH, month-1);//month in Calendar(0-11) and in GregorianCalendar(1-12)
		tempCalendar.set(Calendar.YEAR, year);
		int offset = 0;
		if(tempCalendar.get(Calendar.DAY_OF_WEEK) == 1) { // first Weekday in Calendarclass is Sunday with int 1
			offset = 6;
		} else {
			offset = tempCalendar.get(Calendar.DAY_OF_WEEK) - 2; // Monday has int 2, subtract 2 to get Monday in first column of grid 
		}

		//buildingCalendarScene
		AnchorPane rootPane = new AnchorPane();
		GridPane grid = new GridPane();
		List<Tile> tileList = new ArrayList<Tile>(35);
		Node node;
		int y = 0;
		for(int i = 0; i < daysInMonth; i++) {
			tileList.add(new Tile(i+1));
			node = tileList.get(i).getNode();
			grid.add(node, (i+offset)%7, y);
			if((i+offset)%7 > 5) y++;
		}
		rootPane.getChildren().add(grid);
		Scene scene = new Scene(rootPane, SceneFactory.widthWindow, SceneFactory.heightWindow);
		primaryStage.setScene(scene);
		primaryStage.show();
		return scene;
	}
	public Scene getScene(int typ) {
		if(typ == 1) {
			return sceneEntry();
		}else {
			return sceneCalendar();
		}
		
	}

	
	
}
