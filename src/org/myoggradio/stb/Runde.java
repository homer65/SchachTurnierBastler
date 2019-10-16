package org.myoggradio.stb;
public interface Runde 
{
	public void setMaxPartien(int n);
	public void setPartie(Partie partie,int nummer);
	public void setFreilos(Spieler spieler);
	public int getMaxPartien();
	public Partie getPartie(int nummer);
	public Spieler getFreilos();
	public boolean alleErgebnisEingetragen();
}
