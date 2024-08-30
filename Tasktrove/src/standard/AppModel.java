package standard;


import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.time.LocalDate;
import java.time.Month;
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
import standard.AppController.CalendarScene;
public class AppModel {
	public static enum CurrentScene {
		CALENDAR_SCENE,
		ENTRY_SCENE,
		DELETE_SCENE,
		TASK_DEFAULT_SCENE,
		TASK_CREATE_SCENE,
		TASK_DELETE_SCENE
	}

	TimeZone timeZone;
	GregorianCalendar calendar;
	private CurrentScene currentScene;
	private LinkedList<CalendarDate> currentDates;
	private Month currentMonth;
	private int currentYear;
	private File baseDir;
	private File xmlDataFile;
	private File configFile;
	private AppController controller;

	public AppModel(AppController controller, File baseDir) { 
		currentScene = CurrentScene.CALENDAR_SCENE;
		this.controller = controller;
		timeZone = TimeZone.getDefault();
		calendar = new GregorianCalendar(timeZone);
		currentMonth = CalendarDate.parseMonthCalendarToEnum(calendar.get(GregorianCalendar.MONTH));
		currentYear = calendar.get(Calendar.YEAR);
		this.baseDir = baseDir;
	}
	
	public int[] getCalendarInfo() {
		//calculating days in month for Calendargrid
		int daysInMonth = 0;
		if(currentMonth.getValue() <1 | currentMonth.getValue() >12) {
			System.err.println("In SceneFactory ist Int month nicht valide(Wert auﬂerhalb des Bereichs 1-12)!");
			Platform.exit();
		}
		if(currentMonth != Month.FEBRUARY && ((currentMonth.getValue()-1)%7)%2 == 0) daysInMonth = 31;
		if(currentMonth != Month.FEBRUARY && ((currentMonth.getValue()-1)%7)%2 == 1) daysInMonth = 30;
		if(currentMonth == Month.FEBRUARY && calendar.isLeapYear(currentYear) == true) daysInMonth = 29;
		if(currentMonth == Month.FEBRUARY && calendar.isLeapYear(currentYear) == false) daysInMonth = 28;
		
		//Weekday of first day in month for offset to establish order in View of month
		GregorianCalendar tempCalendar = (GregorianCalendar) calendar.clone();
		tempCalendar.set(Calendar.DAY_OF_MONTH, 1);
		tempCalendar.set(Calendar.MONTH, (currentMonth.getValue()-1)); // -1 because GregorianCalendar.Month = [0,11] and Month.getValue = [1,12]
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
	public CurrentScene getCurrentScene() {
		return currentScene;
	}
	
	public void setCurrentScene(CurrentScene currentScene) {
		this.currentScene = currentScene;
	}

	public String getStringCurrentMonth() {
		String erg = "";
		switch(currentMonth) {
		case JANUARY:	erg = "Januar";
								break;
		case FEBRUARY:	erg = "Februar";
								break;
		case MARCH:	erg = "M‰rz";
								break;
		case APRIL:	erg = "April";
								break;
		case MAY:	erg = "Mai";
							break;
		case JUNE:	erg = "Juni";
							break;
		case JULY:	erg = "July";
							break;
		case AUGUST:	erg = "August";
								break;
		case SEPTEMBER:	erg = "September";
									break;
		case OCTOBER:	erg = "Oktober";
								break;
		case NOVEMBER:	erg = "November";
								break;
		case DECEMBER:	erg = "Dezember";
								break;
		}
		return erg;
	}
	
	
	//hier xmlfilechange integrieren
	public void setToNextMonth() {
		if(currentMonth == Month.DECEMBER) {
			currentMonth = Month.JANUARY;
			currentYear++;
		}else {
			currentMonth = currentMonth.plus(1);
		}
	}
	
	public void setToPreviousMonth() {
		if(currentMonth == Month.JANUARY) {
			currentMonth = Month.DECEMBER;
			currentYear--;							//maximalGrenze implementieren
		}else {
			currentMonth = currentMonth.minus(1);
		}
	}

	public Month getCurrentMonth() {
		return currentMonth;
	}

	public int getCurrentYear() {
		return currentYear;
	}

	/**
	 * Writes specified CalendarDate into the right xml File.
	 * Creates new Directory for FileStorage if needed. 
	 * If that fails, method calls the Controller to handle everything
	 * Creates new xmlFile for CalendarDate Storage if needed. 
	 * @param calendarDate
	 */
	public void writeDateIntoFile(CalendarDate calendarDate) {
		if(calendarDate == null) {
			System.err.println("calendarDate null bei Methode writeIntoFile() in Klasse AppModel");
			return;
		}
		if(!baseDir.exists()) {
			baseDir.mkdir();
		}
		if(!baseDir.canRead() || !baseDir.canWrite()) {
			controller.handle(CalendarScene.NO_BASE_DIR);
		}
		
		LocalDate currentDate = calendarDate.getStartDate();
		while(currentDate.isBefore(calendarDate.getEndDate()) || currentDate.isEqual(calendarDate.getEndDate())) {
			try {
				xmlDataFile = new File(baseDir, "Dates_" + currentDate.getYear() + "_" + currentDate.getMonthValue() + ".xml");
				if(!xmlDataFile.exists()) {
					xmlDataFile.createNewFile();
				}
			}catch (Exception ex){
				ex.printStackTrace();
				Platform.exit();
			}
			try {
				AppFileProcessor processor = new AppFileProcessor(xmlDataFile);
				processor.appendToXMLFile(calendarDate);
			} catch (Exception e) {
				e.printStackTrace();
			}
			currentDate = currentDate.plusMonths(1);
			currentDate = currentDate.withDayOfMonth(1);
		} 
		
	} 
	
	public void deleteDatesInFile(LinkedList<CalendarDate> removableDates) {
		if(removableDates == null) {
			System.err.println("calendarDate null bei Methode deleteInFile() in Klasse AppModel");
			return;
		}
		if(!baseDir.exists()) {
			baseDir.mkdir();
		}
		if(!baseDir.canRead() || !baseDir.canWrite()) {
			controller.handle(CalendarScene.NO_BASE_DIR);
		}
		for(int i = 0; i < removableDates.size(); i++) {
			LocalDate currentDate = removableDates.get(i).getStartDate();
			while(currentDate.isBefore(removableDates.get(i).getEndDate()) || currentDate.isEqual(removableDates.get(i).getEndDate())) {
				try {
					xmlDataFile = new File(baseDir, "Dates_" + currentDate.getYear() + "_" + currentDate.getMonthValue() + ".xml");
					if(!xmlDataFile.exists()) {
						currentDate = currentDate.plusMonths(1);
						currentDate = currentDate.withDayOfMonth(1);
						continue;
					}
				}catch (Exception ex){
					ex.printStackTrace();
					Platform.exit();
				}

				try {
					AppFileProcessor processor = new AppFileProcessor(xmlDataFile);
					LinkedList<CalendarDate> listOriginal = processor.readFromXMLFile();
					LinkedList<CalendarDate> listRet = new LinkedList<CalendarDate>();
					if(listRet != null) {
						while(!(listOriginal.isEmpty()) && !(listOriginal == null)) {
							if(listOriginal.getLast().compareTo(removableDates.getLast()) == 0) {
								listOriginal.removeLast();
							} else {
								listRet.add(listOriginal.removeLast());
							}
						}
					}
					
					processor.rewriteXMLFile(listRet);
				} catch (Exception e) {
					e.printStackTrace();
				}
				currentDate = currentDate.plusMonths(1);
				currentDate = currentDate.withDayOfMonth(1);
			}//while
		}//for
}//deleteInFile()

	public LinkedList<CalendarDate> getCurrentDates() {
		if(baseDir == null) {
			return new LinkedList<CalendarDate>();
		}
		File xmlDataFile = new File(baseDir, "Dates_" + currentYear + "_" + currentMonth.getValue() + ".xml");

		try {
			xmlDataFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		AppFileProcessor processor = new AppFileProcessor(xmlDataFile);
		return processor.readFromXMLFile();
	}
	
	public void writeTaskIntoFile(AppTask task) {
	if(task == null) {
			System.err.println("calendarDate null bei Methode writeIntoFile() in Klasse AppModel");
			return;
		}
		if(!baseDir.exists()) {
			baseDir.mkdir();
		}
		if(!baseDir.canRead() || !baseDir.canWrite()) {
			controller.handle(CalendarScene.NO_BASE_DIR);
		}
		try {
			xmlDataFile = new File(baseDir, "Tasks.xml");
			if(!xmlDataFile.exists()) {
				xmlDataFile.createNewFile();
			}
		}catch (Exception ex){
			ex.printStackTrace();
			Platform.exit();
		}
		try {
			AppFileProcessor processor = new AppFileProcessor(xmlDataFile);
			processor.appendTaskToXML(task);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//deleteTasksFromFile
	//rewriteTasksFromFile
	
	public void setBaseDir(File baseDir) {
		this.baseDir = baseDir;
	}
}
