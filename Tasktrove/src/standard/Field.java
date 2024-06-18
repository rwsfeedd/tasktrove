package standard;

import java.util.ArrayList;
import java.util.List;

public class Field {
	private static List<Tile> TileList = new ArrayList<>(35);
	
	public void addTile(Tile tile) {
		TileList.add(tile);
	}
	public Tile getTile(int id) {
		return TileList.get(id);
	}
}
