package org.myoggradio.stbjgj;
import org.myoggradio.stb.*;
import java.util.ArrayList;
public interface JGJTurnier 
{
	public void setSpieler(ArrayList<Spieler> spieler);
	public void setNextRunde(JGJRunde runde);
	public JGJRunde getRunde(int nummer);
	public int getMaxrunden();
	public ArrayList<Spieler> getSpieler();
	public void start();
}
