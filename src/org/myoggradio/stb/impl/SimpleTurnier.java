package org.myoggradio.stb.impl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;

import org.myoggradio.stb.*;
public class SimpleTurnier implements Turnier,Serializable
{
	private static final long serialVersionUID = -6122828935930565600L;
	private ArrayList<Spieler> spieler = null;
	private Runde[] runden = null;
	private int aktiveRunde = 0;
	@Override
	public void setMaxRunden(int n) 
	{
		runden = new Runde[n];		
	}
	@Override
	public void setMaxRundenPlus()
	{
		Runde[] temp = new Runde[runden.length+1];
		for (int i=0;i<runden.length;i++)
		{
			temp[i] = runden[i];
		}
		runden = temp;
	}
	@Override
	public void setNextRunde(Runde runde) 
	{
		runden[aktiveRunde] = runde;
		if (aktiveRunde > 0)
		{
			TurnierManager manager = Factory.getTurnierManager();
			Parameter.auswertungen = manager.getAuswertung(aktiveRunde);
			Collections.sort(Parameter.auswertungen,new AuswertungComparator());
			RundenSortierer rs = Factory.getRundenSortierer();
			rs.sortierePartien(runde);
		}
		aktiveRunde++;
		TurnierAutoSaver tas = Factory.getTurnierAutoSaver();
		tas.save(this);
	}
	@Override
	public int getMaxrunden() 
	{
		return runden.length;
	}
	@Override
	public Runde getRunde(int nummer) 
	{
		return runden[nummer];
	}
	@Override
	public Runde getAktiveRunde() 
	{
		return runden[aktiveRunde-1];
	}
	@Override
	public int getNummerAktiveRunde() 
	{
		return (aktiveRunde-1);
	}
	@Override
	public void start() // baut die erste Runde 
	{
		Collections.sort(spieler,new DWZComparator());
		int n = spieler.size();
		int nh = n / 2;
		Runde runde0 = Factory.getRunde();
		runde0.setMaxPartien(nh);
		if (2*nh != n)
		{
			runde0.addFreilos(spieler.get(n-1));
		}
		for (int i=0;i<nh;i++)
		{
			Partie partie = Factory.getPartie();
			Spieler spieler1 = spieler.get(i);
			Spieler spieler2 = spieler.get(i + nh);
			double test = Math.random();
			if (test > 0.5)
			{
				partie.setWeiss(spieler1);
				partie.setSchwarz(spieler2);
			}
			else
			{
				partie.setWeiss(spieler2);
				partie.setSchwarz(spieler1);
			}
			runde0.setPartie(partie,i);
		}
		setNextRunde(runde0);
	}
	@Override
	public void setSpieler(ArrayList<Spieler> spieler) 
	{
		this.spieler = spieler;		
	}
	@Override
	public int getUngerade() 
	{
		int erg = 0;
		int n = spieler.size();
		int nh = n / 2;
		erg = n - (2 * nh);
		return erg;
	}
	@Override
	public ArrayList<Spieler> getSpieler() 
	{
		return spieler;
	}
	@Override
	public boolean storniereAktiveRunde()
	{
		boolean erg = false;
		if (aktiveRunde > 1)
		{
			Runde runde = runden[aktiveRunde-1];
			boolean ok = true;
			for (int i=0;i<runde.getMaxPartien();i++)
			{
				Partie partie = runde.getPartie(i);
				int ergebnis = partie.getErgebnis();
				if (ergebnis != 0)
				{
					ok = false;
				}
			}
			if (ok)
			{
				aktiveRunde--;
				erg = true;
			}
			else
			{
				JOptionPane.showMessageDialog(null,"Es darf nur eine Runde ohne Ergebnisse storniert werden","Fehler",JOptionPane.INFORMATION_MESSAGE);
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null,"Die erste Runde darf nicht storniert werden","Fehler",JOptionPane.INFORMATION_MESSAGE);
		}
		return erg;
	}
	@Override
	public void storniereSpieler(Spieler einspieler) 
	{
		for (int i=0;i<aktiveRunde;i++)
		{
			Runde runde = runden[i];
			runde.storniereSpieler(einspieler);
		}
		spieler.remove(einspieler);
	}
}
