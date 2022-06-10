package org.myoggradio.stbko.impl;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.myoggradio.stb.*;
import org.myoggradio.stbko.*;
public class SimpleKOTurnierManager implements KOTurnierManager
{
	@Override
	public KORunde starteNaechsteRunde(KOTurnier turnier) 
	{
		KORunde erg = KOFactory.getKORunde();
		KORunde runde = turnier.getAktiveRunde();
		int n = runde.getMaxPartien();
		int nh = n / 2;
		erg.setMaxPartien(nh);
		if (nh > 0)
		{
			for (int i=0;i<nh;i++)
			{
				Partie partie1 = runde.getPartie(2*i);
				Partie partie2 = runde.getPartie(2*i+1);
				Spieler spieler1 = getSieger(partie1);
				if (spieler1 == null)
				{
					erg = null;
					JOptionPane.showMessageDialog(null,"Es sind nicht alle Ergebnisse eingetragen","Fehler",JOptionPane.INFORMATION_MESSAGE);
					break;
				}
				Spieler spieler2 = getSieger(partie2);
				if (spieler2 == null)
				{
					erg = null;
					JOptionPane.showMessageDialog(null,"Es sind nicht alle Ergebnisse eingetragen","Fehler",JOptionPane.INFORMATION_MESSAGE);
					break;
				}
				Partie partie = Factory.getPartie();
				int anzahlWeissSpieler1 = getAnzahlWeiss(spieler1);
				int anzahlSchwarzSpieler1 = getAnzahlSchwarz(spieler1);
				int anzahlWeissSpieler2 = getAnzahlWeiss(spieler2);
				int anzahlSchwarzSpieler2 = getAnzahlSchwarz(spieler2);
				if (anzahlWeissSpieler1 > anzahlWeissSpieler2)
				{
					partie.setWeiss(spieler2);
					partie.setSchwarz(spieler1);
				}
				else if (anzahlWeissSpieler1 < anzahlWeissSpieler2)
				{
					partie.setWeiss(spieler1);
					partie.setSchwarz(spieler2);
				}
				else
				{
					if (anzahlSchwarzSpieler1 > anzahlSchwarzSpieler2)
					{
						partie.setWeiss(spieler1);
						partie.setSchwarz(spieler2);
					}
					else if (anzahlSchwarzSpieler1 < anzahlSchwarzSpieler2)
					{
						partie.setWeiss(spieler2);
						partie.setSchwarz(spieler1);
					}
					else
					{
						double dr = Math.random();
						if (dr > 0.5)
						{
							partie.setWeiss(spieler1);
							partie.setSchwarz(spieler2);
						}
						else
						{
							partie.setWeiss(spieler2);
							partie.setSchwarz(spieler1);
						}
					}
				}
				partie.setErgebnis(0);
				if (istFreilos(partie.getSchwarz())) partie.setErgebnis(2);
				if (istFreilos(partie.getWeiss())) partie.setErgebnis(3);
				erg.setPartie(partie,i);
			}
		}
		else
		{
			erg = null;
			JOptionPane.showMessageDialog(null,"Letzte Runde bereits erzeugt","Fehler",JOptionPane.INFORMATION_MESSAGE);
		}
		return erg;
	}
	private int getAnzahlWeiss(Spieler spieler)
	{
		int erg = 0;
		for (int i=0;i<KOParameter.turnier.getNummerAktiveRunde();i++)
		{
			KORunde runde = KOParameter.turnier.getRunde(i);
			for (int a=0;a<runde.getMaxPartien();a++)
			{
				Partie partie = runde.getPartie(a);
				if (spieler == partie.getWeiss())
				{
					if (!istFreilos(partie.getSchwarz())) erg++;
				}
			}
		}
		return erg;
	}
	private int getAnzahlSchwarz(Spieler spieler)
	{
		int erg = 0;
		for (int i=0;i<KOParameter.turnier.getNummerAktiveRunde();i++)
		{
			KORunde runde = KOParameter.turnier.getRunde(i);
			for (int a=0;a<runde.getMaxPartien();a++)
			{
				Partie partie = runde.getPartie(a);
				if (spieler == partie.getSchwarz())
				{
					if (!istFreilos(partie.getWeiss())) erg++;
				}
			}
		}
		return erg;
	}
	private Spieler getSieger(Partie partie)
	{
		Spieler erg = null;
		int ergebnis = partie.getErgebnis();
		if (ergebnis == 2) erg = partie.getWeiss();
		if (ergebnis == 3) erg = partie.getSchwarz();
		return erg;
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
					spieler[blockFirst] = gesetzte.get(0);
					gesetzte.remove(0);
				}
				if (last==null)
				{
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
