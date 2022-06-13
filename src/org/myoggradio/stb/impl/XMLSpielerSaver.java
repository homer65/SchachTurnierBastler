package org.myoggradio.stb.impl;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import org.myoggradio.stb.Protokol;
import org.myoggradio.stb.Spieler;
public class XMLSpielerSaver
{
	public void save(ArrayList<Spieler> spieler,File file) 
	{
		try
		{
			OutputStream os = new FileOutputStream(file);
			Writer writer = new OutputStreamWriter(os,"UTF-8");
			XMLOutputFactory outputFactory = XMLOutputFactory.newFactory();
			XMLStreamWriter out = outputFactory.createXMLStreamWriter(writer);
			out.writeStartDocument();
			out.writeStartElement("spielerlist");
			for (int i=0;i<spieler.size();i++)
			{
				Spieler s = spieler.get(i);
				out.writeStartElement("spieler");
				out.writeAttribute("vorname",s.getVorname());
				out.writeAttribute("name",s.getName());
				out.writeAttribute("dwz","" + s.getDWZ());
				out.writeEndElement();
			}
			out.writeEndElement();
			out.writeEndDocument();
			out.close();
		}
		catch (Exception e)
		{
			Protokol.write("XMLSpielerSaver:save:Exception:");
			Protokol.write(e.toString());
		}
	}
}
