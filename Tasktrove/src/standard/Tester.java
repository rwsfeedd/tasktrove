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
		
		BorderWidths borderWidths = new BorderWidths(2);
		BorderStrokeStyle borderStrokeStyle = BorderStrokeStyle.DOTTED;
		Paint stroke = null;
		
		BorderPane pane0 = new BorderPane();
		BorderStroke borderStroke0 = new BorderStroke(stroke, borderStrokeStyle, null, borderWidths);
		Border border0 = new Border(borderStroke0);
		pane0.setBorder(border0);
		BorderPane pane1 = new BorderPane();
		BorderStroke borderStroke1 = new BorderStroke(stroke, borderStrokeStyle, null, borderWidths);
		Border border1 = new Border(borderStroke1);
		pane1.setBorder(border1);
		BorderPane pane2 = new BorderPane();
		BorderStroke borderStroke2 = new BorderStroke(stroke, borderStrokeStyle, null, borderWidths);
		Border border2 = new Border(borderStroke2);
		pane2.setBorder(border2);
		BorderPane pane3 = new BorderPane();
		BorderStroke borderStroke3 = new BorderStroke(stroke, borderStrokeStyle, null, borderWidths);
		Border border3 = new Border(borderStroke3);
		pane3.setBorder(border3);
		
		BorderPane rootBorderPane = new BorderPane();
		rootBorderPane.setLeft(pane0);
		rootBorderPane.setRight(pane1);
		rootBorderPane.setTop(pane2);
		rootBorderPane.setBottom(pane3);
		Scene scene = new Scene(rootBorderPane, widthWindow, heightWindow);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}

}
