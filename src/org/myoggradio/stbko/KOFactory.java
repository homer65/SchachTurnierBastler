package org.myoggradio.stbko;
import org.myoggradio.stbko.impl.*;
public class KOFactory 
{
	public static KOTurnierMenu getKOTurnierMenu()
	{
		return new SimpleKOTurnierMenu();
	}
}
