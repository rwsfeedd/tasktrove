package standard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedList;
import java.util.StringTokenizer;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

public class AppFileProcessor {
	private final String TERMIN_LISTE = "terminListe";
	private final String TERMIN = "termin";
	private final String TERMIN_NAME = "name";
	private final String DATUM_VON = "datumVon";
	private final String DATUM_BIS = "datumBis";
	private final String UHRZEIT_VON = "uhrzeitVon";
	private final String UHRZEIT_BIS = "uhrzeitBis";

	private final String AUFGABEN_LISTE = "aufgabenListe";
	private final String AUFGABE = "aufgabe";
	private final String AUFGABEN_NAME = "name";
	private final String AUFGABEN_SCHWIERIGKEIT = "schwierigkeit";
	private final String AUFGABEN_TYP = "typ";
	private final String AUFGABE_ERLEDIGT = "erledigt";
	
	private final String PUNKTE = "punkte";
	
	
	private File xmlDataFile;
	
	public AppFileProcessor(File xmlDataFile) {
		this.xmlDataFile = xmlDataFile;
	}
	
	

	public LinkedList<CalendarDate> readFromXMLFile() {
		LinkedList<CalendarDate> list = new LinkedList<CalendarDate>();
		try {
			FileInputStream fis = new FileInputStream(xmlDataFile);
			XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
			XMLStreamReader reader = xmlInputFactory.createXMLStreamReader(fis, "utf-8");

			if(xmlDataFile.length()<=0) return null;
			while(reader.hasNext()){
				reader.next();
				if(reader.getEventType() == XMLStreamConstants.START_ELEMENT) {
					if(reader.getLocalName().equals(TERMIN_LISTE)) {
						continue;
					}
					if(reader.getLocalName().equals(TERMIN)) list.add(new CalendarDate());
					if(reader.getLocalName().equals(TERMIN_NAME)) list.getLast().setName(reader.getElementText());
					if(reader.getLocalName().equals(DATUM_VON)) {
						String[] stringStartDate = reader.getElementText().split("-");
						list.getLast().setStartDate(LocalDate.of(Integer.valueOf(stringStartDate[0]), 
								Month.of(Integer.valueOf(stringStartDate[1])) ,
								Integer.valueOf(stringStartDate[2])));
					}
					if(reader.getLocalName().equals(DATUM_BIS)) {
						String[] stringEndDate = reader.getElementText().split("-");
						list.getLast().setEndDate(LocalDate.of(Integer.valueOf(stringEndDate[0]), 
								Month.of(Integer.valueOf(stringEndDate[1])) ,
								Integer.valueOf(stringEndDate[2])));
					}
					if(reader.getLocalName().equals(UHRZEIT_VON)) {
						StringTokenizer tokenizer = new StringTokenizer(reader.getElementText(), ":");
						list.getLast().setStartHour(Integer.valueOf(tokenizer.nextToken()));
						list.getLast().setStartMinute(Integer.valueOf(tokenizer.nextToken()));
					}
					if(reader.getLocalName().equals(UHRZEIT_BIS)) {
						StringTokenizer tokenizer = new StringTokenizer(reader.getElementText(), ":");
						list.getLast().setEndHour(Integer.valueOf(tokenizer.nextToken()));
						list.getLast().setEndMinute(Integer.valueOf(tokenizer.nextToken()));
					}
				}
			}
			reader.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return new LinkedList<CalendarDate>(list);
		
	}

	public void appendToXMLFile(CalendarDate calendarDate) {
		try{
			int punkte = readPoints();
			LinkedList<CalendarDate> list = readFromXMLFile();
			FileOutputStream fos = new FileOutputStream(xmlDataFile);
			XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory();
			XMLStreamWriter writer = xmlOutputFactory.createXMLStreamWriter(fos, "utf-8");
			writer.writeStartDocument("utf-8", "1.0");
			writer.writeCharacters("\n");
			writer.writeStartElement(TERMIN_LISTE);
			
			writePoints(punkte, writer);

			if(list != null && !list.isEmpty()) {
				for(int i = 0; i < list.size(); i++) {
					writeDate(list.get(i), writer);
				}
			}
			if(calendarDate != null) {
				writeDate(calendarDate, writer);
			}
			writer.writeEndDocument();
			writer.flush();
			writer.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void rewriteXMLFile(LinkedList<CalendarDate> listDates) {
		try {
			int points = readPoints();
			FileOutputStream fos = new FileOutputStream(xmlDataFile);
			XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory();
			XMLStreamWriter writer = xmlOutputFactory.createXMLStreamWriter(fos, "utf-8");
			writer.writeStartDocument("utf-8", "1.0");
			writer.writeCharacters("\n");
			writer.writeStartElement(TERMIN_LISTE);

			writePoints(points, writer);

			if(!(listDates == null) && !(listDates.size() < 1)) {
				for(int i = 0; i < listDates.size(); i++) {
					writeDate(listDates.get(i), writer);
				}//for
			}

			writer.writeEndDocument();
			writer.flush();
			writer.close();
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void writeDate(CalendarDate calendarDate, XMLStreamWriter writer) {
		try {
			writer.writeCharacters("\n\t");
			writer.writeStartElement(TERMIN);

			writer.writeCharacters("\n\t\t");
			writer.writeStartElement(TERMIN_NAME);
			writer.writeCharacters(calendarDate.getName());
			writer.writeEndElement();

			writer.writeCharacters("\n\t\t");
			writer.writeStartElement(DATUM_VON);
			writer.writeCharacters(calendarDate.getStartDate().getYear()+"-"+calendarDate.getStartDate().getMonthValue()
					+"-"+calendarDate.getStartDate().getDayOfMonth());
			writer.writeEndElement();

			writer.writeCharacters("\n\t\t");
			writer.writeStartElement(DATUM_BIS);
			writer.writeCharacters(calendarDate.getEndDate().getYear()+"-"+calendarDate.getEndDate().getMonthValue()
					+"-"+calendarDate.getEndDate().getDayOfMonth());
			writer.writeEndElement();

			writer.writeCharacters("\n\t\t");
			writer.writeStartElement(UHRZEIT_VON);
			writer.writeCharacters(calendarDate.getStartHour()+":"+calendarDate.getStartMinute());
			writer.writeEndElement();

			writer.writeCharacters("\n\t\t");
			writer.writeStartElement(UHRZEIT_BIS);
			writer.writeCharacters(calendarDate.getEndHour()+":"+calendarDate.getEndMinute());
			writer.writeEndElement();

			writer.writeCharacters("\n\t");
			writer.writeEndElement();
			writer.writeCharacters("\n");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public LinkedList<AppTask> readTasks() {
		LinkedList<AppTask> list = new LinkedList<AppTask>();
		try {
			FileInputStream fis = new FileInputStream(xmlDataFile);
			XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
			XMLStreamReader reader = xmlInputFactory.createXMLStreamReader(fis, "utf-8");

			if(xmlDataFile.length()<=0) return null;
			while(reader.hasNext()){
				reader.next();
				if(reader.getEventType() == XMLStreamConstants.START_ELEMENT) {
					if(reader.getLocalName().equals(AUFGABEN_LISTE)) {
						continue;
					}
					if(reader.getLocalName().equals(AUFGABE)) list.add(new AppTask());
					if(reader.getLocalName().equals(AUFGABEN_NAME)) list.getLast().setName(reader.getElementText());
					if(reader.getLocalName().equals(AUFGABEN_SCHWIERIGKEIT)) list.getLast().setDifficulty(AppTask.Difficulty.valueOf(reader.getElementText()));
					if(reader.getLocalName().equals(AUFGABEN_TYP)) list.getLast().setType(AppTask.Type.valueOf(reader.getElementText()));
					if(reader.getLocalName().equals(AUFGABE_ERLEDIGT)) list.getLast().setDone(Boolean.parseBoolean(reader.getElementText()));
				}
			}
			reader.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
	
	public void appendTaskToXML(AppTask task) {
		try{
			LinkedList<AppTask> list = readTasks();
			FileOutputStream fos = new FileOutputStream(xmlDataFile);
			XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory();
			XMLStreamWriter writer = xmlOutputFactory.createXMLStreamWriter(fos, "utf-8");
			writer.writeStartDocument("utf-8", "1.0");
			writer.writeCharacters("\n");
			writer.writeStartElement(AUFGABEN_LISTE);
			
			if(list != null && !list.isEmpty()) {
				for(int i = 0; i < list.size(); i++) {
					writeTask(list.get(i), writer);
				}
			}
			if(task != null) {
				writeTask(task, writer);
			}
			writer.writeEndDocument();
			writer.flush();
			writer.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void rewriteTasksIntoXML(LinkedList<AppTask> listTasks) {
		try {
			FileOutputStream fos = new FileOutputStream(xmlDataFile);
			XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory();
			XMLStreamWriter writer = xmlOutputFactory.createXMLStreamWriter(fos, "utf-8");
			writer.writeStartDocument("utf-8", "1.0");
			writer.writeCharacters("\n");
			writer.writeStartElement(AUFGABEN_LISTE);
			
			if(!(listTasks == null) && !(listTasks.size() < 1)) {
				for(int i = 0; i < listTasks.size(); i++) {
					writeTask(listTasks.get(i), writer);
				}//for
			}
			writer.writeEndDocument();
			writer.flush();
			writer.close();
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void writeTask(AppTask task, XMLStreamWriter writer) {
		try {
			writer.writeCharacters("\n\t");
			writer.writeStartElement(AUFGABE);

			writer.writeCharacters("\n\t\t");
			writer.writeStartElement(AUFGABEN_NAME);
			writer.writeCharacters(task.getName());
			writer.writeEndElement();

			writer.writeCharacters("\n\t\t");
			writer.writeStartElement(AUFGABEN_SCHWIERIGKEIT);
			writer.writeCharacters(task.getDifficulty().toString());
			writer.writeEndElement();

			writer.writeCharacters("\n\t\t");
			writer.writeStartElement(AUFGABEN_TYP);
			writer.writeCharacters(task.getType().toString());
			writer.writeEndElement();

			writer.writeCharacters("\n\t\t");
			writer.writeStartElement(AUFGABE_ERLEDIGT);
			writer.writeCharacters(Boolean.toString(task.isDone()));
			writer.writeEndElement();

			writer.writeCharacters("\n\t");
			writer.writeEndElement();
			writer.writeCharacters("\n");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addPoints(int addedPoints) {
		try {
			int points = readPoints() + addedPoints;
			LinkedList<CalendarDate> listDates = readFromXMLFile();
			FileOutputStream fos = new FileOutputStream(xmlDataFile);
			XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory();
			XMLStreamWriter writer = xmlOutputFactory.createXMLStreamWriter(fos, "utf-8");
			writer.writeStartDocument("utf-8", "1.0");
			writer.writeCharacters("\n");
			writer.writeStartElement(TERMIN_LISTE);

			writePoints(points, writer);

			if(!(listDates == null) && !(listDates.size() < 1)) {
				for(int i = 0; i < listDates.size(); i++) {
					writeDate(listDates.get(i), writer);
				}//for
			}

			writer.writeEndDocument();
			writer.flush();
			writer.close();
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void writePoints(int i, XMLStreamWriter writer) {
		try {
			writer.writeCharacters("\n\t");
			writer.writeStartElement(PUNKTE);
			writer.writeCharacters(Integer.toString(i));
			writer.writeEndElement();
			writer.writeCharacters("\n");
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	public int readPoints() {
		int ret = 0;
		try {
			FileInputStream fis = new FileInputStream(xmlDataFile);
			XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
			XMLStreamReader reader = xmlInputFactory.createXMLStreamReader(fis, "utf-8");

			if(xmlDataFile.length()<=0) return 0;
			while(reader.hasNext()){
				reader.next();
				if(reader.getEventType() == XMLStreamConstants.START_ELEMENT) {
					if(reader.getLocalName().equals(PUNKTE)) {
						ret = Integer.parseInt(reader.getElementText());
						break;
					}
				}
			}
			reader.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return ret;
	}
}
