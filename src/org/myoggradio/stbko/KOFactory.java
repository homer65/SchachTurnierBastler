package org.myoggradio.stbko;
import org.myoggradio.stbko.impl.*;
public class KOFactory 
{
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
		return null;
	}
	public static KOTurnierManager getKOTurnierManager()
	{
		return null;
	}
}
