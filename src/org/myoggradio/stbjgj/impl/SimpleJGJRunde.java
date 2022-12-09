package org.myoggradio.stbjgj.impl;
import java.io.Serializable;

import org.myoggradio.stb.ErgebnisDarsteller;
import org.myoggradio.stb.Partie;
import org.myoggradio.stb.Spieler;
import org.myoggradio.stbjgj.JGJRunde;
public class SimpleJGJRunde implements JGJRunde,Serializable
{
	private static final long serialVersionUID = 1L;
	private Spieler[] spieler = null;
	private Partie[] partien = null;
	@Override
	public void setMaxPartien(int n) 
	{
		partien = new Partie[n];		
	}
	@Override
	public void setPartie(Partie partie, int nummer) 
	{
		partien[nummer] = partie;		
	}
	@Override
	public int getMaxPartien() 
	{
		return partien.length;
	}
	@Override
	public Partie getPartie(int nummer) 
	{
		return partien[nummer];
	}
	@Override
	public boolean alleErgebnisEingetragen() 
	{
		boolean erg = true;
		for (int i=0;i<partien.length;i++)
		{
			int ergebnis = partien[i].getErgebnis();
			if (ergebnis == 0) erg = false;
		}
		return erg;
	}
	@Override
	public Spieler[] getSpieler() 
	{
		return spieler;
	}
	@Override
	public void setSpieler(Spieler[] spieler) 
	{
		this.spieler = spieler;		
	}
	@Override
	public String getErgebnis(Spieler spieler1, Spieler spieler2) 
	{
		String erg = null;
		for (int i=0;i<getMaxPartien();i++)
		{
			Partie partie = getPartie(i);
			Spieler t1 = partie.getWeiss();
			Spieler t2 = partie.getSchwarz();
			if (t1.getId() == spieler1.getId() && t2.getId() == spieler2.getId())
			{
				int x = partie.getErgebnis();
				erg = ErgebnisDarsteller.get(x);
			}
			if (t2.getId() == spieler1.getId() && t1.getId() == spieler2.getId())
			{
				int x = partie.getErgebnis();
				erg = ErgebnisDarsteller.getUmgedreht(x);
			}
		}
		return erg;
	}
}
