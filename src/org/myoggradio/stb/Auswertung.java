package org.myoggradio.stb;
public class Auswertung 
{
	private Spieler spieler = null;
	private double punkte = 0.0;
	private double buchholz = 0.0;
	private int anzahlWeiss = 0;
	private int anzahlSchwarz = 0;
	public void setAnzahlWeiss(int i)
	{
		anzahlWeiss = i;
	}
	public void setAnzahlSchwarz(int i)
	{
		anzahlSchwarz = i;
	}
	public void setSpieler(Spieler spieler)
	{
		this.spieler = spieler;
	}
	public void setPunkte(double d)
	{
		punkte = d;
	}
	public void setBuchholz(double d)
	{
		buchholz = d;
	}
	public int getAnzahlWeiss()
	{
		return anzahlWeiss;
	}
	public int getAnzahlSchwarz()
	{
		return anzahlSchwarz;
	}
	public Spieler getSpieler()
	{
		return spieler;
	}
	public double getPunkte()
	{
		return punkte;
	}
	public double getBuchholz()
	{
		return buchholz;
	}
}
