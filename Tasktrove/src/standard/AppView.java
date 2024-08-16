package standard;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AppView{
	public final double heightWindow = 768;
	public final double widthWindow = 1024;
	private Stage primaryStage;
	private AppModel model;
	private AppController controller;

	public AppView(Stage primaryStage, AppModel model, AppController controller) {
		this.primaryStage = primaryStage;
		this.model = model;
		this.controller = controller;
	}
	public void update() {
		primaryStage.setHeight(heightWindow); 		
		primaryStage.setWidth(widthWindow);
		switch(model.getCurrentScene()) {
			case AppModel.ENTRY_SCENE: primaryStage.setScene(getSceneEntry());
					break;
			default: primaryStage.setScene(getSceneCalendar());
		}
		primaryStage.show();
	}
	private Scene getSceneCalendar() {
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
		
		Button buttonNewDate = new Button("neuer Termin");
		buttonNewDate.setOnAction(e -> {controller.handle(AppController.NEW_DATE);});

		HBox paneMonth = new HBox();
		Button buttonPreviousMonth = new Button();
		Label labelMonth = new Label(model.getCurrentMonth());
		Button buttonNextMonth = new Button();
		paneMonth.getChildren().addAll(buttonPreviousMonth, labelMonth, buttonNextMonth);

		AnchorPane.setTopAnchor(paneMonth, 0.0);
		AnchorPane.setRightAnchor(buttonNewDate, 0.0);
		AnchorPane.setBottomAnchor(grid, 0.0);
		rootPane.getChildren().addAll(paneMonth, grid, buttonNewDate); 
		return new Scene(rootPane);
	}
	private Scene getSceneEntry() {
		VBox pane = new VBox();
		//pane.setPrefSize(widthWindow,heightWindow); //noch �ndern, da Windowsgr��e > Pane	
		Label labelTitle = new Label("neuer Termin");
		labelTitle.setMinHeight(25);

		Label labelName = new Label("Name:");
		TextField textFieldName = new TextField();

		Label labelDescription = new Label("Beschreibung");
		TextField textFieldDescription = new TextField();

		Button buttonSaveData = new Button("Speichern");
		//buttonSaveData.setOnAction(e->{controller.handle(Controller.BUTTON_SAVE_DATA);});
		
		pane.getChildren().addAll(labelTitle, labelName, textFieldName, labelDescription, textFieldDescription, buttonSaveData);
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
		return new Scene(pane);
	}

}
