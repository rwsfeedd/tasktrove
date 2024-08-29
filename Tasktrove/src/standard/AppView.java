package standard;

import java.io.File;
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
import javafx.scene.control.CheckBox;
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
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import standard.AppController.CalendarScene;
import standard.AppController.DateDeleteScene;
import standard.AppController.DateEntryScene;
import standard.AppModel.CurrentScene;

public class AppView{
	public final double heightWindow = 700;
	public final double widthWindow = 700;
	private Stage primaryStage;
	private AppModel model;
	private AppController controller;
	private CalendarDate calendarDate;
	private LinkedList<CalendarDate> listDates;

	public AppView(Stage primaryStage, AppModel model, AppController controller) {
		this.primaryStage = primaryStage;
		this.model = model;
		this.controller = controller;
		primaryStage.setMinHeight(heightWindow); 		
		primaryStage.setMinWidth(widthWindow);
	}

	public void update() {

		switch(model.getCurrentScene()) {
			case AppModel.CurrentScene.ENTRY_SCENE: 
				primaryStage.setScene(getSceneEntry());
				break;
			case AppModel.CurrentScene.DELETE_SCENE: 
				primaryStage.setScene(getSceneDelete());
				break;
			case AppModel.CurrentScene.CALENDAR_SCENE:
				primaryStage.setScene(getSceneCalendar());
				break;
			default: 
				primaryStage.setScene(getSceneCalendar());
				break;
		}
		//System.out.println(model.getCurrentScene().toString());
		primaryStage.show();
	}

