package standard;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class AppView{
	public final double heightWindow = 768;
	public final double widthWindow = 1024;
	private Stage primaryStage;
	private AppModel model;
	private AppController controller;
	private CalendarDate calendarDate;

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
			CalendarDate date = CalendarDate.getTestObject();
			LinkedList<CalendarDate> liste = new LinkedList<CalendarDate>();
			liste.add(date);
			tileList.add(new Tile(i+1, liste));
			node = tileList.get(i).getNode();
			grid.add(node, (i+calendarData[0])%7, y);
			if((i+calendarData[0])%7 > 5) y++;
		}
	
		Button buttonNewDate = new Button("neuer Termin");
		buttonNewDate.setOnAction(e -> {controller.handle(AppController.NEW_DATE);});

		//Pane für Monatsauswahl initialisieren
		HBox paneMonth = new HBox();
		//Button für Wechsel in vorherigen Monat initialisieren
		Canvas graphicPreviousMonth = new Canvas(15, 15);
		GraphicsContext gcPreviousMonth = graphicPreviousMonth.getGraphicsContext2D();
		gcPreviousMonth.setFill(Color.BLACK);
		double[] xPreviousMonth = {0, 10, 10};
		double[] yPreviousMonth = {5,10, 0};
		gcPreviousMonth.fillPolygon(xPreviousMonth, yPreviousMonth,  3);
		Button buttonPreviousMonth = new Button(null, graphicPreviousMonth);
		buttonPreviousMonth.setOnAction(e -> {controller.handle(AppController.BUTTON_PREVIOUS_MONTH);});
		//Label zur Anzeige des Monats initialisieren
		Label labelMonth = new Label(model.getStringCurrentMonth());
		labelMonth.setMinWidth(70);
		//Label zur Anzeige des Jahres initialisieren
		Label labelYear = new Label("" + model.getCurrentYear());
		labelYear.setMinWidth(40);
		//Button für Wechsel in nächsten Monat initialisieren
		Canvas graphicNextMonth = new Canvas(15, 15);
		GraphicsContext gcNextMonth = graphicNextMonth.getGraphicsContext2D();
		gcNextMonth.setFill(Color.BLACK);
		double[] xNextMonth = {0, 0, 10};
		double[] yNextMonth = {0,10, 5};
		gcNextMonth.fillPolygon(xNextMonth, yNextMonth,  3);
		Button buttonNextMonth = new Button(null, graphicNextMonth);
		buttonNextMonth.setOnAction(e -> {controller.handle(AppController.BUTTON_NEXT_MONTH);});
		//Pane für Monatsauswahl zusammenfügen 
		paneMonth.getChildren().addAll(buttonPreviousMonth, labelMonth, labelYear, buttonNextMonth);

		AnchorPane.setTopAnchor(paneMonth, 0.0);
		AnchorPane.setRightAnchor(buttonNewDate, 0.0);
		AnchorPane.setBottomAnchor(grid, 0.0);
		rootPane.getChildren().addAll(paneMonth, grid, buttonNewDate); 
		return new Scene(rootPane);
	}

	private Scene getSceneEntry() {
		VBox pane = new VBox();
		//pane.setPrefSize(widthWindow,heightWindow); //noch ändern, da Windowsgröße > Pane	
		Label labelTitle = new Label("neuer Termin");
		labelTitle.setMinHeight(25);

		Label labelName = new Label("Name:");
		TextField textFieldName = new TextField();
		pane.getChildren().addAll(labelName, textFieldName);

		Label labelStartDate = new Label("Startdatum");
		DatePicker datePickerStart = new DatePicker();
		pane.getChildren().addAll(labelStartDate, datePickerStart);

		Label labelEndDate = new Label("Enddatum");
		DatePicker datePickerEnd = new DatePicker();
		pane.getChildren().addAll(labelEndDate, datePickerEnd);

		Label labelStartTime = new Label("StartZeit");
		TextField fieldStartTime = new TextField();
		pane.getChildren().addAll(labelStartTime, fieldStartTime);

		Label labelEndTime = new Label("EndZeit");
		TextField fieldEndTime = new TextField();
		pane.getChildren().addAll(labelEndTime, fieldEndTime);

		Button buttonSaveData = new Button("Speichern");
		buttonSaveData.setOnAction(e->{
			calendarDate = new CalendarDate(textFieldName.getText(), datePickerStart.getValue(), 
					datePickerEnd.getValue(), fieldStartTime.getText(), fieldEndTime.getText());
			controller.handle(AppController.BUTTON_SAVE_DATA);
			});
		pane.getChildren().add(buttonSaveData);
		
		return new Scene(pane);
	}

	public CalendarDate getCalendarDate() {
		return calendarDate;
	}
}
