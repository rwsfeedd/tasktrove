package standard;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class Tile {
	
	private Pane pane;
	Image imageBlack = new Image("file:images/blackBackround.png");
	ImageView imageViewBlack = new ImageView(imageBlack);
	Image imageWhite = new Image("file:images/whiteBackround.png");
	ImageView imageViewWhite = new ImageView(imageWhite);
	
	public Tile(int number) {
		this.pane = new AnchorPane();
		pane.setBorder(new Border(new BorderStroke(null, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
		pane.setMinSize(100, 100);
		Label label = new Label(Integer.toString(number));
		pane.getChildren().add(label);
		AnchorPane.setBottomAnchor(label, 0.0);
		AnchorPane.setRightAnchor(label, 0.0);
	}
		
	public Node getNode() {
		return pane;
	}
}
