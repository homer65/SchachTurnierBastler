package org.myoggradio.stbko.impl;
import java.io.Serializable;
import org.myoggradio.stb.Partie;
import org.myoggradio.stbko.*;
public class SimpleKORunde implements KORunde,Serializable
{
	private static final long serialVersionUID = 1L;
	Partie[] partien = null;
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
			if (ergebnis == 1) erg = false;
		}
		return erg;
	}
}
