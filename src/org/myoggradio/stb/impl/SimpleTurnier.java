package org.myoggradio.stb.impl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
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
	public void setNextRunde(Runde runde) 
	{
		runden[aktiveRunde] = runde;
		aktiveRunde++;
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
			runde0.setFreilos(spieler.get(n-1));
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
}