	private Scene getSceneDelete() {
		listDates = model.getCurrentDates();
		AnchorPane rootPane = new AnchorPane();

		VBox paneSelection = new VBox();
		LinkedList<CheckBox> nodeSelection = new LinkedList<CheckBox>();
		if(!(listDates == null)) {
			for(int i = 0; i < listDates.size(); i++) {
				nodeSelection.add(new CheckBox(listDates.get(i).getName() + ": von " + listDates.get(i).getStartDate().toString() 
						+ " bis " + listDates.get(i).getEndDate().toString()));
				paneSelection.getChildren().add(nodeSelection.getLast());
			}
			rootPane.getChildren().add(paneSelection);
		}

		VBox panelButtons = new VBox();
		Button buttonCancel = new Button("Abbrechen");
		buttonCancel.setOnAction(e->{controller.handle(DateDeleteScene.BUTTON_CANCEL);});
		panelButtons.getChildren().add(buttonCancel);

		Button buttonDelete = new Button("L�schen");
		buttonDelete.setOnAction(e->{
			LinkedList<CalendarDate> datesToDelete = new LinkedList<CalendarDate>();
			if(nodeSelection != null) {
				for(int l = 0; l < nodeSelection.size(); l++) {
					if(nodeSelection.get(l).isSelected() == true) {
						datesToDelete.add(listDates.get(l));
					}
				}
			}
			
			listDates = datesToDelete;
			//System.out.println(listDates.get(0).getName());
			controller.handle(DateDeleteScene.BUTTON_DELETE);});

		panelButtons.getChildren().add(buttonDelete);
		rootPane.getChildren().add(panelButtons);

		AnchorPane.setRightAnchor(panelButtons, 0.0);
		AnchorPane.setLeftAnchor(paneSelection, 0.0);
		
		return new Scene(rootPane);
	}
	private Scene getSceneCalendar() {
		//buildingCalendarScene
		VBox rootPane = new VBox();
		int[] calendarData = model.getCalendarInfo();
			
		HBox paneTop = new HBox();
		//Button f�r Wechsel in vorherigen Monat initialisieren
		Canvas graphicPreviousMonth = new Canvas(15, 15);
		GraphicsContext gcPreviousMonth = graphicPreviousMonth.getGraphicsContext2D();
		gcPreviousMonth.setFill(Color.BLACK);
		double[] xPreviousMonth = {0, 10, 10};
		double[] yPreviousMonth = {5,10, 0};
		gcPreviousMonth.fillPolygon(xPreviousMonth, yPreviousMonth,  3);
		Button buttonPreviousMonth = new Button(null, graphicPreviousMonth);
		buttonPreviousMonth.setOnAction(e -> {controller.handle(CalendarScene.BUTTON_PREVIOUS_MONTH);});
		paneTop.getChildren().add(buttonPreviousMonth);
		//Label zur Anzeige des Monats initialisieren
		Label labelMonth = new Label(model.getStringCurrentMonth());
		labelMonth.setMinWidth(70);
		paneTop.getChildren().add(labelMonth);
		//Label zur Anzeige des Jahres initialisieren
		Label labelYear = new Label("" + model.getCurrentYear());
		labelYear.setMinWidth(40);
		paneTop.getChildren().add(labelYear);
		//Button f�r Wechsel in n�chsten Monat initialisieren
		Canvas graphicNextMonth = new Canvas(15, 15);
		GraphicsContext gcNextMonth = graphicNextMonth.getGraphicsContext2D();
		gcNextMonth.setFill(Color.BLACK);
		double[] xNextMonth = {0, 0, 10};
		double[] yNextMonth = {0,10, 5};
		gcNextMonth.fillPolygon(xNextMonth, yNextMonth,  3);
		Button buttonNextMonth = new Button(null, graphicNextMonth);
		buttonNextMonth.setOnAction(e -> {controller.handle(CalendarScene.BUTTON_NEXT_MONTH);});
		paneTop.getChildren().add(buttonNextMonth);
		//Button f�r neuen Termin initialisieren 
		Button buttonNewDate = new Button("neuer Termin");
		buttonNewDate.setOnAction(e -> {controller.handle(CalendarScene.NEW_DATE);});
		paneTop.getChildren().add(buttonNewDate);
		//Button f�r l�schen von Terminen initialisieren 
		Button buttonDeleteDate = new Button("Termin l�schen");
		buttonDeleteDate.setOnAction(e->{
			controller.handle(CalendarScene.BUTTON_DELETE_DATE);});
		paneTop.getChildren().add(buttonDeleteDate);
		rootPane.getChildren().add(paneTop);

		GridPane grid = new GridPane();
		List<Tile> tileList = new ArrayList<Tile>(35); // reduzieren
		Node node;
		LinkedList<CalendarDate> currentDates = model.getCurrentDates();
		//initialize Tiles for CalendarView
		//Tile shows a Date if currentDay is between startDate and endDate
		int y = 0;
		LinkedList<CalendarDate> sectionCurrentDates;
		for(int i = 0; i < calendarData[1]; i++) {
			sectionCurrentDates = new LinkedList<CalendarDate>();
			if(!(currentDates == null)) {
				for(int j = 0; j < currentDates.size(); j++) {
					if(currentDates.get(j).getStartDate().getYear() == model.getCurrentYear() 
							&& currentDates.get(j).getStartDate().getMonthValue() == model.getCurrentMonth().getValue()
							&& currentDates.get(j).getStartDate().getDayOfMonth() == i+1
							) { 
						sectionCurrentDates.add(currentDates.get(j));
						continue;
					}

					LocalDate currentLocalDate = LocalDate.of(model.getCurrentYear(), model.getCurrentMonth(), i+1);
					if(currentLocalDate.compareTo(currentDates.get(j).getStartDate()) > -1 
							&& currentLocalDate.compareTo(currentDates.get(j).getEndDate()) < 1) {
						sectionCurrentDates.add(currentDates.get(j));
					}
				}
			} 				
			tileList.add(new Tile(i+1, sectionCurrentDates));
			node = tileList.get(i).getNode();
			sectionCurrentDates.clear();
			grid.add(node, (i+calendarData[0])%7, y);
			if((i+calendarData[0])%7 > 5) y++;
		}
		rootPane.getChildren().add(grid);
		return new Scene(rootPane);
	}

	private Scene getSceneEntry() {
		VBox pane = new VBox();
		//pane.setPrefSize(widthWindow,heightWindow); //noch �ndern, da Windowsgr��e > Pane	
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

		HBox buttonPanel = new HBox();
		Button buttonSaveData = new Button("Speichern");
		buttonSaveData.setOnAction(e->{
			calendarDate = new CalendarDate(textFieldName.getText(), datePickerStart.getValue(), 
					datePickerEnd.getValue(), fieldStartTime.getText(), fieldEndTime.getText());
			controller.handle(DateEntryScene.BUTTON_SAVE_DATA);
			});
		buttonPanel.getChildren().add(buttonSaveData);
		Button buttonCancel = new Button("Abbrechen");
		buttonCancel.setOnAction(e->{controller.handle(DateEntryScene.BUTTON_CANCEL);});
		buttonPanel.getChildren().add(buttonCancel);
		
		pane.getChildren().add(buttonPanel);
		
		return new Scene(pane);
	}
	
	public File getBaseDirectory() {
		DirectoryChooser chooser = new DirectoryChooser();
		return chooser.showDialog(primaryStage);
	}

	public CalendarDate getCalendarDate() {
		return calendarDate;
	}
	
	public LinkedList<CalendarDate> getListDates() {
		return listDates;
	}
}
