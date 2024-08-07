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
	
	public Scene basicLayout() {
		
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
		GridPane grid = new GridPane();
		List<Tile> tileList = new ArrayList<Tile>(35);
		Node node;
		int y = 0;
		for(int i = 0; i < 35; i++) {
			tileList.add(new Tile(i));
			tileList.get(i).setType(i%2);
			node = tileList.get(i).getNode();
			grid.add(node, i%5, y);
			if(i%5 > 3) y++;
		}
		//grid.gridLinesVisibleProperty();
		rootBorderPane.setCenter(grid);
		
		Scene basicScene = new Scene(rootBorderPane, WindowsFactory.widthWindow, WindowsFactory.heightWindow);
		return basicScene;
		
	}//basicLayout()
	
	public void start(Stage primaryStage) throws Exception{
		WindowsFactory test = new WindowsFactory();
		Scene testScene = test.scene;
		Scene basicScene = basicLayout();
		//setStage
		primaryStage.setScene(basicScene);
		primaryStage.setMinHeight(WindowsFactory.heightWindow);
		primaryStage.setMinWidth(WindowsFactory.widthWindow);
		primaryStage.show();
	}//start
	
	public static void main(String[] args) {
		launch(args);
	}//main

}
