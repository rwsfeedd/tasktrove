package standard;

import java.io.Serializable;

public class CalendarDate implements Serializable{
	int y;
	int x;
	
	CalendarDate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	int getX() {
		return x;
	}
	int getY() {
		return y;
	}
}
