package org.myoggradio.stb.impl;
import java.io.Serializable;
import org.myoggradio.stb.*;
public class SimpleSpieler implements Spieler,Serializable
{
	private static final long serialVersionUID = 1L;
	private int id = 0;
	private String vorname = "";
	private String name = "";
	private int dwz = 0;
	@Override
	public void setId(int i)
	{
		id = i;
	}
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
	public int getId()
	{
		return id;
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
		name = null;
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
	@Override
	public boolean istGleich(Spieler test)
	{
		boolean erg = false;
		if (id == test.getId()) erg = true;
		return erg;
	}
/*
	public boolean istGleich(Spieler test) 
	{
		boolean erg = true;
		if (dwz != test.getDWZ()) erg = false;
		if (!name.equals(test.getName())) erg = false;
		if (!vorname.equals(test.getVorname())) erg = false;
		return erg;
	}
*/
}
