package standard;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class AppModel {
	public final static int CALENDAR_SCENE = 0;
	public final static int ENTRY_SCENE = 1;

	TimeZone timeZone;
	GregorianCalendar calendar;
	private int currentScene = 0;
	private int currentMonth;

	public AppModel() {
		timeZone = TimeZone.getDefault();
		calendar = new GregorianCalendar(timeZone);
		currentMonth = calendar.get(GregorianCalendar.MONTH);
		System.out.println(currentMonth);
	}
	
	public int[] getCalendarInfo() {
		//calculating days in month for Calendargrid
		int year = 2024;
		int daysInMonth = 0;
		if(currentMonth <0 | currentMonth >11) {
			System.err.println("In SceneFactory ist Int month nicht valide(Wert auﬂerhalb des Bereichs 1-12)!");
			Platform.exit();
		}
		if(currentMonth != Calendar.FEBRUARY && ((currentMonth)%7)%2 == 0) daysInMonth = 31;
		if(currentMonth != Calendar.FEBRUARY && ((currentMonth)%7)%2 == 1) daysInMonth = 30;
		if(currentMonth == Calendar.FEBRUARY && calendar.isLeapYear(year) == true) daysInMonth = 29;
		if(currentMonth == Calendar.FEBRUARY && calendar.isLeapYear(year) == false) daysInMonth = 28;
		
		//Weekday of first day in month for offset to establish order in View of month
		GregorianCalendar tempCalendar = (GregorianCalendar) calendar.clone();
		tempCalendar.set(Calendar.DAY_OF_MONTH, 1);
		tempCalendar.set(Calendar.MONTH, currentMonth);
		tempCalendar.set(Calendar.YEAR, year);
		int offset = 0;
		if(tempCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) { // first Weekday in Calendarclass is Sunday with int 1
			offset = 6;
		} else {
			offset = tempCalendar.get(Calendar.DAY_OF_WEEK) - 2; // Monday has int 2, subtract 2 to get Monday in first column of grid 
		}

		int[] ret = {offset, daysInMonth};
		return ret;
	}
	public int getCurrentScene() {
		return currentScene;
	}
	public void setCurrentScene(int nextScene) {
		currentScene = nextScene;
	}
	public String getCurrentMonth() {
		String erg = "";
		switch(currentMonth) {
		case Calendar.JANUARY:	erg = "Januar";
								break;
		case Calendar.FEBRUARY:	erg = "Februar";
								break;
		case Calendar.MARCH:	erg = "M‰rz";
								break;
		case Calendar.APRIL:	erg = "April";
								break;
		case Calendar.MAY:	erg = "Mai";
							break;
		case Calendar.JUNE:	erg = "Juni";
							break;
		case Calendar.JULY:	erg = "July";
							break;
		case Calendar.AUGUST:	erg = "August";
								break;
		case Calendar.SEPTEMBER:	erg = "September";
									break;
		case Calendar.OCTOBER:	erg = "Oktober";
								break;
		case Calendar.NOVEMBER:	erg = "November";
								break;
		case Calendar.DECEMBER:	erg = "Dezember";
								break;
		}
		return erg;
	}
}
