package org.myoggradio.stb;
public class ErgebnisDarsteller 
{
	public static String get(int ergebnis)
	{
		String erg = "? : ?";
		if (ergebnis == 0) erg = "  0  :  0 ";
		if (ergebnis == 1) erg = "1/2 : 1/2";
		if (ergebnis == 2) erg = "  1  :  0 ";
		if (ergebnis == 3) erg = "  0  :  1 ";
		return erg;
	}
	public static String getUmgedreht(int ergebnis)
	{
		String erg = "? : ?";
		if (ergebnis == 0) erg = "  0  :  0 ";
		if (ergebnis == 1) erg = "1/2 : 1/2";
		if (ergebnis == 2) erg = "  0  :  1 ";
		if (ergebnis == 3) erg = "  1  :  0 ";
		return erg;
	}
}
