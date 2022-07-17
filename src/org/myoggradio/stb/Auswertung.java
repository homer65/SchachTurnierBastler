package org.myoggradio.stb;
public class Auswertung 
{
	private Spieler spieler = null;
	private double punkte = 0.0;
	private double buchholz = 0.0;
	private double sonneberger = 0.0;
	private int anzahlWeiss = 0;
	private int anzahlSchwarz = 0;
	private int anzahlWeissHintereinander = 0;
	private int anzahlSchwarzHintereinander = 0;
	public void setAnzahlWeiss(int i)
	{
		anzahlWeiss = i;
	}
	public void setAnzahlWeissHintereinander(int i)
	{
		anzahlWeissHintereinander = i;
	}
	public void setAnzahlSchwarz(int i)
	{
		anzahlSchwarz = i;
	}
	public void setAnzahlSchwarzHintereinander(int i)
	{
		anzahlSchwarzHintereinander = i;
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
	public void setSonneberger(double d)
	{
		sonneberger = d;
	}
	public int getAnzahlWeiss()
	{
		return anzahlWeiss;
	}
	public int getAnzahlWeissHintereinander()
	{
		return anzahlWeissHintereinander;
	}
	public int getAnzahlSchwarz()
	{
		return anzahlSchwarz;
	}
	public int getAnzahlSchwarzHintereinander()
	{
		return anzahlSchwarzHintereinander;
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
	public double getSonneberger()
	{
		return sonneberger;
	}
}
