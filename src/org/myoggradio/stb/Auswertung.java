package org.myoggradio.stb;
public class Auswertung 
{
	private Spieler spieler = null;
	private double punkte = 0.0;
	private double buchholz = 0.0;
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
