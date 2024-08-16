package standard;

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
	public AppModel model;
	public AppView view;

	public void start(Stage primaryStage) throws Exception{
		model = new AppModel();
		view = new AppView(primaryStage, model, this);
		view.update();
	}//start
	public void handle(int componentID) {
		switch(componentID) {
			case NEW_DATE: 	model.setCurrentScene(AppModel.ENTRY_SCENE);
							break;
			case BUTTON_NEXT_MONTH: model.setToNextMonth();
									break;
			case BUTTON_PREVIOUS_MONTH: model.setToPreviousMonth();
									break;
			default:	System.err.println("Unbekannte Komponente in handle() von AppController-Instanz!");
						Platform.exit();
		}
		view.update();
	}
	public static void main(String[] args) {
		launch(args);
	}//main
	
                                      
}
