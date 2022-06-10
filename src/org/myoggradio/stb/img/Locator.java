package org.myoggradio.stb.img;
import java.net.*;
public class Locator 
{
	public URL getURL(String pfad)
	{
		URL url = this.getClass().getResource(pfad);
		return url;
	}
}
