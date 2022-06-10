package org.myoggradio.stbko;
import org.myoggradio.stbko.impl.*;
public class KOFactory 
{
	public static KORunde getKORunde()
	{
		return new SimpleKORunde();
	}
	public static KOTurnierMenu getKOTurnierMenu()
	{
		return new SimpleKOTurnierMenu();
	}
	public static KOTurnierMenu2 getKOTurnierMenu2()
	{
		return new SimpleKOTurnierMenu2();
	}
	public static KOTurnier getKOTurnier()
	{
		return new SimpleKOTurnier();
	}
	public static KOTurnierManager getKOTurnierManager()
	{
		return new SimpleKOTurnierManager();
	}
	public static KOErgebnisDialog getKOErgebnisDialog()
	{
		return new SimpleKOErgebnisDialog();
	}
	public static KOTurnierLoader getKOTurnierLoader()
	{
		return new SimpleKOTurnierLoader();
	}
	public static KOTurnierSaver getKOTurnierSaver()
	{
		return new SimpleKOTurnierSaver();
	}
	public static KOTurnierAutoSaver getKOTurnierAutoSaver()
	{
		return new SimpleKOTurnierAutoSaver();
	}
	public static KOAuswertungDialog getKOAuswertungDialog()
	{
		return new SimpleKOAuswertungDialog();
	}
}
