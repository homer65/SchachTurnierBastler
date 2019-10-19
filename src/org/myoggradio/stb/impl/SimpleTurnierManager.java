package org.myoggradio.stb.impl;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.myoggradio.stb.*;
public class SimpleTurnierManager implements TurnierManager
{
	private Turnier turnier = null;
	@Override
	public Runde starteNaechsteRunde(Turnier turnier) 
	{
		this.turnier = turnier;
		Runde erg = null;
		int maxrunden = turnier.getMaxrunden();
		int aktiverunde = turnier.getNummerAktiveRunde();
		if (maxrunden-1 > aktiverunde)
		{
			Runde minrunde = getRandoomRunde();
			int minimum = bewerte(minrunde);
			int iter = 0;
			int iterx = 0;
			for (int i=0;i<Parameter.maxiter;i++)
			{
				iter++;
				iterx++;
				if (iterx >= Parameter.itermsg)
				{
					iterx = 0;
					Protokol.write("SimpleTurnierManager:AnzahlIteratione:" + iter);
				}
				Runde test = getRandoomRunde();
				int wert = bewerte(test);
				if (wert < minimum)
				{
					minimum = wert;
					minrunde = test;
				}
			}
			erg = minrunde;
			
			Protokol.write("SimpleTurnierManager:Bewertung Minimum: " + minimum);
		}
		else
		{
			JOptionPane.showMessageDialog(null,"Letzte Runde bereits erzeugt","Fehler",JOptionPane.INFORMATION_MESSAGE);
		}
		return erg;
	}
	public int bewerte(Runde runde)
	{
		int erg = 0;
		erg += bewerteNichtMehrAlsEinmalGegeneinander(runde);
		erg += bewerteKeinSpielerMehrAlsEinmalFreilos(runde);
		erg += bewerteDieFarbdifferenzEinesSpielersMussKleiner3Sein(runde);
		erg += bewerteKeinSpielerDarfDreimalHintereinanderDieGleicheFarbeHaben(runde);
		erg += bewerteDieFarbdifferenzEinesSpielersSollteKleiner2Sein(runde);
		erg += bewerteGleichGuteSpielerSolltenGegeneinaderSpielen(runde);
		return erg;
	}
	public int bewerteGleichGuteSpielerSolltenGegeneinaderSpielen(Runde runde)
	{
		int erg = 0;
		for (int i=0;i<runde.getMaxPartien();i++)
		{
			Partie partie = runde.getPartie(i);
			Spieler spieler1 = partie.getWeiss();
			Spieler spieler2 = partie.getSchwarz();
			int punktew = getAnzahlPunkte(spieler1);
			int punktes = getAnzahlPunkte(spieler2);
			int delta = punktew - punktes;
			if (delta < 0) delta = punktes - punktew;
			erg += delta * Parameter.malusGleichGut;
		}
		return erg;
	}
	public int getAnzahlPunkte(Spieler spieler)
	{
		int erg = 0;
		for (int i=0;i<=turnier.getNummerAktiveRunde();i++)
		{
			Runde runde = turnier.getRunde(i);
			if (runde.getFreilos() != null)
			{
				if (runde.getFreilos() == spieler) erg = erg + 2;
			}
			for (int a=0;a<runde.getMaxPartien();a++)
			{
				Partie partie = runde.getPartie(a);
				Spieler weiss = partie.getWeiss();
				Spieler schwarz = partie.getSchwarz();
				int ergebnis = partie.getErgebnis();
				if (spieler == weiss)
				{
					if (ergebnis == 1) erg += 1;
					if (ergebnis == 2) erg += 2;
				}
				if (spieler == schwarz)
				{
					if (ergebnis == 1) erg += 1;
					if (ergebnis == 3) erg += 2;
				}
			}
		}
		return erg;
	}
	public int bewerteKeinSpielerDarfDreimalHintereinanderDieGleicheFarbeHaben(Runde runde)
	{
		int erg = 0;
		int aktiveRunde = turnier.getNummerAktiveRunde();
		if (aktiveRunde > 0)
		{
			Runde rundet0 = runde;
			Runde rundet1 = turnier.getRunde(aktiveRunde);
			Runde rundet2 = turnier.getRunde(aktiveRunde-1);
			for (int i=0;i<turnier.getSpieler().size();i++)
			{
				Spieler spieler = turnier.getSpieler().get(i);
				int anzahlWeiss = getAnzahlWeiss(spieler,rundet0);
				anzahlWeiss += getAnzahlWeiss(spieler,rundet1);
				anzahlWeiss += getAnzahlWeiss(spieler,rundet2);
				int anzahlSchwarz = getAnzahlSchwarz(spieler,rundet0);
				anzahlSchwarz += getAnzahlSchwarz(spieler,rundet1);
				anzahlSchwarz += getAnzahlSchwarz(spieler,rundet2);
				if (anzahlWeiss == 3) erg += Parameter.malus3malGleicheFarbe;
				if (anzahlSchwarz == 3) erg += Parameter.malus3malGleicheFarbe;
			}
		}
		return erg;
	}
	public int bewerteDieFarbdifferenzEinesSpielersSollteKleiner2Sein(Runde runde)
	{
		int erg = 0;
		for (int i=0;i<turnier.getSpieler().size();i++)
		{
			Spieler spieler = turnier.getSpieler().get(i);
			int anzahlWeiss = 0;
			int anzahlSchwarz = 0;
			for (int a=0;a<=turnier.getNummerAktiveRunde();a++)
			{
				Runde testrunde = turnier.getRunde(a);
				anzahlWeiss+=getAnzahlWeiss(spieler,testrunde);
				anzahlSchwarz+=getAnzahlSchwarz(spieler,testrunde);
			}
			anzahlWeiss+=getAnzahlWeiss(spieler,runde);
			anzahlSchwarz+=getAnzahlSchwarz(spieler,runde);
			int delta = anzahlWeiss-anzahlSchwarz;
			if (delta < 0) delta = anzahlSchwarz-anzahlWeiss;
			if (delta > 1) erg += Parameter.malusFarbdifferenz2;
		}
		return erg;
	}
	public int bewerteDieFarbdifferenzEinesSpielersMussKleiner3Sein(Runde runde)
	{
		int erg = 0;
		for (int i=0;i<turnier.getSpieler().size();i++)
		{
			Spieler spieler = turnier.getSpieler().get(i);
			int anzahlWeiss = 0;
			int anzahlSchwarz = 0;
			for (int a=0;a<=turnier.getNummerAktiveRunde();a++)
			{
				Runde testrunde = turnier.getRunde(a);
				anzahlWeiss+=getAnzahlWeiss(spieler,testrunde);
				anzahlSchwarz+=getAnzahlSchwarz(spieler,testrunde);
			}
			anzahlWeiss+=getAnzahlWeiss(spieler,runde);
			anzahlSchwarz+=getAnzahlSchwarz(spieler,runde);
			int delta = anzahlWeiss-anzahlSchwarz;
			if (delta < 0) delta = anzahlSchwarz-anzahlWeiss;
			if (delta > 2) erg += Parameter.malusFarbdifferenz3;
		}
		return erg;
	}
	public int getAnzahlWeiss(Spieler spieler,Runde runde)
	{
		int erg = 0;
		for (int i=0;i<runde.getMaxPartien();i++)
		{
			Partie partie = runde.getPartie(i);
			Spieler weiss = partie.getWeiss();
			if (weiss == spieler) erg++;
		}
		return erg;
	}
	public int getAnzahlSchwarz(Spieler spieler,Runde runde)
	{
		int erg = 0;
		for (int i=0;i<runde.getMaxPartien();i++)
		{
			Partie partie = runde.getPartie(i);
			Spieler schwarz = partie.getSchwarz();
			if (schwarz == spieler) erg++;
		}
		return erg;
	}
	public int bewerteKeinSpielerMehrAlsEinmalFreilos(Runde runde)
	{
		int erg = 0;
		if (Parameter.turnier.getUngerade() == 1)
		{
			ArrayList<Spieler> freilos = runde.getFreilos();
			for (int a=0;a<freilos.size();a++)
			{
				for (int i=0;i<=turnier.getNummerAktiveRunde();i++)
				{
					Runde test = turnier.getRunde(i);
					ArrayList<Spieler> testfreilos = test.getFreilos();
					for (int b=0;b<testfreilos.size();b++)
					{
						if (freilos.get(a) == testfreilos.get(b)) erg += Parameter.malusMehrAlsEinmalFreilos;
					}
				}
			}
		}		
		return erg;
	}
	public int bewerteNichtMehrAlsEinmalGegeneinander(Runde runde)
	{
		int erg = 0;
		for (int i=0;i<=turnier.getNummerAktiveRunde();i++)
		{
			Runde test = turnier.getRunde(i);
			for (int a=0;a<test.getMaxPartien();a++)
			{
				Partie partiet1 = test.getPartie(a);
				for (int b=0;b<runde.getMaxPartien();b++)
				{
					Partie partiet2 = runde.getPartie(b);
					if (partieIstGleich(partiet1,partiet2)) erg+=Parameter.malusGleichePartie;
				}
			}
		}
		return erg;
	}
	public boolean partieIstGleich(Partie p1,Partie p2)
	{
		boolean erg = false;
		Spieler w1 = p1.getWeiss();
		Spieler s1 = p1.getSchwarz();
		Spieler w2 = p2.getWeiss();
		Spieler s2 = p2.getSchwarz();
		if (s1 == s2 && w1 == w2) erg = true;
		if (s1 == w2 && w1 == s2) erg = true;
		return erg;
	}
	public Runde getRandoomRunde()
	{
		Runde erg = Factory.getRunde();
		int n = turnier.getSpieler().size();
		Spieler[] neuGeordneteSpieler = new Spieler[n];
		Spieler[] altGeordneteSpieler = new Spieler[n];
		for (int i=0;i<n;i++)
		{
			altGeordneteSpieler[i] = turnier.getSpieler().get(i);
		}
		int m = n;
		for (int i=0;i<n;i++)
		{
			double dm = (double) m;
			double zufall = Math.random();
			int x = (int) (dm * zufall);
			neuGeordneteSpieler[i] = altGeordneteSpieler[x];
			for (int j=x;j<m-1;j++)
			{
				altGeordneteSpieler[j] = altGeordneteSpieler[j+1];
			}
			m--;
		}
		int nh = n / 2;
		erg.setMaxPartien(nh);
		if (2*nh != n)
		{
			erg.addFreilos(neuGeordneteSpieler[n-1]);
		}
		for (int i=0;i<nh;i++)
		{
			Partie partie = Factory.getPartie();
			Spieler spieler1 = neuGeordneteSpieler[i];
			Spieler spieler2 = neuGeordneteSpieler[i + nh];
			partie.setWeiss(spieler1);
			partie.setSchwarz(spieler2);
			erg.setPartie(partie,i);
		}
		return erg;
	}
	@Override
	public ArrayList<Auswertung> getAuswertung(int rundenNummer) 
	{
		ArrayList<Auswertung> erg = new ArrayList<Auswertung>();
		for (int i=0;i<Parameter.turnier.getSpieler().size();i++)
		{
			Spieler spieler = Parameter.turnier.getSpieler().get(i);
			double punkte = 0.0;
			for (int x=0;x<=rundenNummer;x++)
			{
				Runde runde = Parameter.turnier.getRunde(x);
				if (runde.getFreilos() != null)
				{
					for (int c=0;c<runde.getFreilos().size();c++)
					{
						if (runde.getFreilos().get(c) == spieler) punkte = punkte + 1.0;
					}
				}
				for (int a=0;a<runde.getMaxPartien();a++)
				{
					Partie partie = runde.getPartie(a);
					Spieler weiss = partie.getWeiss();
					Spieler schwarz = partie.getSchwarz();
					int ergebnis = partie.getErgebnis();
					if (spieler == weiss)
					{
						if (ergebnis == 1) punkte += 0.5;
						if (ergebnis == 2) punkte += 1.0;
					}
					if (spieler == schwarz)
					{
						if (ergebnis == 1) punkte += 0.5;
						if (ergebnis == 3) punkte += 1.0;
					}
				}
			}
			Auswertung auswertung = new Auswertung();
			auswertung.setSpieler(spieler);
			auswertung.setPunkte(punkte);
			erg.add(auswertung);
		}
		for (int i=0;i<Parameter.turnier.getSpieler().size();i++)
		{
			Auswertung auswertung = erg.get(i);
			double buchholz = 0.0;
			Spieler spieler = auswertung.getSpieler();
			for (int x=0;x<=rundenNummer;x++)
			{
				Runde runde = Parameter.turnier.getRunde(x);
				for (int a=0;a<runde.getMaxPartien();a++)
				{
					Partie partie = runde.getPartie(a);
					Spieler weiss = partie.getWeiss();
					Spieler schwarz = partie.getSchwarz();
					if (spieler == weiss)
					{
						for (int k=0;k<erg.size();k++)
						{
							Auswertung test = erg.get(k);
							Spieler testspieler = test.getSpieler();
							if (testspieler == schwarz)
							{
								buchholz += test.getPunkte();
							}
						}
					}
					if (spieler == schwarz)
					{
						for (int k=0;k<erg.size();k++)
						{
							Auswertung test = erg.get(k);
							Spieler testspieler = test.getSpieler();
							if (testspieler == weiss)
							{
								buchholz += test.getPunkte();
							}
						}
					}
				}
			}
			auswertung.setBuchholz(buchholz);
		}
		return erg;
	}
}

	