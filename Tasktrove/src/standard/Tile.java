package standard;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class Tile {
	
	private int type;
	private Pane pane;
	Image imageBlack = new Image("file:images/blackBackround.png");
	ImageView imageViewBlack = new ImageView(imageBlack);
	Image imageWhite = new Image("file:images/whiteBackround.png");
	ImageView imageViewWhite = new ImageView(imageWhite);
	
	public Tile(int type) {
		this.pane = new GridPane();
		pane.setBorder(new Border(new BorderStroke(null, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
		pane.setMinSize(100, 100);
		this.type = type;
	}
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
		
	public Node getNode() {
		return pane;
	}
}
