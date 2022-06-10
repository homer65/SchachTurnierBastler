package org.myoggradio.stb.impl;
import java.io.Serializable;
import org.myoggradio.stb.*;
public class SimplePartie implements Partie,Serializable
{
	private static final long serialVersionUID = 1L;
	private Spieler weiss = null;
	private Spieler schwarz = null;
	private int ergebnis = 0;
	@Override
	public void setWeiss(Spieler spieler) 
	{
		weiss = spieler;		
	}
	@Override
	public void setSchwarz(Spieler spieler) 
	{
		schwarz = spieler;		
	}
	@Override
	public void setErgebnis(int i) 
	{
		ergebnis = i;		
	}
	@Override
	public Spieler getWeiss() 
	{
		return weiss;
	}
	@Override
	public Spieler getSchwarz() 
	{
		return schwarz;
	}
	@Override
	public int getErgebnis() 
	{
		return ergebnis;
	}
}
