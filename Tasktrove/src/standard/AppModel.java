package standard;


import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

import javax.xml.XMLConstants;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import javafx.application.Platform;
public class AppModel {
	public final static int CALENDAR_SCENE = 0;
	public final static int ENTRY_SCENE = 1;

	TimeZone timeZone;
	GregorianCalendar calendar;
	private int currentScene = CALENDAR_SCENE;
	private LinkedList<CalendarDate> currentDates;
	private int intCurrentMonth;
	private int currentYear;
	private File xmlDataFile;
	private String dtdDataFile;

	public AppModel() {
		timeZone = TimeZone.getDefault();
		calendar = new GregorianCalendar(timeZone);
		intCurrentMonth = calendar.get(GregorianCalendar.MONTH);
		currentYear = calendar.get(Calendar.YEAR);
		try {
			xmlDataFile = new File("src\\TesterDatafile.xml");
			dtdDataFile = "src\\TesterDatafile.dtd";
			System.out.println(xmlDataFile.canRead());
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		AppXMLProcessor processor = new AppXMLProcessor(xmlDataFile);
		currentDates = processor.readFromXMLFile();
	}
	
	public int[] getCalendarInfo() {
		//calculating days in month for Calendargrid
		int daysInMonth = 0;
		if(intCurrentMonth <0 | intCurrentMonth >11) {
			System.err.println("In SceneFactory ist Int month nicht valide(Wert auﬂerhalb des Bereichs 1-12)!");
			Platform.exit();
		}
		if(intCurrentMonth != Calendar.FEBRUARY && ((intCurrentMonth)%7)%2 == 0) daysInMonth = 31;
		if(intCurrentMonth != Calendar.FEBRUARY && ((intCurrentMonth)%7)%2 == 1) daysInMonth = 30;
		if(intCurrentMonth == Calendar.FEBRUARY && calendar.isLeapYear(currentYear) == true) daysInMonth = 29;
		if(intCurrentMonth == Calendar.FEBRUARY && calendar.isLeapYear(currentYear) == false) daysInMonth = 28;
		
		//Weekday of first day in month for offset to establish order in View of month
		GregorianCalendar tempCalendar = (GregorianCalendar) calendar.clone();
		tempCalendar.set(Calendar.DAY_OF_MONTH, 1);
		tempCalendar.set(Calendar.MONTH, intCurrentMonth);
		tempCalendar.set(Calendar.YEAR, currentYear);
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

	public String getStringCurrentMonth() {
		String erg = "";
		switch(intCurrentMonth) {
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
	
	//hier xmlfilechange integrieren
	public void setToNextMonth() {
		if(intCurrentMonth == Calendar.DECEMBER) {
			intCurrentMonth = Calendar.JANUARY;
			currentYear++;
		}else {
			intCurrentMonth++;
		}
	}
	
	public void setToPreviousMonth() {
		if(intCurrentMonth == Calendar.JANUARY) {
			intCurrentMonth = Calendar.DECEMBER;
			currentYear--;							//maximalGrenze implementieren
		}else {
			intCurrentMonth--;
		}
	}
	
	public int getCurrentYear() {
		return currentYear;
	}
	
	public void writeIntoFile(CalendarDate calendarDate) {
		try {
			AppXMLProcessor processor = new AppXMLProcessor(xmlDataFile);
			processor.writeIntoXMLFile(calendarDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public LinkedList<CalendarDate> getCurrentDates() {
		return currentDates;
	}
}
