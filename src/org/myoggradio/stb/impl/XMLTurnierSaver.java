package org.myoggradio.stb.impl;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import org.myoggradio.stb.Parameter;
import org.myoggradio.stb.Partie;
import org.myoggradio.stb.Protokol;
import org.myoggradio.stb.Runde;
import org.myoggradio.stb.Spieler;
import org.myoggradio.stb.Turnier;
public class XMLTurnierSaver
{
	public void save(Turnier turnier,File file) 
	{
		try
		{
			OutputStream os = new FileOutputStream(file);
			Writer writer = new OutputStreamWriter(os,"UTF-8");
			XMLOutputFactory outputFactory = XMLOutputFactory.newFactory();
			XMLStreamWriter out = outputFactory.createXMLStreamWriter(writer);
			out.writeStartDocument();
			out.writeStartElement("turnier");
			out.writeAttribute("aktiverunde","" + turnier.getNummerAktiveRunde());
			out.writeAttribute("maxrunden","" + turnier.getMaxrunden());
			out.writeAttribute("version",Parameter.version);
			ArrayList<Spieler> spieler = turnier.getSpieler();
			for (int i=0;i<spieler.size();i++)
			{
				Spieler s = spieler.get(i);
				out.writeStartElement("spieler");
				out.writeAttribute("id",s.getId() + "");
				out.writeAttribute("vorname",s.getVorname());
				out.writeAttribute("name",s.getName());
				out.writeAttribute("dwz","" + s.getDWZ());
				out.writeEndElement();
			}
			int n = turnier.getMaxrunden();
			for (int i=0;i<n;i++)
			{
				Protokol.write("XMLTurnierSaver:save:Runde:" + i);
				Runde runde = turnier.getRunde(i);
				if (runde != null)
				{
					out.writeStartElement("runde");
					ArrayList<Spieler> freilos = runde.getFreilos();
					for (int j=0;j<freilos.size();j++)
					{
						Spieler s = freilos.get(j);
						out.writeStartElement("freilos");
						out.writeAttribute("id",s.getId() + "");
						out.writeAttribute("vorname",s.getVorname());
						out.writeAttribute("name",s.getName());
						out.writeAttribute("dwz","" + s.getDWZ());
						out.writeEndElement();
					}
					int m = runde.getMaxPartien();
					for (int j=0;j<m;j++)
					{
						Partie partie = runde.getPartie(j);
						out.writeStartElement("partie");
						out.writeAttribute("ergebnis","" + partie.getErgebnis());
						Spieler weiss = partie.getWeiss();
						out.writeStartElement("weiss");
						out.writeAttribute("id",weiss.getId() + "");
						out.writeAttribute("vorname",weiss.getVorname());
						out.writeAttribute("name",weiss.getName());
						out.writeAttribute("dwz","" + weiss.getDWZ());
						out.writeEndElement();
						Spieler schwarz = partie.getSchwarz();
						out.writeStartElement("schwarz");
						out.writeAttribute("id",schwarz.getId() + "");
						out.writeAttribute("vorname",schwarz.getVorname());
						out.writeAttribute("name",schwarz.getName());
						out.writeAttribute("dwz","" + schwarz.getDWZ());
						out.writeEndElement();
						out.writeEndElement();
					}
					out.writeEndElement();
				}
			}
			out.writeEndElement();
			out.writeEndDocument();
			out.close();
		}
		catch (Exception e)
		{
			Protokol.write("XMLTurnierSaver:save:Exception:");
			Protokol.write(e.toString());
		}
	}
}
