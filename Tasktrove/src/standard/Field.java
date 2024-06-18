package standard;

import java.util.ArrayList;
import java.util.List;

public class Field {
	private int fieldWidth = 7;
	private int fieldHeight = 5;
	private int tileCount = fieldWidth * fieldHeight;  
	
	private List<Tile> TileList = new ArrayList<>(tileCount);
	
	public Field() {
		int y = 0, x = 0;
		for(int i = 0; i < tileCount; i++) {
			TileList.add(new Tile(x , y, 0));
			x++;
			if(x >= fieldWidth) {
				x = 0;
				y++;
			}
		}
	}
	public void addTile(Tile tile) {
		TileList.add(tile.getX()*tile.getY(), tile);
	}
	public Tile getTile(int id) {
		return TileList.get(id);
	}
	public int getTileCount() {
		return tileCount;
	}
}
