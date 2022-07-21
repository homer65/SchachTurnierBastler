package org.myoggradio.stb.impl;
import java.util.ArrayList;

import org.myoggradio.stb.Parameter;
import org.myoggradio.stb.Spieler;
import org.myoggradio.stb.SpielerManager;
public class SimpleSpielerManager implements SpielerManager
{
	@Override
	public int getMaxId(ArrayList<Spieler> spieler) 
	{
		int id = 0;
		if (spieler != null)
		{
			for (int i=0;i<spieler.size();i++)
			{
				int test = spieler.get(i).getId();
				if (test > id) id = test;
			}
		}
		if (Parameter.turnier != null)
		{
			ArrayList<Spieler> tspieler = Parameter.turnier.getSpieler();
			if (tspieler != null)
			{
				for (int i=0;i<tspieler.size();i++)
				{
					int test = tspieler.get(i).getId();
					if (test > id) id = test;
				}
			}
		}
		return id;
	}

	@Override
	public int getId(Spieler spieler, ArrayList<Spieler> spielerlist) 
	{
		int erg = 0;
		for (int i=0;i<spielerlist.size();i++)
		{
			Spieler test = spielerlist.get(i);
			boolean gleich = true;
			if (!test.getVorname().equals(spieler.getVorname())) gleich = false;
			if (!test.getName().equals(spieler.getName())) gleich = false;
			if (test.getDWZ() != spieler.getDWZ()) gleich = false;
			if (gleich) erg = test.getId();
		}
		return erg;
	}
}
