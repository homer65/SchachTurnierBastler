package org.myoggradio.stb.impl;
import java.util.ArrayList;
import java.util.Collections;
import org.myoggradio.stb.*;
public class SimpleRundenSortierer implements RundenSortierer
{
	@Override
	public void sortierePartien(Runde runde) 
	{
		ArrayList<Partie> partien = new ArrayList<Partie>();
		for (int i=0;i<runde.getMaxPartien();i++)
		{
			Partie partie = runde.getPartie(i);
			partien.add(partie);
		}
		int nummerAktiveRunde = Parameter.turnier.getNummerAktiveRunde();
		if (nummerAktiveRunde >= 0)
		{
			Collections.sort(partien,new PartieComparator());
		}
		for (int i=0;i<partien.size();i++)
		{
			runde.setPartie(partien.get(i),i);
		}
	}
}
