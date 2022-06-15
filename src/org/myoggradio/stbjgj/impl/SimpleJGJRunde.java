package org.myoggradio.stbjgj.impl;
import java.io.Serializable;
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
}
