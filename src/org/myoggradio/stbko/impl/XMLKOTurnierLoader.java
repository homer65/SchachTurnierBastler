package org.myoggradio.stbko.impl;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.myoggradio.stb.Factory;
import org.myoggradio.stb.Partie;
import org.myoggradio.stb.Protokol;
import org.myoggradio.stb.Spieler;
import org.myoggradio.stb.SpielerManager;
import org.myoggradio.stbko.KOFactory;
import org.myoggradio.stbko.KORunde;
import org.myoggradio.stbko.KOTurnier;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
public class XMLKOTurnierLoader 
{
	private int maxid = 0;
	private ArrayList<Spieler> spielerlist = new ArrayList<Spieler>();
	public KOTurnier load(File file)
	{
		KOTurnier erg = KOFactory.getKOTurnier();
		try
		{
			InputStream fin = new FileInputStream(file);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(fin));
			Node turnier = doc.getFirstChild();
			Element elemturnier = (Element) turnier;
			erg = getKOTurnier(elemturnier);
		}
		catch (Exception e)
		{
			Protokol.write("XML:read:Exception:");
			Protokol.write(e.toString());
		}
		return erg; 
	}
	public Spieler getSpieler(Element element)
	{
		Spieler erg = Factory.getSpieler();
		int id = maxid++;
		String vorname = element.getAttribute("vorname");
		String name = element.getAttribute("name");
		String sdwz = element.getAttribute("dwz");
		int dwz = Integer.parseInt(sdwz);
		erg.setVorname(vorname);
		erg.setName(name);
		erg.setDWZ(dwz);
		erg.setId(id);
		return erg;
	}
	public Spieler getSpielerInRunde(Element element)
	{
		SpielerManager spielerManager = Factory.getSpielerManager();
		Spieler erg = Factory.getSpieler();
		String vorname = element.getAttribute("vorname");
		String name = element.getAttribute("name");
		String sdwz = element.getAttribute("dwz");
		int dwz = Integer.parseInt(sdwz);
		erg.setVorname(vorname);
		erg.setName(name);
		erg.setDWZ(dwz);
		erg.setId(spielerManager.getId(erg,spielerlist));
		return erg;
	}
	public KORunde getKORunde(Element element)
	{
		KORunde erg = KOFactory.getKORunde();
		ArrayList<Partie> partien = new ArrayList<Partie>();
		NodeList list = element.getChildNodes();
		for (int i=0;i<list.getLength();i++)
		{
			Node node = list.item(i);
			if (node instanceof Element)
			{
				Element rundeelement = (Element) node;
				String name = rundeelement.getTagName();
				if (name.equals("partie"))
				{
					Partie partie = getPartie(rundeelement);
					partien.add(partie);
				}
			}
		}
		erg.setMaxPartien(partien.size());
		for (int i=0;i<partien.size();i++)
		{
			erg.setPartie(partien.get(i),i);
		}
		return erg;
	}
	public Partie getPartie(Element element)
	{
		Partie partie = Factory.getPartie();
		String sergebnis = element.getAttribute("ergebnis");
		int ergebnis = Integer.parseInt(sergebnis);
		Spieler weiss = Factory.getSpieler();
		Spieler schwarz = Factory.getSpieler();
		NodeList list = element.getChildNodes();
		for (int i=0;i<list.getLength();i++)
		{
			Node node = list.item(i);
			if (node instanceof Element)
			{
				Element partieelement = (Element) node;
				String name = partieelement.getTagName();
				if (name.equals("weiss"))
				{
					weiss = getSpieler(partieelement);
				}
				if (name.equals("schwarz"))
				{
					schwarz = getSpieler(partieelement);
				}
			}
		}
		partie.setErgebnis(ergebnis);
		partie.setWeiss(weiss);
		partie.setSchwarz(schwarz);
		return partie;
	}
	public KOTurnier getKOTurnier(Element element)
	{
		KOTurnier erg = KOFactory.getKOTurnier();
		ArrayList<KORunde> runden = new ArrayList<KORunde>();
		NodeList list = element.getChildNodes();
		for (int i=0;i<list.getLength();i++)
		{
			Node node = list.item(i);
			if (node instanceof Element)
			{
				Element turnierelement = (Element) node;
				String name = turnierelement.getTagName();
				if (name.equals("spieler"))
				{
					Spieler spieler = getSpieler(turnierelement);
					spielerlist.add(spieler);
				}
				if (name.equals("korunde"))
				{
					KORunde runde = getKORunde(turnierelement);
					runden.add(runde);
				}
			}
		}
		erg.setSpieler(spielerlist);
		for (int i=0;i<runden.size();i++)
		{
			if (runden.get(i) == null) Protokol.write("XMLKOTurnierLoader:getTurnier:Null Runde:" + i);
			erg.setNextRunde(runden.get(i));
		}
		return erg;
	}
}
