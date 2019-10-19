package org.myoggradio.stb;
import java.util.ArrayList;
public class Parameter 
{
	public static String version = "0.14 - 19.10.2019";
	public static int anzahlRunden = 7;
	public static ArrayList<Spieler> spieler = new ArrayList<Spieler>();
	public static Turnier turnier = Factory.getTurnier();
	public static ArrayList<Auswertung> auswertungen = new ArrayList<Auswertung>();
	public static int maxiter = 1000000;
	public static int itermsg = 100000;
	public static int malusGleichePartie = 10000;
	public static int malusMehrAlsEinmalFreilos = 10000;
	public static int malusFarbdifferenz3 = 9000;
	public static int malus3malGleicheFarbe = 9500;
	public static int malusFarbdifferenz2 = 1;
	public static int malusGleichGut = 1;
}
