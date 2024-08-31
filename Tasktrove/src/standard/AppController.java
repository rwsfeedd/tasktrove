package standard;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
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
	public static enum CalendarScene {
		NEW_DATE,
		BUTTON_NEXT_MONTH,
		BUTTON_PREVIOUS_MONTH,
		BUTTON_DELETE_DATE,
		NO_BASE_DIR,
		BUTTON_TASKS
	}

	public static enum DateEntryScene {
		BUTTON_SAVE_DATA,
		BUTTON_CANCEL
	}
	
	public static enum DateDeleteScene {
		BUTTON_CANCEL,
		BUTTON_DELETE
	}
	
	public static enum TaskStandardScene {
		BUTTON_CANCEL,
		BUTTON_DELETE,
		BUTTON_CREATE,
		BUTTON_DONE
	}

	public static enum TaskCreateScene {
		BUTTON_CANCEL,
		BUTTON_SAVE,
	}

	public static enum TaskDeleteScene {
		BUTTON_CANCEL,
		BUTTON_DELETE
	}
	
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

	public void handle(CalendarScene componentID) {
		switch(componentID) {
			case NEW_DATE: 	
				model.setCurrentScene(AppModel.CurrentScene.ENTRY_SCENE);
				view.update();
				break;
			case BUTTON_NEXT_MONTH: 
				model.setToNextMonth();
				view.update();
				break;
			case BUTTON_PREVIOUS_MONTH: 
				model.setToPreviousMonth();
				view.update();
				break;
			case NO_BASE_DIR: 
				try {
					File baseDir = view.getBaseDirectory();
					model.setBaseDir(baseDir);
				}catch(Exception ex) {
					ex.printStackTrace();
					Platform.exit();
				}
				break;
			case BUTTON_DELETE_DATE: 
				model.setCurrentScene(AppModel.CurrentScene.DELETE_SCENE);
				view.update();
				break;
			case BUTTON_TASKS:
				model.setCurrentScene(AppModel.CurrentScene.TASK_DEFAULT_SCENE);
				view.update();
				break;
			default:	
				System.err.println("Unbekannte Komponente in handle(CalendarScene) von AppController-Instanz!");
				Platform.exit();
		}
	}
	
	public void handle(DateEntryScene componentID) {
		switch(componentID) {
		case BUTTON_SAVE_DATA:
				CalendarDate calendarDate = view.getCalendarDate();
				if((calendarDate.validate() == CalendarDate.VALID)) model.writeDateIntoFile(calendarDate); 
				if((calendarDate.validate() & CalendarDate.INVALID_NAME) == CalendarDate.INVALID_NAME) System.out.println("kein valider Name!");
				if((calendarDate.validate() & CalendarDate.INVALID_START_DATE) == CalendarDate.INVALID_START_DATE) System.out.println("kein valides Startjahr!");
				if((calendarDate.validate() & CalendarDate.INVALID_END_DATE) == CalendarDate.INVALID_END_DATE) System.out.println("kein valides Endjahr!");
				if((calendarDate.validate() & CalendarDate.INVALID_DATE_LENGTH) == CalendarDate.INVALID_DATE_LENGTH) System.out.println("kein valides Endjahr!");
				if((calendarDate.validate() & CalendarDate.INVALID_START_HOUR) == CalendarDate.INVALID_START_HOUR) System.out.println("keine valide startstunde!");
				if((calendarDate.validate() & CalendarDate.INVALID_START_MINUTE) == CalendarDate.INVALID_START_MINUTE) System.out.println("keine valide Startminute!");
				if((calendarDate.validate() & CalendarDate.INVALID_END_HOUR) == CalendarDate.INVALID_END_HOUR) System.out.println("keine valide endstunde!");
				if((calendarDate.validate() & CalendarDate.INVALID_END_MINUTE) == CalendarDate.INVALID_END_MINUTE) System.out.println("keine valide endminute!");
				break;
		case BUTTON_CANCEL: 
				model.setCurrentScene(AppModel.CurrentScene.CALENDAR_SCENE);
				view.update();
				break;
		default:	
				System.err.println("Unbekannte Komponente in handle(DateEntryScene) von AppController-Instanz!");
				Platform.exit();
		}
	}
	
	public void handle(DateDeleteScene componentID) {
		switch(componentID) {
		case BUTTON_CANCEL: 
				model.setCurrentScene(AppModel.CurrentScene.CALENDAR_SCENE);
				view.update();
				break;
		case BUTTON_DELETE:
			model.deleteDatesInFile(view.getListDates()); 
			view.update();
			break;
		default:	
				System.err.println("Unbekannte Komponente in handle(DateDeleteScene) von AppController-Instanz!");
				Platform.exit();
				break;
		}
	}
	
	public void handle(TaskStandardScene componentID) {
		switch(componentID) {
		case BUTTON_CREATE:
			model.setCurrentScene(AppModel.CurrentScene.TASK_CREATE_SCENE);
			view.update();
			break;
		case BUTTON_DELETE:
			model.setCurrentScene(AppModel.CurrentScene.TASK_DELETE_SCENE);
			view.update();
			break;
		case BUTTON_CANCEL:
			model.setCurrentScene(AppModel.CurrentScene.CALENDAR_SCENE);
			view.update();
			break;
		case BUTTON_DONE:
			//Tasks deren Status geändert werden sollen von view abrufen
			LinkedList<AppTask> listTasks = view.getListTasks();
			//Tasks an Model übergeben, welches die Änderungen in das File schreibt
			model.updateTasksInFile(listTasks);
			//wenn aktive Aufgaben geändert wurden, müssen die Punkte noch richtig addiert werden
			int punkte = 0;
			for(int i = 0; i < listTasks.size(); i++) {
				if(!(listTasks.get(i).isDone())) {
					switch(listTasks.get(i).getDifficulty()) {
					case LOW:
						punkte += 2;
						break;
					case MEDIUM:
						punkte += 5;
						break;
					case HIGH:
						punkte += 8;
						break;
					}
				}
			}
			if(punkte != 0) {
				model.addPointsInFile(punkte);
			}
			//view holt sich diese neue Taskliste ab und wird geupdated 
			view.update();
			break;
		default:
			System.err.println("Unbekannte Komponente in handle(TaskDefaultScene) von AppController-Instanz!");
			Platform.exit();
			break;
		}
	}
	
	public void handle(TaskCreateScene componentID) {
		switch(componentID) {
		case BUTTON_CANCEL:
			model.setCurrentScene(AppModel.CurrentScene.TASK_DEFAULT_SCENE);
			view.update();
			break;
		case BUTTON_SAVE:
			AppTask task = view.getTask();
			if(task.isValid()) model.writeTaskIntoFile(task);
			break;
		default:
			System.err.println("Unbekannte Komponente in handle(TaskCreateScene) von AppController-Instanz!");
			Platform.exit();
			break;
		
		}
	}
	
	public void handle(TaskDeleteScene componentID) {
		switch(componentID) {
		case BUTTON_CANCEL:
			model.setCurrentScene(AppModel.CurrentScene.TASK_DEFAULT_SCENE);
			view.update();
			break;
		case BUTTON_DELETE:
			LinkedList<AppTask> listChangedTasks = view.getListTasks();
			if(!listChangedTasks.isEmpty()) {
				model.deleteTasksInFile(listChangedTasks);
			}
			view.update();
			break;
		default:
			System.err.println("Unbekannte Komponente in handle(TaskDeleteScene) von AppController-Instanz!");
			Platform.exit();
			break;
		
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}//main
	
                                      
}
