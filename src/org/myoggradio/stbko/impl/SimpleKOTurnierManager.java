package org.myoggradio.stbko.impl;
import java.util.ArrayList;
import org.myoggradio.stb.*;
import org.myoggradio.stbko.*;
public class SimpleKOTurnierManager implements KOTurnierManager
{
	@Override
	public KORunde starteNaechsteRunde(KOTurnier turnier) 
	{
		return null;
	}
	@Override
	public KORunde starteErsteRunde(KOTurnier turnier) 
	{
		ArrayList<Spieler> gesetzte = turnier.getSpieler();
		int n = gesetzte.size();
		int zweiHochX = 2;
		int x = 1;
		while (zweiHochX < n) // Berechne Turniergroesse
		{
			x++;
			zweiHochX = zweiHochX * 2;
		}
		Protokol.write("SimpleKOTurnierManager:zweiHochX:"+ zweiHochX);
		for (int i=n;i<zweiHochX;i++) // Fuelle gesetzte mit FREILOS auf
		{
			Spieler freilos = Factory.getSpieler();
			freilos.setName("FREILOS");
			freilos.setVorname("");
			freilos.setDWZ(0);
			gesetzte.add(freilos);
		}
		Protokol.write("SimpleKOTurnierManager:Anzahl gesetzte:" + gesetzte.size());
		Spieler[] spieler = new Spieler[zweiHochX];
		for (int i=0;i<zweiHochX;i++) // Fuelle Feld erstmal mit null auf
		{
			spieler[i] = null;
		}
		spieler[0] = gesetzte.get(0);
		gesetzte.remove(0);
		spieler[zweiHochX-1] = gesetzte.get(0);
		gesetzte.remove(0);
		int blockGroesse = zweiHochX;
		int blockAnzahl = 1;
		for (int i=0;i<x;i++) // Fuelle Spielfeld der Reihe nach auf
		{
			blockGroesse = blockGroesse / 2;
			blockAnzahl = blockAnzahl * 2;
			for (int j=0;j<blockAnzahl;j++)
			{
				int blockFirst = j * blockGroesse;
				int blockLast = ((j+1) * blockGroesse) - 1;
				Spieler first = spieler[blockFirst];
				Spieler last = spieler[blockLast];
				if (first==null)
				{
					Protokol.write("SimpleKOTurnierManager:blockFirst:" + blockFirst);
					spieler[blockFirst] = gesetzte.get(0);
					gesetzte.remove(0);
				}
				if (last==null)
				{
					Protokol.write("SimpleKOTurnierManager:blockLast:" + blockLast);
					spieler[blockLast] = gesetzte.get(0);
					gesetzte.remove(0);
				}
			}
		}
		KORunde runde = KOFactory.getKORunde();
		runde.setMaxPartien(zweiHochX/2);
		for (int i=0;i<zweiHochX/2;i++)
		{
			Partie partie = Factory.getPartie();
			double dr = Math.random();
			if (dr > 0.5)
			{
				partie.setWeiss(spieler[2*i]);
				partie.setSchwarz(spieler[2*i+1]);
			}
			else
			{
				partie.setSchwarz(spieler[2*i]);
				partie.setWeiss(spieler[2*i+1]);
			}
			partie.setErgebnis(0);
			if (istFreilos(partie.getSchwarz())) partie.setErgebnis(2);
			if (istFreilos(partie.getWeiss())) partie.setErgebnis(3);
			runde.setPartie(partie,i);
		}
		return runde;
	}
	private boolean istFreilos(Spieler spieler)
	{
		boolean erg = false;
		if (spieler.getName().equals("FREILOS")) erg = true;
		return erg;
	}
}
