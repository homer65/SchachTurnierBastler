package org.myoggradio.stb;
import java.util.Comparator;
public class AuswertungComparator implements Comparator<Auswertung>
{
	@Override
	public int compare(Auswertung o1, Auswertung o2) 
	{
		int erg = 0;
		double punkte1 = o1.getPunkte();
		double punkte2 = o2.getPunkte();
		double buchholz1 = o1.getBuchholz();
		double buchholz2 = o2.getBuchholz();
		int dwz1 = o1.getSpieler().getDWZ();
		int dwz2 = o2.getSpieler().getDWZ();
		if (punkte1 > punkte2) erg = -1;
		else if (punkte2 > punkte1) erg = 1;
		else
		{
			if (buchholz1 > buchholz2) erg = -1;
			else if (buchholz2 > buchholz1) erg = 1;
			else 
			{
				if (dwz1 > dwz2) erg = -1;
				else if (dwz2 > dwz1) erg = 1;
				else
				{
					erg = 0;
				}
			}
		}
		return erg;
	}

}
