package org.myoggradio.stb;
import java.util.Comparator;
public class NumberComparator implements Comparator<String>
{
	@Override
	public int compare(String o1, String o2) 
	{
		int erg = Integer.parseInt(o2) - Integer.parseInt(o1);
		return erg;
	}

}
