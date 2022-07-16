package org.myoggradio.stb;
import java.util.ArrayList;
public interface SpielerManager 
{
	public int getMaxId(ArrayList<Spieler> spieler);
	public int getId(Spieler spieler,ArrayList<Spieler> spielerlist);
}
