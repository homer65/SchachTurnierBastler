package org.myoggradio.stb;
import java.util.ArrayList;
public class Parameter 
{
	public static String version = "0.41 - 14.06.2022";
	public static int anzahlRunden = 7;
	public static ArrayList<Spieler> spieler = new ArrayList<Spieler>();
	public static Turnier turnier = Factory.getTurnier();
	public static ArrayList<Auswertung> auswertungen = new ArrayList<Auswertung>();
	public static int maxiter = 500000;
	public static int itermsg = 50000;
	public static int malusGleichePartie = 1000000;
	public static int malusMehrAlsEinmalFreilos = 950000;
	public static int malusFarbdifferenz3 = 900000;
	public static int malus3malGleicheFarbe = 900000;
	public static int malusFarbdifferenz2 = 1;
	public static int malusGleichGut = 25;
	public static int scrwidth = 1200;
	public static int scrheight = 500;
	public static int reichweite = 3;
}
