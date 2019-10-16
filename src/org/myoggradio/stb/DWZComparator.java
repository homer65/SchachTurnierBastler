package org.myoggradio.stb;
import java.util.Comparator;
public class DWZComparator implements Comparator<Spieler>
{
	@Override
	public int compare(Spieler o1, Spieler o2) 
	{
		int erg = 0;
		int dwz1 = o1.getDWZ();
		int dwz2 = o2.getDWZ();
		if (dwz1 > dwz2) erg = -1;
		if (dwz2 > dwz1) erg = 1;
		return erg;
	}

}
