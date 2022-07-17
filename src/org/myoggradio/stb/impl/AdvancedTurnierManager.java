package org.myoggradio.stb.impl;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;
import org.myoggradio.stb.*;
public class AdvancedTurnierManager implements TurnierManager
{
	private Turnier turnier = null;
	@Override
	public Runde starteNaechsteRunde(Turnier turnier) 
	{
		int minimum = Integer.MAX_VALUE;
		Runde minimumRunde = null;
		this.turnier = turnier;
		int maxrunden = turnier.getMaxrunden();
		int aktiverunde = turnier.getNummerAktiveRunde();
		Protokol.write("AdvancedTurnierManager:startNaechsteRunde:maxrunden:" + maxrunden + ":aktiverunde:" + aktiverunde);
		int s=0; // Anzahl Iterationen bis zur Meldung
		int t=0; // Anzahl Iterationen
		if (maxrunden-1 > aktiverunde)
		{
			Parameter.auswertungen = getAuswertung(turnier.getNummerAktiveRunde());
			int n = Parameter.auswertungen.size();
			int nh = n / 2;
			boolean ungerade = false; // gerade Anzahl von Spielern
			if (2*nh != n)
			{
				ungerade = true; // ungerade Anzahl von Spielern
				nh++;
			}
			for (int i=0;i<Parameter.maxiter;i++) // Erste Iteration
			{
				s++;
				t++;
				if (s>=Parameter.itermsg)
				{
					s = 0;
					Protokol.write("AdvancedTurnierManager:Anzahl Iterationen: " + t + " Derzeitiges Minimum Teil1: " + minimum);
				}
				ArrayList<Spieler> geordneteAuswertung = new ArrayList<Spieler>(); 
				for (int x=0;x<n;x++)
				{
					geordneteAuswertung.add(Parameter.auswertungen.get(x).getSpieler());
				} // Spieler wurden nach Platzierung sortiert
				Spieler freilos = Factory.getSpieler();
				if (ungerade)
				{
					freilos.setId(-1);
					freilos.setDWZ(-1);
					geordneteAuswertung.add(freilos); // Freilos kommt auf den letzten Platz; Anzahl Spieler wird gerade
				}
				Runde runde = Factory.getRunde();
				runde.setMaxPartien(nh);
				boolean rundeErmittelbar = true;
				for (int j=0;j<nh;j++)  // Ermittelung der Paarungen
				{
					Spieler spieler1 = geordneteAuswertung.get(0); // Erster Spieler einer Paarung wird von oben genommen
					geordneteAuswertung.remove(0);
					/* 
					 Der zweite Spieler einer Paarung darf noch nicht gegen den Ersten gespielt haben
					 Er wird zufällig ermittelt, aber nur innerhalb eines bestimmten Abstandes (reichweite)
					*/
					// Protokol.write("AdvancedTurnierManager:debug:" + j + ":" + nh + ":" + geordneteAuswertung.size() + ":" + Parameter.reichweite);
					Spieler spieler2 = getRandomPlayer(geordneteAuswertung,Parameter.reichweite,spieler1);
					if (spieler2 != null)
					{
						geordneteAuswertung.remove(spieler2);
						double random = Math.random();
						Partie partie = Factory.getPartie();
						if (random > 0.5)
						{
							partie.setWeiss(spieler1);
							partie.setSchwarz(spieler2);
						}
						else
						{
							partie.setWeiss(spieler2);
							partie.setSchwarz(spieler1);
						}
						runde.setPartie(partie,j);
					}
					else
					{
						rundeErmittelbar = false; // Die Paarungen einer Runde konnten nicht ermittelt werden
						break;
					}
				}
				if (rundeErmittelbar)
				{
					if (ungerade) // Wenn ja, ermittele Freilos
					{
						Runde temp = Factory.getRunde();
						temp.setMaxPartien(nh-1);
						int l=0;
						for (int k=0;k<nh;k++)
						{
							Partie partie = runde.getPartie(k);
							Spieler weiss = partie.getWeiss();
							Spieler schwarz = partie.getSchwarz();
							if (weiss==freilos)
							{
								temp.addFreilos(schwarz);
							}
							else if (schwarz == freilos)
							{
								temp.addFreilos(weiss);
							}
							else
							{
								temp.setPartie(partie, l);
								l++;
							}
						}
						runde = temp;
					}
					int wertRunde = bewerteTeil1(runde); // Volle Bewertung
					if (wertRunde < minimum) // Hat diese Runde die beste (kleinste) Bewertung
					{
						minimum = wertRunde;
						minimumRunde = runde;
					}
				}
			}
			if (minimumRunde == null)
			{
				Protokol.write("AdvancedTurnierManager:Minimum Bewertung Teil1:Es konnte keine Minimum Runde ermittelt werden");
				JOptionPane.showMessageDialog(null,"Es konnte keine Runde ermittelt werden","Fehler",JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				Protokol.write("AdvancedTurnierManager:Minimum Bewertung Teil1: " + minimum);
				minimum = bewerteTeil2(minimumRunde);
				Protokol.write("AdvancedTurnierManager:Erste Bewertung Teil2: " + minimum);
				/*
				 Ergebnis steht eigentlich fest, aber es wird durch zufälliges Tauschen von Schwarz und Weiss
				 versucht weiter zu optimieren
				*/
				for (int i=0;i<Parameter.maxiter;i++) 
				{
					s++;
					t++;
					if (s>=Parameter.itermsg)
					{
						s = 0;
						Protokol.write("AdvancedTurnierManager:Anzahl Iterationen: " + t + " Derzeitiges Minimum Teil2: " + minimum);
					}
					Runde runde = copyRunde(minimumRunde); // Kein DeepCopy; Spieler müssen identifizierbar bleiben
					if (runde != null)
					{
						randomizeColor(runde);
						int wertRunde = bewerteTeil2(runde);
						if (wertRunde < minimum)
						{
							minimum = wertRunde;
							minimumRunde = runde;
						}
					}
				}
				Protokol.write("AdvancedTurnierManager:Minimum Bewertung Teil2: " + minimum);
				minimum = bewerteTeil3(minimumRunde);
				Protokol.write("AdvancedTurnierManager:Erste Bewertung Teil3: " + minimum);
				for (int i=0;i<minimumRunde.getMaxPartien();i++)
				{
					Runde runde = copyRunde(minimumRunde);
					Partie partie = runde.getPartie(i);
					Spieler weiss = partie.getWeiss();
					Spieler schwarz = partie.getSchwarz();
					partie.setWeiss(schwarz);
					partie.setSchwarz(weiss);
					int wertRunde = bewerteTeil3(runde);
					if (wertRunde < minimum)
					{
						minimum = wertRunde;
						minimumRunde = runde;
					}
				}
				Protokol.write("AdvancedTurnierManager:Minimum Bewertung Teil3: " + minimum);
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null,"Letzte Runde bereits erzeugt","Fehler",JOptionPane.INFORMATION_MESSAGE);
		}
		return minimumRunde;
	}
	private Runde copyRunde(Runde runde) // DeepCopy nicht möglich, da Spieler auf = abgefragt werden
	{
		Runde erg = Factory.getRunde();
		if (runde != null)
		{
		erg.setMaxPartien(runde.getMaxPartien());
		for (int i=0;i<runde.getMaxPartien();i++)
		{
			Partie partie = runde.getPartie(i);
			Partie temp = Factory.getPartie();
			temp.setErgebnis(partie.getErgebnis());
			temp.setWeiss(partie.getWeiss());
			temp.setSchwarz(partie.getSchwarz());
			erg.setPartie(temp, i);
		}
		ArrayList<Spieler> freilos = runde.getFreilos();
		for (int i=0;i<freilos.size();i++)
		{
			erg.addFreilos(freilos.get(i));
		}
		}
		else
		{
			erg = null;
		}
		return erg;
	}
	private void randomizeColor(Runde runde) // Tausche Schwarz und Weiss zufällig
	{
		for (int i=0;i<runde.getMaxPartien();i++)
		{
			Partie partie = runde.getPartie(i);
			randomizeColorPartie(partie);
		}
	}
	private void randomizeColorPartie(Partie partie)
	{
		double dr = Math.random();
		if (dr < 0.5)
		{
			Spieler weiss = partie.getWeiss();
			Spieler schwarz = partie.getSchwarz();
			partie.setWeiss(schwarz);
			partie.setSchwarz(weiss);				
		}
	}
	private Spieler getRandomPlayer(ArrayList<Spieler> spieler,int reichweite,Spieler spieler1)
	{
		ArrayList<Spieler> list = new ArrayList<Spieler>();
		for (int x=0;x<spieler.size();x++)
		{
			boolean ok = true;
			Partie partie = Factory.getPartie();
			partie.setWeiss(spieler1);
			partie.setSchwarz(spieler.get(x));
			for (int i=0;i<=turnier.getNummerAktiveRunde();i++)
			{
				Runde test = turnier.getRunde(i);
				for (int a=0;a<test.getMaxPartien();a++)
				{
					Partie partiet1 = test.getPartie(a);
					if (partieIstGleich(partiet1,partie)) ok = false;
				}
			}
			if (ok) list.add(spieler.get(x));
		}
		if (list.size() < reichweite) reichweite = list.size();
		Spieler erg = null;
		if (reichweite != 0)
		{
			double dr = (double) reichweite;
			double random = Math.random();
			dr = dr * random;
			int x = (int) dr;
			erg = list.get(x);
		}
		return erg;
	}
	private int bewerteTeil1(Runde runde)
	{
		int erg = 0;
		erg += bewerteKeinSpielerMehrAlsEinmalFreilos(runde);
		erg += bewerteDieFarbdifferenzEinesSpielersMussKleiner3Sein(runde);
		erg += bewerteKeinSpielerDarfDreimalHintereinanderDieGleicheFarbeHaben(runde);
		erg += bewerteDieFarbdifferenzEinesSpielersSollteKleiner2Sein(runde);
		erg += bewerteGleichGuteSpielerSolltenGegeneinaderSpielen(runde);
		return erg;
	}
	private int bewerteTeil2(Runde runde)
	{
		int erg = 0;
		if (runde != null)
		{
			//erg += bewerteKeinSpielerMehrAlsEinmalFreilos(runde);
			erg += bewerteDieFarbdifferenzEinesSpielersMussKleiner3Sein(runde);
			erg += bewerteKeinSpielerDarfDreimalHintereinanderDieGleicheFarbeHaben(runde);
			erg += bewerteDieFarbdifferenzEinesSpielersSollteKleiner2Sein(runde);
			//erg += bewerteGleichGuteSpielerSolltenGegeneinaderSpielen(runde);
		}
		return erg;
	}
	private int bewerteTeil3(Runde runde)
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
			if (runde != null)
			{
				anzahlWeiss+=getAnzahlWeiss(spieler,runde);
				anzahlSchwarz+=getAnzahlSchwarz(spieler,runde);
			}
			int delta = anzahlWeiss-anzahlSchwarz;
			int deltaq = delta * delta;
			erg += deltaq;
		}
		return erg;
	}
	private boolean partieIstGleich(Partie p1,Partie p2)
	{
		boolean erg = false;
		Spieler w1 = p1.getWeiss();
		Spieler s1 = p1.getSchwarz();
		Spieler w2 = p2.getWeiss();
		Spieler s2 = p2.getSchwarz();
		if (s1.istGleich(s2) && w1.istGleich(w2)) erg = true;
		if (s1.istGleich(w2) && w1.istGleich(s2)) erg = true;
		return erg;
	}
	private int bewerteKeinSpielerMehrAlsEinmalFreilos(Runde runde)
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
						if (freilos.get(a).istGleich(testfreilos.get(b))) erg += Parameter.malusMehrAlsEinmalFreilos;
					}
				}
			}
		}		
		return erg;
	}
	private int bewerteDieFarbdifferenzEinesSpielersMussKleiner3Sein(Runde runde)
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
			if (runde != null)
			{
				anzahlWeiss+=getAnzahlWeiss(spieler,runde);
				anzahlSchwarz+=getAnzahlSchwarz(spieler,runde);
			}
			int delta = anzahlWeiss-anzahlSchwarz;
			if (delta < 0) delta = anzahlSchwarz-anzahlWeiss;
			if (delta > 2) erg += Parameter.malusFarbdifferenz3;
		}
		return erg;
	}
	private int bewerteKeinSpielerDarfDreimalHintereinanderDieGleicheFarbeHaben(Runde runde)
	{
		int erg = 0;
		if (runde != null)
		{
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
		}
		return erg;
	}
	private int bewerteDieFarbdifferenzEinesSpielersSollteKleiner2Sein(Runde runde)
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
			if (runde != null)
			{
				anzahlWeiss+=getAnzahlWeiss(spieler,runde);
				anzahlSchwarz+=getAnzahlSchwarz(spieler,runde);
			}
			int delta = anzahlWeiss-anzahlSchwarz;
			if (delta < 0) delta = anzahlSchwarz-anzahlWeiss;
			if (delta > 1) erg += Parameter.malusFarbdifferenz2;
		}
		return erg;
	}
	private int getAnzahlWeiss(Spieler spieler,Runde runde)
	{
		int erg = 0;
		for (int i=0;i<runde.getMaxPartien();i++)
		{
			Partie partie = runde.getPartie(i);
			Spieler weiss = partie.getWeiss();
			if (weiss.istGleich(spieler)) erg++;
		}
		return erg;
	}
	private int getAnzahlSchwarz(Spieler spieler,Runde runde)
	{
		int erg = 0;
		for (int i=0;i<runde.getMaxPartien();i++)
		{
			Partie partie = runde.getPartie(i);
			Spieler schwarz = partie.getSchwarz();
			if (schwarz.istGleich(spieler)) erg++;
		}
		return erg;
	}
	private int bewerteGleichGuteSpielerSolltenGegeneinaderSpielen(Runde runde)
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
			ArrayList<Spieler> freilose = runde.getFreilos();
			for (int x=0;x<freilose.size();x++)
			{
				if (freilose.get(x) == spieler) erg = erg + 2;
			}
			for (int a=0;a<runde.getMaxPartien();a++)
			{
				Partie partie = runde.getPartie(a);
				Spieler weiss = partie.getWeiss();
				Spieler schwarz = partie.getSchwarz();
				int ergebnis = partie.getErgebnis();
				if (spieler.istGleich(weiss))
				{
					if (ergebnis == 1) erg += 1;
					if (ergebnis == 2) erg += 2;
				}
				if (spieler.istGleich(schwarz))
				{
					if (ergebnis == 1) erg += 1;
					if (ergebnis == 3) erg += 2;
				}
			}
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
			int anzahlWeiss = 0;
			int anzahlSchwarz = 0;
			int anzahlWeissHintereinander = 0;
			int anzahlSchwarzHintereinander = 0;
			for (int x=0;x<=rundenNummer;x++)
			{
				Runde runde = Parameter.turnier.getRunde(x);
				ArrayList<Spieler> freilos = runde.getFreilos();
				if (freilos != null)
				{
					for (int c=0;c<freilos.size();c++)
					{
						if (freilos.get(c).istGleich(spieler)) punkte = punkte + 1.0;
					}
				}
				for (int a=0;a<runde.getMaxPartien();a++)
				{
					Partie partie = runde.getPartie(a);
					Spieler weiss = partie.getWeiss();
					Spieler schwarz = partie.getSchwarz();
					int ergebnis = partie.getErgebnis();
					if (spieler.istGleich(weiss))
					{
						anzahlWeiss++;
						if (ergebnis == 1) punkte += 0.5;
						if (ergebnis == 2) punkte += 1.0;
					}
					if (spieler.istGleich(schwarz))
					{
						anzahlSchwarz++;
						if (ergebnis == 1) punkte += 0.5;
						if (ergebnis == 3) punkte += 1.0;
					}
				}
				for (int a=0;a<runde.getMaxPartien();a++)
				{
					Partie partie = runde.getPartie(a);
					Spieler weiss = partie.getWeiss();
					Spieler schwarz = partie.getSchwarz();
					if (spieler.istGleich(weiss))
					{
						anzahlWeissHintereinander++;
						anzahlSchwarzHintereinander = 0;
					}
					if (spieler.istGleich(schwarz))
					{
						anzahlSchwarzHintereinander++;
						anzahlWeissHintereinander = 0;
					}
				}
			}
			Auswertung auswertung = new Auswertung();
			auswertung.setSpieler(spieler);
			auswertung.setPunkte(punkte);
			auswertung.setAnzahlWeiss(anzahlWeiss);
			auswertung.setAnzahlSchwarz(anzahlSchwarz);
			auswertung.setAnzahlWeissHintereinander(anzahlWeissHintereinander);
			auswertung.setAnzahlSchwarzHintereinander(anzahlSchwarzHintereinander);
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
					if (spieler.istGleich(weiss))
					{
						for (int k=0;k<erg.size();k++)
						{
							Auswertung test = erg.get(k);
							Spieler testspieler = test.getSpieler();
							if (testspieler.istGleich(schwarz))
							{
								buchholz += test.getPunkte();
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
								buchholz += test.getPunkte();
							}
						}
					}
				}
			}
			auswertung.setBuchholz(buchholz);
		}
		for (int i=0;i<Parameter.turnier.getSpieler().size();i++)
		{
			Auswertung auswertung = erg.get(i);
			double sonneberger = 0.0;
			Spieler spieler = auswertung.getSpieler();
			for (int x=0;x<=rundenNummer;x++)
			{
				Runde runde = Parameter.turnier.getRunde(x);
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
		Collections.sort(erg,new AuswertungComparator());
		return erg;
	}
	@Override
	public void changeSpieler(Spieler alt, Spieler neu) 
	{
		for (int i=0;i<Parameter.turnier.getMaxrunden();i++)
		{
			Runde runde = Parameter.turnier.getRunde(i);
			if (runde != null)
			{
				runde.changeSpieler(alt,neu);
			}
		}
		ArrayList<Spieler> spieler = Parameter.turnier.getSpieler();
		if (spieler != null)
		{
			for (int i=0;i<spieler.size();i++)
			{
				Spieler test = spieler.get(i);
				if (test.istGleich(alt))
				{
					test.setId(neu.getId());
					test.setVorname(neu.getVorname());
					test.setName(neu.getName());
					test.setDWZ(neu.getDWZ());
				}
			}
		}
		if (turnier != null)
		{
			for (int i=0;i<turnier.getMaxrunden();i++)
			{
				Runde runde = turnier.getRunde(i);
				if (runde != null)
				{
					runde.changeSpieler(alt,neu);
				}
			}
			spieler = turnier.getSpieler();
			if (spieler != null)
			{
				for (int i=0;i<spieler.size();i++)
				{
					Spieler test = spieler.get(i);
					if (test.istGleich(alt))
					{
						test.setId(neu.getId());
						test.setVorname(neu.getVorname());
						test.setName(neu.getName());
						test.setDWZ(neu.getDWZ());
					}
				}
			}
		}
	}
}
