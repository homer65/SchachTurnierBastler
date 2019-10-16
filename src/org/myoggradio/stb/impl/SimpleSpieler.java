package org.myoggradio.stb.impl;
import java.io.Serializable;
import org.myoggradio.stb.*;
public class SimpleSpieler implements Spieler,Serializable
{
	private static final long serialVersionUID = 1L;
	private String vorname = "";
	private String name = "";
	private int dwz = 0;
	@Override
	public void setVorname(String s) 
	{
		vorname = s;		
	}
	@Override
	public void setName(String s) 
	{
		name = s;		
	}
	@Override
	public void setDWZ(int i) 
	{
		dwz = i;		
	}
	@Override
	public String getVorname() 
	{
		return vorname;
	}
	@Override
	public String getName() 
	{
		return name;
	}
	@Override
	public int getDWZ()
	{
		return dwz;
	}
	@Override
	public void fromString(String satz) 
	{
		String[] worte = satz.split("=");
		if (worte.length >= 4)
		{
			String id = worte[0].toUpperCase();
			if (id.equals("SPIELER"))
			{
				vorname = worte[1];
				name = worte[2];
				dwz = Integer.parseInt(worte[3]);
			}
		}
	}
	@Override
	public String toString()
	{
		String erg = "Spieler=";
		erg += vorname + "=";
		erg += name + "=";
		erg += dwz + "=";
		return erg;
	}
}
