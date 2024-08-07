package standard;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Tile {
	
	private int type;
	private Node node;
	Image imageBlack = new Image("file:images/blackBackround.png");
	ImageView imageViewBlack = new ImageView(imageBlack);
	Image imageWhite = new Image("file:images/whiteBackround.png");
	ImageView imageViewWhite = new ImageView(imageWhite);
	
	public Tile(int type) {
		this.type = type;
		
	}
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
		
	public Node getNode() {
		switch(type) {
			case 0:
				return imageViewWhite;
			case 1:
				return imageViewBlack;
			default:
				return imageViewBlack;
		}

	}
}
