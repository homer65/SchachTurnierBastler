package org.myoggradio.stbko;
import org.myoggradio.stb.*;
public interface KORunde 
{
	public void setMaxPartien(int n);
	public void setPartie(Partie partie,int nummer);
	public int getMaxPartien();
	public Partie getPartie(int nummer);
	public boolean alleErgebnisEingetragen();
}
