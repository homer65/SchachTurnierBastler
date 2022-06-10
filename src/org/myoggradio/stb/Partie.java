package org.myoggradio.stb;
public interface Partie 
{
	public void setWeiss(Spieler spieler);
	public void setSchwarz(Spieler spieler);
	public void setErgebnis(int i);
	public Spieler getWeiss();
	public Spieler getSchwarz();
	public int getErgebnis(); // 0:nicht gespielt; 1:unentschieden; 2:Sieg weiss; 3:Sieg schwarz
}
