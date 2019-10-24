package org.myoggradio.stb;
import java.util.ArrayList;
public class Parameter 
{
	public static String version = "0.20 - 24.10.2019";
	public static int anzahlRunden = 7;
	public static ArrayList<Spieler> spieler = new ArrayList<Spieler>();
	public static Turnier turnier = Factory.getTurnier();
	public static ArrayList<Auswertung> auswertungen = new ArrayList<Auswertung>();
	public static int maxiter = 1000000;
	public static int itermsg = 100000;
	public static int malusGleichePartie = 1000000;
	public static int malusMehrAlsEinmalFreilos = 900000;
	public static int malusFarbdifferenz3 = 900000;
	public static int malus3malGleicheFarbe = 900000;
	public static int malusFarbdifferenz2 = 1;
	public static int malusGleichGut = 25;
	public static int scrwidth = 800;
	public static int scrheight = 500;
	public static int reichweite = 3;
}
