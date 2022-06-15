package org.myoggradio.stbjgj.impl;
import java.io.Serializable;
import java.util.ArrayList;
import org.myoggradio.stb.Spieler;
import org.myoggradio.stbjgj.JGJFactory;
import org.myoggradio.stbjgj.JGJRunde;
import org.myoggradio.stbjgj.JGJTurnier;
import org.myoggradio.stbjgj.JGJTurnierManager;
public class SimpleJGJTurnier implements JGJTurnier,Serializable
{
	private static final long serialVersionUID = -7340860382167608998L;
	private ArrayList<Spieler> spieler = null;
	private ArrayList<JGJRunde> runden = new ArrayList<JGJRunde>();
	@Override
	public void setSpieler(ArrayList<Spieler> spieler) 
	{
		this.spieler = spieler;		
	}
	@Override
	public JGJRunde getRunde(int nummer) 
	{
		return runden.get(nummer);
	}
	@Override
	public ArrayList<Spieler> getSpieler() 
	{
		return spieler;
	}
	@Override
	public void start() 
	{
		JGJTurnierManager manager = JGJFactory.getJGJTurnierManager();	
		manager.starteErsteRunde(this);
		manager.starteFolgeRunden(this);
	}
	@Override
	public int getMaxrunden() 
	{
		return runden.size();
	}
	@Override
	public void setNextRunde(JGJRunde runde) 
	{
		runden.add(runde);
	}
}
