package standard;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Tester extends Application{
	public int heightWindow = 600;
	public int widthWindow = 900;
	
	public void start(Stage primaryStage) throws Exception{
		Tile tile0 = new Tile(0, 0, 0);
		Tile tile1 = new Tile(4, 0, 0);
		Tile tile2 = new Tile(30, 0, 0);
		Field field = new Field();
		
		field.addTile(tile0);
		field.addTile(tile1);
		field.addTile(tile2);
		
		for(int i = 0; i < 3; i++) {
			System.out.println(field.getTile(i).x);
		}
		
		Image image = new Image("file:images/blackBackround.png");
		ImageView imageView = new ImageView(image);
		Label label = new Label("x of Field 0 = " + field.getTile(0).x);
		Label label1 = new Label("x of Field 1 = " + field.getTile(1).x);
		Label label2 = new Label("x of Field 2 = " + field.getTile(2).x);
		TilePane pane = new TilePane(imageView);
		VBox rootVBox = new VBox(label, label1, label2, pane);
		Scene scene = new Scene(rootVBox, widthWindow, heightWindow);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}

}
