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
		int dwz1w = spieler1w.getDWZ();
		int dwz1s = spieler1s.getDWZ();
		int dwz2w = spieler2w.getDWZ();
		int dwz2s = spieler2s.getDWZ();
		int dwz1 = dwz1s + dwz1w;
		int dwz2 = dwz2s + dwz2w;
		if (dwz1 > dwz2) erg = -1;
		if (dwz2 > dwz1) erg = 1;
		return erg;
	}

}
