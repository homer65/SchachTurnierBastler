package org.myoggradio.stbjgj;
import java.util.ArrayList;
import org.myoggradio.stb.Spieler;
public interface JGJSpielerManager 
{
	public int getMaxId(ArrayList<Spieler> spielerlist);
	public void ergaenzeSpieler(Spieler spieler);
}
