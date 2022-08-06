package org.myoggradio.stbjgj.impl;
import java.util.ArrayList;
import org.myoggradio.stb.Factory;
import org.myoggradio.stb.Spieler;
import org.myoggradio.stbjgj.JGJFactory;
import org.myoggradio.stbjgj.JGJParameter;
import org.myoggradio.stbjgj.JGJSpielerManager;
import org.myoggradio.stbjgj.JGJTurnierManager;
public class SimpleJGJSpielerManager implements JGJSpielerManager
{
	@Override
	public int getMaxId(ArrayList<Spieler> spielerlist) 
	{
		int id = 0;
		if (spielerlist != null)
		{
			for (int i=0;i<spielerlist.size();i++)
			{
				int test = spielerlist.get(i).getId();
				if (test > id) id = test;
			}
		}
		if (JGJParameter.turnier != null)
		{
			ArrayList<Spieler> tspieler = JGJParameter.turnier.getSpieler();
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
	public void ergaenzeSpieler(Spieler spieler) 
	{
		ArrayList<Spieler> spielerlist = JGJParameter.spieler;
		boolean istFreilos = false;
		for (int i=0;i<spielerlist.size();i++)
		{
			Spieler test = spielerlist.get(i);
			if (test.getId() < 0)
			{
				istFreilos = true;
				spielerlist.remove(i);
				spielerlist.add(i,spieler);
			}
		}
		if (!istFreilos)
		{
			int nh = spielerlist.size() / 2;
			Spieler freilos = Factory.getSpieler();
			freilos.setId(-1);
			freilos.setName("freilos");
			JGJParameter.spieler.add(nh,freilos);
			JGJParameter.spieler.add(nh+1,spieler);
		}
		JGJTurnierManager manager = JGJFactory.getJGJTurnierManager();
		JGJParameter.turnier = JGJFactory.getJGJTurnier();
		JGJParameter.turnier.setSpieler(JGJParameter.spieler);
		manager.starteErsteRunde(JGJParameter.turnier);
		manager.starteFolgeRunden(JGJParameter.turnier);
	}
}
