package org.myoggradio.stbjgj.impl;
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
import org.myoggradio.stb.Spieler;
import org.myoggradio.stbjgj.JGJRunde;
import org.myoggradio.stbjgj.JGJTurnier;
public class XMLJGJTurnierSaver
{
	public void save(JGJTurnier turnier,File file) 
	{
		try
		{
			OutputStream os = new FileOutputStream(file);
			Writer writer = new OutputStreamWriter(os,"UTF-8");
			XMLOutputFactory outputFactory = XMLOutputFactory.newFactory();
			XMLStreamWriter out = outputFactory.createXMLStreamWriter(writer);
			out.writeStartDocument();
			out.writeStartElement("jgjturnier");
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
				JGJRunde runde = turnier.getRunde(i);
				if (runde != null)
				{
					out.writeStartElement("jgjrunde");
					Spieler[] rspieler = runde.getSpieler();
					int m = rspieler.length;
					for (int j=0;j<m;j++)
					{
						Spieler rs = rspieler[j];
						out.writeStartElement("spieler");
						out.writeAttribute("id",rs.getId() + "");
						out.writeAttribute("vorname",rs.getVorname());
						out.writeAttribute("name",rs.getName());
						out.writeAttribute("dwz","" + rs.getDWZ());
						out.writeEndElement();
					}
					m = runde.getMaxPartien();
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
			Protokol.write("XMLKOTurnierSaver:save:Exception:");
			Protokol.write(e.toString());
		}
	}
}
