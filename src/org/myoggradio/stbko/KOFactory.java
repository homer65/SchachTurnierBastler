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
		return null;
	}
	public static KOTurnier getKOTurnier()
	{
		return new SimpleKOTurnier();
	}
	public static KOTurnierManager getKOTurnierManager()
	{
		return new SimpleKOTurnierManager();
	}
}
