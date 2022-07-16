package org.myoggradio.stb.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.myoggradio.stb.Factory;
import org.myoggradio.stb.Protokol;
import org.myoggradio.stb.Spieler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XMLSpielerLoader 
{
	public ArrayList<Spieler> load(File file)
	{
		ArrayList<Spieler> erg = new ArrayList<Spieler>();
		try
		{
			InputStream fin = new FileInputStream(file);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(fin));
			Node turnier = doc.getFirstChild();
			Element elemturnier = (Element) turnier;
			erg = getSpielerList(elemturnier);
		}
		catch (Exception e)
		{
			Protokol.write("XMLSpielerLoader:read:Exception:");
			Protokol.write(e.toString());
		}
		return erg; 
	}
	public Spieler getSpieler(Element element)
	{
		Spieler erg = Factory.getSpieler();
		String sid = element.getAttribute("id");
		String vorname = element.getAttribute("vorname");
		String name = element.getAttribute("name");
		String sdwz = element.getAttribute("dwz");
		int dwz = Integer.parseInt(sdwz);
		int id = Integer.parseInt(sid);
		erg.setId(id);
		erg.setVorname(vorname);
		erg.setName(name);
		erg.setDWZ(dwz);
		return erg;
	}
	public ArrayList<Spieler> getSpielerList(Element element)
	{
		ArrayList<Spieler> erg = new ArrayList<Spieler>();
		NodeList list = element.getChildNodes();
		for (int i=0;i<list.getLength();i++)
		{
			Node node = list.item(i);
			if (node instanceof Element)
			{
				Element spielerlistelement = (Element) node;
				String name = spielerlistelement.getTagName();
				if (name.equals("spieler"))
				{
					Spieler spieler = getSpieler(spielerlistelement);
					erg.add(spieler);
				}
			}
		}
		return erg;
	}
}
