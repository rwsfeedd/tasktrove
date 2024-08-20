package standard;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.xml.stream.Location;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

public class AppXMLWriter {
	FileOutputStream fos;
	FileInputStream fis;
	
	public AppXMLWriter(FileOutputStream fos, FileInputStream fis) {
		this.fos = fos;
		this.fis = fis;
	}
	
	public void createNewXMLFile() {
		try {
			XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory();
			XMLStreamWriter writer = xmlOutputFactory.createXMLStreamWriter(fos, "utf-8");
			writer.writeStartDocument("utf-8", "1.0");
			writer.writeCharacters("\n");
			writer.writeStartElement("terminListe");
			writer.writeEndDocument();
			//xmlStreamWriter.writeDTD("<!DOCTYPE termin SYSTEM \""+dtdDataFile+"\">");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void writeDate() {
		try {
			XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
			XMLStreamReader reader = xmlInputFactory.createXMLStreamReader(fis, "utf-8");
			int documentEnd = 0;
			while(reader.hasNext()) {
				reader.next();
				if(reader.getEventType() == XMLStreamConstants.END_DOCUMENT) documentEnd = reader.getLocation().getLineNumber();
			}
			XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory();
			XMLStreamWriter xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(fos, "utf-8");
			//cursor noch auf enddocument setzen
			mlStreamWriter.writeStartElement("termin");
			xmlStreamWriter.writeCharacters("\n\t");
			xmlStreamWriter.writeStartElement("name");
			xmlStreamWriter.writeCharacters("wichtige Informationen");
			xmlStreamWriter.writeEndElement();
			xmlStreamWriter.writeCharacters("\n\t");
			xmlStreamWriter.writeStartElement("datum");
			xmlStreamWriter.writeCharacters("\n\t\t");
			xmlStreamWriter.writeStartElement("datumVon");
			xmlStreamWriter.writeEndElement();
			xmlStreamWriter.writeCharacters("\n\t\t");
			xmlStreamWriter.writeStartElement("datumBis");
			xmlStreamWriter.writeEndElement();
			xmlStreamWriter.writeCharacters("\n\t");
			xmlStreamWriter.writeEndElement();
			xmlStreamWriter.writeCharacters("\n\t");
			xmlStreamWriter.writeStartElement("uhrzeit");
			xmlStreamWriter.writeCharacters("\n\t\t");
			xmlStreamWriter.writeStartElement("uhrzeitVon");
			xmlStreamWriter.writeEndElement();
			xmlStreamWriter.writeCharacters("\n\t\t");
			xmlStreamWriter.writeStartElement("uhrzeitBis");
			xmlStreamWriter.writeEndElement();
			xmlStreamWriter.writeCharacters("\n\t");
			xmlStreamWriter.writeEndElement();
			xmlStreamWriter.writeCharacters("\n");
			xmlStreamWriter.writeEndDocument();
			xmlStreamWriter.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
