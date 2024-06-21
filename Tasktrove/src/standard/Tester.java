package standard;

import javafx.application.Application;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class Tester extends Application{
	public int heightWindow = 600;
	public int widthWindow = 900;
	
	public void start(Stage primaryStage) throws Exception{
		
		Field field = new Field();
		for(int i = 0; i < field.getTileCount(); i++) {
			System.out.println("Field " + i + " :x= " + field.getTile(i).getX() + ",y= " + field.getTile(i).getY());
		}
		
		
		//stretch backround to needed parameters
		
		Image image = new Image("file:images/blackBackround.png");
		ImageView imageView = new ImageView(image);
		Label label = new Label("x of Field 0 = " + field.getTile(0).getX());
		Label label1 = new Label("x of Field 1 = " + field.getTile(1).getX());
		Label label2 = new Label("x of Field 2 = " + field.getTile(2).getX());
		

		//für die Randleiste der Szene
		Paint stroke = null;
		BorderStrokeStyle borderStrokeStyle = BorderStrokeStyle.DOTTED;
		CornerRadii radii = null;
		BorderWidths borderWidths = new BorderWidths(2);
		
		BorderPane pane[] = new BorderPane[4];
		Border border[] = new Border[4];
		BorderStroke borderStroke[] = new BorderStroke[4];
		for(int i = 0; i < 4;i++) {
			borderStroke[i] = new BorderStroke(stroke, borderStrokeStyle, radii, borderWidths);
			pane[i] = new BorderPane();
			border[i] = new Border(borderStroke[i]);
			pane[i].setBorder(border[i]);
		}
		BorderPane rootBorderPane = new BorderPane();
		rootBorderPane.setLeft(pane[0]);
		rootBorderPane.setRight(pane[1]);
		rootBorderPane.setTop(pane[2]);
		rootBorderPane.setBottom(pane[3]);
		
		//für das Zentrum der Scene
		
		
		
		Scene scene = new Scene(rootBorderPane, widthWindow, heightWindow);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}

}
