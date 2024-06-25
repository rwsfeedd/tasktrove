package standard;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Tile {
	private int x;
	private int y;
	private int type;
	private Node node;
	Image imageBlack = new Image("file:images/blackBackround.png");
	ImageView imageViewBlack = new ImageView(imageBlack);
	Image imageWhite = new Image("file:images/whiteBackround.png");
	ImageView imageViewWhite = new ImageView(imageWhite);
	/*
	 * @param x
	 * @param y
	 * @param type
	 */
	public Tile(int x, int y, int type) {
		this.x = x;
		this.y = y;
		this.type = type;
		
	}
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	public Tile getTile() {
		return this;
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
