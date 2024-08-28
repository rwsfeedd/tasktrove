package standard;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class AppController extends Application{
	public final static int NEW_DATE = 0;
	public final static int BUTTON_SAVE_DATA = 1;
	public final static int BUTTON_NEXT_MONTH = 2;
	public final static int BUTTON_PREVIOUS_MONTH = 3;
	public final static int NO_BASE_DIRECTORY = 4;
	public AppModel model;
	public AppView view;

	public void start(Stage primaryStage) throws Exception{
		File baseDir = new File("baseDir");
		boolean dirError = false;
		if(!baseDir.exists()) { // lese/schreibrechte?
			try {
				baseDir.mkdir();
			}catch (SecurityException sEx){
				sEx.printStackTrace();
				dirError = true;
			}
		}
		model = new AppModel(this, baseDir);
		view = new AppView(primaryStage, model, this);
		view.update();
		if(dirError) {
			File newBaseDir = view.getBaseDirectory();
			if(newBaseDir != null && newBaseDir.exists() && newBaseDir.isDirectory()) {
				baseDir = newBaseDir;
			} else {
				System.err.println("baseDir konnte nicht erstellt werden!");
				Platform.exit();
			}
		}
	}//start
	public void handle(int componentID) {
		switch(componentID) {
			case NEW_DATE: 	
				model.setCurrentScene(AppModel.ENTRY_SCENE);
				view.update();
				break;
			case BUTTON_SAVE_DATA:
				CalendarDate calendarDate = view.getCalendarDate();
				if((calendarDate.validate() == CalendarDate.VALID)) model.writeIntoFile(calendarDate); 
				if((calendarDate.validate() & CalendarDate.INVALID_NAME) == CalendarDate.INVALID_NAME) System.out.println("Fehlender Name!");
				if((calendarDate.validate() & CalendarDate.INVALID_START_YEAR) == CalendarDate.INVALID_START_YEAR) System.out.println("Fehlendes Startjahr!");
				if((calendarDate.validate() & CalendarDate.INVALID_START_MONTH) == CalendarDate.INVALID_START_MONTH) System.out.println("Fehlender StartMonat!");
				if((calendarDate.validate() & CalendarDate.INVALID_START_DAY) == CalendarDate.INVALID_START_DAY) System.out.println("Fehlender StartTag");
				if((calendarDate.validate() & CalendarDate.INVALID_END_YEAR) == CalendarDate.INVALID_END_YEAR) System.out.println("Fehlender Endjahr!");
				if((calendarDate.validate() & CalendarDate.INVALID_END_MONTH) == CalendarDate.INVALID_END_MONTH) System.out.println("Fehlender EndMonat!");
				if((calendarDate.validate() & CalendarDate.INVALID_END_DAY) == CalendarDate.INVALID_END_DAY) System.out.println("Fehlender EndTag!");
				if((calendarDate.validate() & CalendarDate.INVALID_START_HOUR) == CalendarDate.INVALID_START_HOUR) System.out.println("Fehlender startstunde!");
				if((calendarDate.validate() & CalendarDate.INVALID_START_MINUTE) == CalendarDate.INVALID_START_MINUTE) System.out.println("Fehlender Startminute!");
				if((calendarDate.validate() & CalendarDate.INVALID_END_HOUR) == CalendarDate.INVALID_END_HOUR) System.out.println("Fehlender endstunde!");
				if((calendarDate.validate() & CalendarDate.INVALID_END_MINUTE) == CalendarDate.INVALID_END_MINUTE) System.out.println("Fehlender endminute!");

				
			case BUTTON_NEXT_MONTH: 
				model.setToNextMonth();
				view.update();
				break;
			case BUTTON_PREVIOUS_MONTH: 
				model.setToPreviousMonth();
				view.update();
				break;
			case NO_BASE_DIRECTORY: 
				try {
					File baseDir = view.getBaseDirectory();
					model.setBaseDir(baseDir);
				}catch(Exception ex) {
					ex.printStackTrace();
					Platform.exit();
				}
				break;
			default:	
				System.err.println("Unbekannte Komponente in handle() von AppController-Instanz!");
				Platform.exit();
		}
	}
	public static void main(String[] args) {
		launch(args);
	}//main
	
                                      
}
