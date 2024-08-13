package standard;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

public class Tester extends Application{
	
	public void start(Stage primaryStage) throws Exception{
		SceneFactory windowsFactory = new SceneFactory();
		Scene testScene = windowsFactory.getScene(1);
		Scene basicScene = windowsFactory.getScene(0);
		//setStage
		primaryStage.setScene(basicScene);
		primaryStage.setMinHeight(SceneFactory.heightWindow);
		primaryStage.setMinWidth(SceneFactory.widthWindow);
		primaryStage.show();
	}//start
	
	public static void main(String[] args) {
		launch(args);
	}//main
                                      
}
