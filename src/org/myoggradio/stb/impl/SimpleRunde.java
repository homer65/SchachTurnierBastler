package org.myoggradio.stb.impl;
import java.io.Serializable;
import org.myoggradio.stb.*;
public class SimpleRunde implements Runde,Serializable
{
	private static final long serialVersionUID = 1L;
	private Partie[] partien = null;
	private Spieler freilos = null;
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
	public void setFreilos(Spieler spieler) 
	{
		freilos = spieler;		
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
	public Spieler getFreilos() 
	{
		return freilos;
	}
	@Override
	public boolean alleErgebnisEingetragen() 
	{
		boolean erg = true;
		for (int i=0;i<partien.length;i++)
		{
			Partie partie = partien[i];
			int ergebnis = partie.getErgebnis();
			if (ergebnis == 0) erg = false;
		}
		return erg;
	}
}
