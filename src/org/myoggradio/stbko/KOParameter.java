package org.myoggradio.stbko;
import java.util.ArrayList;
import org.myoggradio.stb.Spieler;
public class KOParameter 
{
	public static ArrayList<Spieler> spieler = new ArrayList<Spieler>();
	public static ArrayList<Spieler> gesetzteSpieler = new ArrayList<Spieler>();
	public static KOTurnier turnier = KOFactory.getKOTurnier();
	public static int scrwidth = 1200;
	public static int scrheight = 650;
}
