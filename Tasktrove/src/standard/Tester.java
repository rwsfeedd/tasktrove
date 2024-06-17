package standard;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Tester extends Application{

	public void start(Stage primaryStage) throws Exception{
		Label label = new Label("Hello Test23");
		Scene scene = new Scene(new StackPane(label), 640, 480);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}

}
