package org.myoggradio.stb;
import java.util.Comparator;
public class PartieComparator implements Comparator<Partie>
{
	@Override
	public int compare(Partie o1, Partie o2) 
	{
		int erg = 0;
		Spieler spieler1w = o1.getWeiss();
		Spieler spieler1s = o1.getSchwarz();
		Spieler spieler2w = o2.getWeiss();
		Spieler spieler2s = o2.getSchwarz();
		int pos1w = 0;
		int pos1s = 0;
		int pos2w = 0;
		int pos2s = 0;
		int n = Parameter.auswertungen.size();
		for (int i=0;i<n;i++)
		{
			Auswertung auswertung = Parameter.auswertungen.get(i);
			Spieler spieler = auswertung.getSpieler();
			if (spieler == spieler1w) pos1w = i;
			if (spieler == spieler1s) pos1s = i;
			if (spieler == spieler2w) pos2w = i;
			if (spieler == spieler2s) pos2s = i;
		}
		int pos1 = pos1s + pos1w;
		int pos2 = pos2s + pos2w;
		if (pos1s < pos1w) pos1s = pos1w;
		if (pos2s < pos2w) pos2s = pos2w;
		//
		if (pos1s > pos2s) erg = 1;
		else if (pos2s > pos1s) erg = -1;
		else
		{
			if (pos1 > pos2) erg = 1;
			else if (pos2 > pos1) erg = -1;
			else erg = 0;
		}
		//Protokol.write(spieler1w.getName() + "-" + spieler1s.getName() + " " + pos1);
		//Protokol.write(spieler2w.getName() + "-" + spieler2s.getName() + " " + pos2);
		return erg;
	}

}
