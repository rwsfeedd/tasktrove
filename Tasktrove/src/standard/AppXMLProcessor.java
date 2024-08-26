package standard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.StringTokenizer;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

public class AppXMLProcessor {
	private final String TERMIN_LISTE = "terminListe";
	private final String TERMIN = "termin";
	private final String NAME = "name";
	private final String DATUM_VON = "datumVon";
	private final String DATUM_BIS = "datumBis";
	private final String UHRZEIT_VON = "uhrzeitVon";
	private final String UHRZEIT_BIS = "uhrzeitBis";

	private File xmlDataFile;
	
	public AppXMLProcessor(File xmlDataFile) {
		this.xmlDataFile = xmlDataFile;
	}
	
	public LinkedList<CalendarDate> readFromXMLFile() {
		LinkedList<CalendarDate> list = new LinkedList<CalendarDate>();
		try {
			FileInputStream fis = new FileInputStream(xmlDataFile);
			XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
			XMLStreamReader reader = xmlInputFactory.createXMLStreamReader(fis, "utf-8");

			if(xmlDataFile.length()<0) return null;
			while(reader.hasNext()){
				reader.next();
				if(reader.getEventType() == XMLStreamConstants.START_ELEMENT) {
					if(reader.getLocalName().equals(TERMIN_LISTE)) {
						continue;
					}
					if(reader.getLocalName().equals(TERMIN)) list.add(new CalendarDate());
					if(reader.getLocalName().equals(NAME)) list.getLast().setName(reader.getElementText());
					if(reader.getLocalName().equals(DATUM_VON)) {
						StringTokenizer tokenizer = new StringTokenizer(reader.getElementText(), "-");
						list.getLast().setStartYear(Integer.valueOf(tokenizer.nextToken()));
						list.getLast().setStartMonth(Integer.valueOf(tokenizer.nextToken()));
						list.getLast().setStartDay(Integer.valueOf(tokenizer.nextToken()));
					}
					if(reader.getLocalName().equals(DATUM_BIS)) {
						StringTokenizer tokenizer = new StringTokenizer(reader.getElementText(), "-");
						list.getLast().setEndYear(Integer.valueOf(tokenizer.nextToken()));
						list.getLast().setEndMonth(Integer.valueOf(tokenizer.nextToken()));
						list.getLast().setEndDay(Integer.valueOf(tokenizer.nextToken()));
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
		list = sortCalendarDateList(list);
		return list;
		
	}
	int l = 0;
	public LinkedList<CalendarDate> sortCalendarDateList(LinkedList<CalendarDate> liste) {
		//abbruchbedingung des iterativen Verfahrens
		int id = l;
		l++;
		if(liste.size() <= 1) return liste;

		LinkedList<CalendarDate> leftList = new LinkedList<CalendarDate>();
		LinkedList<CalendarDate> rightList = new LinkedList<CalendarDate>();

		//zuerst Liste in 2 Listen unterteilen
		for(int i = 0; i < liste.size(); i++) {
			if(i<(int)liste.size()/2) {
				leftList.add(liste.get(i));
			} else {
				rightList.add(liste.get(i));
			}
		}

		//sort für linke Liste
		leftList = sortCalendarDateList(leftList);
		//sort für rechte Liste
		rightList = sortCalendarDateList(rightList);
		//return der neuen gemergten Liste
		LinkedList<CalendarDate> newList = new LinkedList<CalendarDate>();
		while(leftList.size()>0 && rightList.size()>0) {
			if(leftList.getLast().getStartDay() <= rightList.getLast().getStartDay()) {
				newList.add(leftList.removeLast());
			}else {
				newList.add(rightList.removeLast());
			}
		}
		//letzte Elemente aussortieren
		while(leftList.size()>0) {
			newList.add(leftList.removeLast());
		}
		while(rightList.size()>0) {
			newList.add(rightList.removeLast());
		}
		if(id <= 1) {
			for(int i = 0; i < newList.size(); i++) {
				System.out.println(newList.get(i).getStartDay());
			}
		}
		return newList;
	}
	
	public void writeIntoXMLFile(CalendarDate calendarDate) {
		try{
			LinkedList<CalendarDate> list = readFromXMLFile();
			FileOutputStream fos = new FileOutputStream(xmlDataFile);
			XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory();
			XMLStreamWriter writer = xmlOutputFactory.createXMLStreamWriter(fos, "utf-8");
			writer.writeStartDocument("utf-8", "1.0");
			writer.writeCharacters("\n");
			writer.writeStartElement(TERMIN_LISTE);
			for(int i = 0; i < list.size(); i++) {
				writeDate(list.get(i), writer);
			}
			writeDate(calendarDate, writer);
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
			writer.writeStartElement(NAME);
			writer.writeCharacters(calendarDate.getName());
			writer.writeEndElement();

			writer.writeCharacters("\n\t\t");
			writer.writeStartElement(DATUM_VON);
			writer.writeCharacters(calendarDate.getStartYear()+"-"+calendarDate.getStartMonth()+"-"+calendarDate.getStartDay());
			writer.writeEndElement();

			writer.writeCharacters("\n\t\t");
			writer.writeStartElement(DATUM_BIS);
			writer.writeCharacters(calendarDate.getEndYear()+"-"+calendarDate.getEndMonth()+"-"+calendarDate.getEndDay());
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
}
