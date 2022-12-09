package org.myoggradio.stbjgj.impl;
import java.util.ArrayList;
import org.myoggradio.stb.*;
import org.myoggradio.stbjgj.JGJFactory;
import org.myoggradio.stbjgj.JGJParameter;
import org.myoggradio.stbjgj.JGJRunde;
import org.myoggradio.stbjgj.JGJTurnier;
import org.myoggradio.stbjgj.JGJTurnierManager;
public class SimpleJGJTurnierManager implements JGJTurnierManager
{
	@Override
	public void starteFolgeRunden(JGJTurnier turnier)
	{
		Spieler freilos = Factory.getSpieler();
		freilos.setId(-1);
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
			rspieler[nh-1] = spieler[nh-1];
			rspieler[nh] = spieler[nh-2];
			nextRunde.setMaxPartien(nh);
			nextRunde.setSpieler(rspieler);
			boolean zuerstWeiss = true;
			for (int j=0;j<nh;j++)
			{
				if (j == nh-1)
				{
					if (!istUngerade(i+j))
					{
						if (zuerstWeiss)
						{
							zuerstWeiss = false;
						}
						else
						{
							zuerstWeiss = true;
						}
					}
				}
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
		freilos.setId(-1);
		freilos.setName("freilos");
		freilos.setVorname("");
		freilos.setDWZ(-1);
		ArrayList<Spieler> spieler = turnier.getSpieler();
		boolean ungerade = true;
		int n = spieler.size();
		int nh = n / 2;
		int m = nh * 2;
		if (m == n) ungerade = false;
		if (ungerade)
		{
			n++;
			nh = n / 2;
			spieler.add(nh-1,freilos);
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
				partie.setWeiss(rspieler[i]);
				partie.setSchwarz(rspieler[n-i-1]);
			}
			else
			{
				zuerstWeiss = true;
				partie.setSchwarz(rspieler[i]);
				partie.setWeiss(rspieler[n-i-1]);
			}
			partie.setErgebnis(0);
			if (partie.getWeiss().istGleich(freilos)) partie.setErgebnis(3);
			if (partie.getSchwarz().istGleich(freilos)) partie.setErgebnis(2);
			erg.setPartie(partie,i);
		}
		turnier.setNextRunde(erg);
	}
	@Override
	public ArrayList<Auswertung> getAuswertung(int rundenNummer) 
	{
		Protokol.write("SimpleJGJTurnierManager:getAuswertung:Runde:" + rundenNummer);
		Spieler freilos = Factory.getSpieler();
		freilos.setId(-1);
		freilos.setName("freilos");
		freilos.setVorname("");
		freilos.setDWZ(-1);
		ArrayList<Auswertung> erg = new ArrayList<Auswertung>();
		for (int i=0;i<JGJParameter.turnier.getSpieler().size();i++)
		{
			Spieler spieler = JGJParameter.turnier.getSpieler().get(i);
			int anzahlWeiss = 0;
			int anzahlSchwarz = 0;
			double punkte = 0.0;
			for (int x=0;x<=rundenNummer;x++)
			{
				JGJRunde runde = JGJParameter.turnier.getRunde(x);
				for (int a=0;a<runde.getMaxPartien();a++)
				{
					Partie partie = runde.getPartie(a);
					Spieler weiss = partie.getWeiss();
					Spieler schwarz = partie.getSchwarz();
					int ergebnis = partie.getErgebnis();
					if (spieler.istGleich(weiss))
					{
						if (!schwarz.istGleich(freilos)) 
						{
							anzahlWeiss++;
							if (ergebnis == 1) punkte += 0.5;
							if (ergebnis == 2) punkte += 1.0;
						}
					}
					if (spieler.istGleich(schwarz))
					{
						if (!weiss.istGleich(freilos)) 
						{
							anzahlSchwarz++;
							if (ergebnis == 1) punkte += 0.5;
							if (ergebnis == 3) punkte += 1.0;
						}	
					}
				}
			}
			Auswertung auswertung = new Auswertung();
			auswertung.setSpieler(spieler);
			auswertung.setPunkte(punkte);
			auswertung.setAnzahlWeiss(anzahlWeiss);
			auswertung.setAnzahlSchwarz(anzahlSchwarz);
			erg.add(auswertung);
		}
		for (int i=0;i<JGJParameter.turnier.getSpieler().size();i++)
		{
			Auswertung auswertung = erg.get(i);
			double buchholz = 0.0;
			Spieler spieler = auswertung.getSpieler();
			for (int x=0;x<=rundenNummer;x++)
			{
				JGJRunde runde = JGJParameter.turnier.getRunde(x);
				for (int a=0;a<runde.getMaxPartien();a++)
				{
					Partie partie = runde.getPartie(a);
					Spieler weiss = partie.getWeiss();
					Spieler schwarz = partie.getSchwarz();
					if (spieler.istGleich(weiss))
					{
						for (int k=0;k<erg.size();k++)
						{
							Auswertung test = erg.get(k);
							Spieler testspieler = test.getSpieler();
							if (testspieler.istGleich(schwarz))
							{
								if (testspieler != freilos)
								{
									buchholz += test.getPunkte();
								}
							}
						}
					}
					if (spieler.istGleich(schwarz))
					{
						for (int k=0;k<erg.size();k++)
						{
							Auswertung test = erg.get(k);
							Spieler testspieler = test.getSpieler();
							if (testspieler.istGleich(weiss))
							{
								if (testspieler != freilos)
								{
									buchholz += test.getPunkte();
								}
							}
						}
					}
				}
			}
			auswertung.setBuchholz(buchholz);
		}
		
		for (int i=0;i<JGJParameter.turnier.getSpieler().size();i++)
		{
			Auswertung auswertung = erg.get(i);
			double sonneberger = 0.0;
			Spieler spieler = auswertung.getSpieler();
			for (int x=0;x<=rundenNummer;x++)
			{
				JGJRunde runde = JGJParameter.turnier.getRunde(x);
				for (int a=0;a<runde.getMaxPartien();a++)
				{
					Partie partie = runde.getPartie(a);
					Spieler weiss = partie.getWeiss();
					Spieler schwarz = partie.getSchwarz();
					if (spieler.istGleich(weiss))
					{
						for (int k=0;k<erg.size();k++)
						{
							Auswertung test = erg.get(k);
							Spieler testspieler = test.getSpieler();
							if (testspieler.istGleich(schwarz))
							{
								int partieergebnis = partie.getErgebnis();
								if (partieergebnis == 2) //Weiss hat gewonnen
								{
									sonneberger += test.getPunkte();
								}
								else if (partieergebnis == 1) //Unentschieden
								{
									sonneberger += test.getPunkte() / 2.0;
								}
							}
						}
					}
					if (spieler.istGleich(schwarz))
					{
						for (int k=0;k<erg.size();k++)
						{
							Auswertung test = erg.get(k);
							Spieler testspieler = test.getSpieler();
							if (testspieler.istGleich(weiss))
							{
								int partieergebnis = partie.getErgebnis();
								if (partieergebnis == 3) //Schwarz hat gewonnen
								{
									sonneberger += test.getPunkte();
								}
								else if (partieergebnis == 1) //Unentschieden
								{
									sonneberger += test.getPunkte() / 2.0;
								}
							}
						}
					}
				}
			}
			auswertung.setSonneberger(sonneberger);
		}
		
		ArrayList<Auswertung> gefiltert = new ArrayList<Auswertung>();
		for (int i=0;i<erg.size();i++)
		{
			Auswertung auswertung = erg.get(i);
			if (!auswertung.getSpieler().istGleich(freilos))
			{
				gefiltert.add(auswertung);
			}
		}
		return gefiltert;
	}
	private boolean istUngerade(int i)
	{
		boolean erg = true;
		int ih = i / 2;
		if (2*ih == i) erg = false;
		return erg;
	}
	@Override
	public String getErgebnis(JGJTurnier turnier, Spieler spieler1, Spieler spieler2, int rundenummer) 
	{
		String erg = "0:0";
		for (int i=0;i<rundenummer;i++)
		{
			JGJRunde runde = turnier.getRunde(i);
			String ergebnis = runde.getErgebnis(spieler1,spieler2);
			if (ergebnis != null) erg = ergebnis;
		}
		return erg;
	}
}