package org.myoggradio.stb.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.myoggradio.stb.Factory;
import org.myoggradio.stb.Parameter;
import org.myoggradio.stb.Partie;
import org.myoggradio.stb.Protokol;
import org.myoggradio.stb.Runde;
import org.myoggradio.stb.Spieler;
import org.myoggradio.stb.Turnier;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XMLTurnierLoader 
{
	public Turnier load(File file)
	{
		Turnier erg = Factory.getTurnier();
		try
		{
			InputStream fin = new FileInputStream(file);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(fin));
			Node turnier = doc.getFirstChild();
			Element elemturnier = (Element) turnier;
			erg = getTurnier(elemturnier);
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
		String vorname = element.getAttribute("vorname");
		String name = element.getAttribute("name");
		String sdwz = element.getAttribute("dwz");
		int dwz = Integer.parseInt(sdwz);
		erg.setVorname(vorname);
		erg.setName(name);
		erg.setDWZ(dwz);
		return erg;
	}
	public Runde getRunde(Element element)
	{
		Runde erg = Factory.getRunde();
		ArrayList<Spieler> freilos = new ArrayList<Spieler>();
		ArrayList<Partie> partien = new ArrayList<Partie>();
		NodeList list = element.getChildNodes();
		for (int i=0;i<list.getLength();i++)
		{
			Node node = list.item(i);
			if (node instanceof Element)
			{
				Element rundeelement = (Element) node;
				String name = rundeelement.getTagName();
				if (name.equals("freilos"))
				{
					Spieler spieler = getSpieler(rundeelement);
					freilos.add(spieler);
				}
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
		for (int i=0;i<freilos.size();i++)
		{
			erg.addFreilos(freilos.get(i));
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
	public Turnier getTurnier(Element element)
	{
		Turnier erg = Factory.getTurnier();
		ArrayList<Spieler> spielerlist = new ArrayList<Spieler>();
		ArrayList<Runde> runden = new ArrayList<Runde>();
		String smaxrunden = element.getAttribute("maxrunden");
		int maxrunden = Integer.parseInt(smaxrunden);
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
				if (name.equals("runde"))
				{
					Runde runde = getRunde(turnierelement);
					runden.add(runde);
				}
			}
		}
		erg.setMaxRunden(maxrunden);
		erg.setSpieler(spielerlist);
		Parameter.turnier = erg;
		for (int i=0;i<runden.size();i++)
		{
			if (runden.get(i) == null) Protokol.write("XMLTurnierLoader:getTurnier:Null Runde:" + i);
			erg.setNextRunde(runden.get(i));
		}
		return erg;
	}
}
