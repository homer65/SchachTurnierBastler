package org.myoggradio.stb;
import org.myoggradio.stb.impl.*;
public class Factory 
{
	public static Initialisierung getInitialisierung()
	{
		return new NoInitialisierung();
	}
	public static MainMenu getMainMenu()
	{
		return new SimpleMainMenu();
	}
	public static Spieler getSpieler()
	{
		return new SimpleSpieler();
	}
	public static SpielerLoader getSpielerLoader()
	{
		return new FileSpielerLoader();
	}
	public static SpielerSaver getSpielerSaver()
	{
		return new FileSpielerSaver();
	}
	public static NeuerSpielerDialog getNeuerSpielerDialog()
	{
		return new SimpleNeuerSpielerDialog();
	}
	public static SpielerLoeschenDialog getSpielerLoeschenDialog()
	{
		return new SimpleSpielerLoeschenDialog();
	}
	public static SpielerAnzeigenDialog getSpielerAnzeigenDialog()
	{
		return new SimpleSpielerAnzeigenDialog();
	}
	public static AnzahlRundenDialog getAnzahlRundenDialog()
	{
		return new SimpleAnzahlRundenDialog();
	}
	public static TurnierMenu getTurnierMenu()
	{
		return new SimpleTurnierMenu();
	}
	public static Partie getPartie()
	{
		return new SimplePartie();
	}
	public static Runde getRunde()
	{
		return new SimpleRunde();
	}
	public static Turnier getTurnier()
	{
		return new SimpleTurnier();
	}
	public static ErgebnisDialog getErgebnisDialog()
	{
		return new SimpleErgebnisDialog();
	}
	public static TurnierManager getTurnierManager()
	{
		return new AdvancedTurnierManager();
	}
	public static AuswertungDialog getAuswertungDialog()
	{
		return new SimpleAuswertungDialog();
	}
	public static TurnierSaver getTurnierSaver()
	{
		return new FileTurnierSaver();
	}
	public static TurnierLoader getTurnierLoader()
	{
		return new FileTurnierLoader();
	}
	public static TurnierAutoSaver getTurnierAutoSaver()
	{
		return new FileTurnierAutoSaver();
	}
	public static RundenSortierer getRundenSortierer()
	{
		return new SimpleRundenSortierer();
	}
	public static SpielerStornierenDialog getSpielerStornierenDialog()
	{
		return new SimpleSpielerStornierenDialog();
	}
	public static SpielerAuswertungDialog getSpielerAuswertungDialog()
	{
		return new SimpleSpielerAuswertungDialog();
	}
}
