package org.myoggradio.stb;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Protokol 
{
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static void write(String s)
	{
		Date jetzt = new Date();
		String prefix = sdf.format(jetzt);
		System.out.println(prefix + " " + s);
	}
}
