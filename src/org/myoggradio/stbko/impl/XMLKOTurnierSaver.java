package org.myoggradio.stbko.impl;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import org.myoggradio.stb.Partie;
import org.myoggradio.stb.Protokol;
import org.myoggradio.stb.Spieler;
import org.myoggradio.stbko.KORunde;
import org.myoggradio.stbko.KOTurnier;
public class XMLKOTurnierSaver
{
	public void save(KOTurnier turnier,File file) 
	{
		try
		{
			OutputStream os = new FileOutputStream(file);
			Writer writer = new OutputStreamWriter(os,"UTF-8");
			XMLOutputFactory outputFactory = XMLOutputFactory.newFactory();
			XMLStreamWriter out = outputFactory.createXMLStreamWriter(writer);
			out.writeStartDocument();
			out.writeStartElement("koturnier");
			ArrayList<Spieler> spieler = turnier.getSpieler();
			for (int i=0;i<spieler.size();i++)
			{
				Spieler s = spieler.get(i);
				out.writeStartElement("spieler");
				out.writeAttribute("vorname",s.getVorname());
				out.writeAttribute("name",s.getName());
				out.writeAttribute("dwz","" + s.getDWZ());
				out.writeEndElement();
			}
			int n = turnier.getNummerAktiveRunde();
			for (int i=0;i<n;i++)
			{
				KORunde runde = turnier.getRunde(i);
				out.writeStartElement("korunde");
				n = runde.getMaxPartien();
				for (int j=0;j<n;j++)
				{
					Partie partie = runde.getPartie(j);
					out.writeStartElement("partie");
					out.writeAttribute("ergebnis","" + partie.getErgebnis());
					Spieler weiss = partie.getWeiss();
					out.writeStartElement("weiss");
					out.writeAttribute("vorname",weiss.getVorname());
					out.writeAttribute("name",weiss.getName());
					out.writeAttribute("dwz","" + weiss.getDWZ());
					out.writeEndElement();
					Spieler schwarz = partie.getSchwarz();
					out.writeStartElement("schwarz");
					out.writeAttribute("vorname",schwarz.getVorname());
					out.writeAttribute("name",schwarz.getName());
					out.writeAttribute("dwz","" + schwarz.getDWZ());
					out.writeEndElement();
					out.writeEndElement();
				}
				out.writeEndElement();
			}
			out.writeEndElement();
			out.writeEndDocument();
			out.close();
		}
		catch (Exception e)
		{
			Protokol.write("XMLKOTurnierSaver:save:Exception:");
			Protokol.write(e.toString());
		}
	}
}
