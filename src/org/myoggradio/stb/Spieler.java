package org.myoggradio.stb;
public interface Spieler 
{
	public void setVorname(String s);
	public void setName(String s);
	public void setDWZ(int i);
	public String getVorname();
	public String getName();
	public int getDWZ();
	public String toString();
	public void fromString(String satz);
}
