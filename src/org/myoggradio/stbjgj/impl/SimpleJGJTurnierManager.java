package org.myoggradio.stbjgj.impl;
import java.util.ArrayList;
import org.myoggradio.stb.*;
import org.myoggradio.stbjgj.JGJFactory;
import org.myoggradio.stbjgj.JGJRunde;
import org.myoggradio.stbjgj.JGJTurnier;
import org.myoggradio.stbjgj.JGJTurnierManager;
public class SimpleJGJTurnierManager implements JGJTurnierManager
{
	@Override
	public void starteFolgeRunden(JGJTurnier turnier)
	{
		Spieler freilos = Factory.getSpieler();
		freilos.setName("freilos");
		freilos.setVorname("");
		freilos.setDWZ(-1);
		JGJRunde runde = turnier.getRunde(0);
		Spieler[] spieler = runde.getSpieler();
		int nh = spieler.length / 2;
		for (int i=2;i<2*nh;i++)
		{
			JGJRunde nextRunde = JGJFactory.getJGJRunde();
			Spieler[] rspieler = new Spieler[spieler.length];
			for (int j=0;j<spieler.length;j++)
			{
				int k = j + 1;
				if (k == spieler.length) k = 0;
				rspieler[k] = spieler[j];
			}
			rspieler[nh] = spieler[nh];
			rspieler[nh+1] = spieler[nh-1];
			nextRunde.setMaxPartien(nh);
			nextRunde.setSpieler(rspieler);
			boolean zuerstWeiss = true;
			for (int j=0;j<nh;j++)
			{
				Partie partie = Factory.getPartie();
				if (zuerstWeiss)
				{
					zuerstWeiss = false;
					partie.setWeiss(rspieler[j]);
					partie.setSchwarz(rspieler[2*nh - 1 - j]);
				}
				else
				{
					zuerstWeiss = true;
					partie.setSchwarz(rspieler[j]);
					partie.setWeiss(rspieler[2*nh-j-1]);
				}
				partie.setErgebnis(0);
				if (partie.getWeiss().istGleich(freilos)) partie.setErgebnis(3);
				if (partie.getSchwarz().istGleich(freilos)) partie.setErgebnis(2);
				nextRunde.setPartie(partie,j);
			}
			turnier.setNextRunde(nextRunde);
			runde = nextRunde;
			spieler = rspieler;
		}
		return;
	}
	@Override
	public void starteErsteRunde(JGJTurnier turnier) 
	{
		JGJRunde erg = JGJFactory.getJGJRunde();
		Spieler freilos = Factory.getSpieler();
		freilos.setName("freilos");
		freilos.setVorname("");
		freilos.setDWZ(-1);
		ArrayList<Spieler> spieler = turnier.getSpieler();
		boolean ungerade = true;;
		int n = spieler.size();
		int nh = n / 2;
		int m = nh * 2;
		if (m == n) ungerade = false;
		if (ungerade)
		{
			n++;
			spieler.add(freilos);
			nh = n / 2;
		}
		Spieler[] rspieler = new Spieler[spieler.size()];
		for (int i=0;i<spieler.size();i++)
		{
			rspieler[i] = spieler.get(i);
		}
		erg.setSpieler(rspieler);
		erg.setMaxPartien(nh);
		boolean zuerstWeiss = true;
		for (int i=0;i<nh;i++)
		{
			Partie partie = Factory.getPartie();
			if (zuerstWeiss)
			{
				zuerstWeiss = false;
				partie.setWeiss(spieler.get(i));
				partie.setSchwarz(spieler.get(n-i-1));
			}
			else
			{
				zuerstWeiss = true;
				partie.setSchwarz(spieler.get(i));
				partie.setWeiss(spieler.get(n-i-1));
			}
			partie.setErgebnis(0);
			if (partie.getWeiss().istGleich(freilos)) partie.setErgebnis(3);
			if (partie.getSchwarz().istGleich(freilos)) partie.setErgebnis(2);
			erg.setPartie(partie,i);
		}
		turnier.setNextRunde(erg);
	}
}

