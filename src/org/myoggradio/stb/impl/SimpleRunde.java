package org.myoggradio.stb.impl;
import java.io.Serializable;
import java.util.ArrayList;
import org.myoggradio.stb.*;
public class SimpleRunde implements Runde,Serializable
{
	private static final long serialVersionUID = 1L;
	private Partie[] partien = null;
	private ArrayList<Spieler> freilos = new ArrayList<Spieler>();
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
	public void addFreilos(Spieler spieler) 
	{
		freilos.add(spieler);		
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
	public ArrayList<Spieler> getFreilos() 
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
	@Override
	public void ergaenzeSpieler(Spieler spieler) 
	{
		if (freilos.size() > 0)
		{
			Spieler gegner = freilos.get(freilos.size() - 1);
			freilos.remove(gegner);
			Partie partie = Factory.getPartie();
			partie.setWeiss(gegner);
			partie.setSchwarz(spieler);
			Partie[] temp = new Partie[partien.length + 1];
			for (int i=0;i<partien.length;i++)
			{
				temp[i] = partien[i];
			}
			temp[partien.length] = partie;
			partien = temp;
		}
		else
		{
			freilos.add(spieler);
		}
	}
	@Override
	public void storniereSpieler(Spieler spieler) 
	{
		ArrayList<Spieler> neuFreilos = new ArrayList<Spieler>();
		for (int i=0;i<freilos.size();i++)
		{
			Spieler test = freilos.get(i);
			if (!test.istGleich(spieler))
			{
				neuFreilos.add(test);
			}
		}
		freilos = neuFreilos;
		for (int i=0;i<partien.length;i++)
		{
			Partie partie = partien[i];
			Spieler weiss = partie.getWeiss();
			Spieler schwarz = partie.getSchwarz();
			if (spieler.istGleich(weiss))
			{
				addFreilos(schwarz);
				remove(partie);
			}
			else if (spieler.istGleich(schwarz))
			{
				addFreilos(weiss);
				remove(partie);
			}
		}
	}
	private void remove(Partie partie)
	{
		Partie[] temp = new Partie[partien.length-1];
		int j=0;
		for (int i=0;i<partien.length;i++)
		{
			Partie test = partien[i];
			if (test != partie)
			{
				temp[j] = test;
				j++;
			}
		}
		partien = temp;
	}
	@Override
	public void removeFreilos() 
	{
		freilos = new ArrayList<Spieler>();		
	}
	@Override
	public void changeSpieler(Spieler alt, Spieler neu) 
	{
		for (int i=0;i<freilos.size();i++)
		{
			if (freilos.get(i).istGleich(alt))
			{
				freilos.get(i).setVorname(neu.getVorname());
				freilos.get(i).setName(neu.getName());
				freilos.get(i).setDWZ(neu.getDWZ());
			}
		}
		for (int i=0;i<partien.length;i++)
		{
			Partie partie = partien[i];
			if (partie != null)
			{
				Spieler weiss = partie.getWeiss();
				Spieler schwarz = partie.getSchwarz();
				if (weiss != null)
				{
					if (weiss.istGleich(alt))
					{
						weiss.setId(neu.getId());
						weiss.setVorname(neu.getVorname());
						weiss.setName(neu.getName());
						weiss.setDWZ(neu.getDWZ());
					}
				}
				if (schwarz != null)
				{
					if (schwarz.istGleich(alt))
					{
						schwarz.setId(neu.getId());
						schwarz.setVorname(neu.getVorname());
						schwarz.setName(neu.getName());
						schwarz.setDWZ(neu.getDWZ());
					}
				}
			}
		}
	}
}
