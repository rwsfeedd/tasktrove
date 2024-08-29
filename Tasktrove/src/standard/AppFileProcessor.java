package standard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

public class AppFileProcessor {
	private final String TERMIN_LISTE = "terminListe";
	private final String TERMIN = "termin";
	private final String NAME = "name";
	private final String DATUM_VON = "datumVon";
	private final String DATUM_BIS = "datumBis";
	private final String UHRZEIT_VON = "uhrzeitVon";
	private final String UHRZEIT_BIS = "uhrzeitBis";

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
					if(reader.getLocalName().equals(NAME)) list.getLast().setName(reader.getElementText());
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
			LinkedList<CalendarDate> list = readFromXMLFile();
			FileOutputStream fos = new FileOutputStream(xmlDataFile);
			XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory();
			XMLStreamWriter writer = xmlOutputFactory.createXMLStreamWriter(fos, "utf-8");
			writer.writeStartDocument("utf-8", "1.0");
			writer.writeCharacters("\n");
			writer.writeStartElement(TERMIN_LISTE);
			
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
			FileOutputStream fos = new FileOutputStream(xmlDataFile);
			XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory();
			XMLStreamWriter writer = xmlOutputFactory.createXMLStreamWriter(fos, "utf-8");
			writer.writeStartDocument("utf-8", "1.0");
			writer.writeCharacters("\n");
			writer.writeStartElement(TERMIN_LISTE);
			

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
			writer.writeStartElement(NAME);
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

}
