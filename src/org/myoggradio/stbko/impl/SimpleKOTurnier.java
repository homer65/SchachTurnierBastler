package org.myoggradio.stbko.impl;
import java.io.Serializable;
import java.util.ArrayList;

import org.myoggradio.stb.Factory;
import org.myoggradio.stb.Spieler;
import org.myoggradio.stb.TurnierAutoSaver;
import org.myoggradio.stbko.*;
public class SimpleKOTurnier implements KOTurnier,Serializable
{
	private static final long serialVersionUID = -7340860382167608998L;
	private ArrayList<Spieler> spieler = null;
	private ArrayList<KORunde> runden = new ArrayList<KORunde>();
	@Override
	public void setSpieler(ArrayList<Spieler> spieler) 
	{
		this.spieler = spieler;		
	}
	@Override
	public void setNextRunde(KORunde runde) 
	{
		runden.add(runde);		
		KOTurnierAutoSaver tas = KOFactory.getKOTurnierAutoSaver();
		tas.save(this);
	}
	@Override
	public KORunde getRunde(int nummer) 
	{
		return runden.get(nummer);
	}
	@Override
	public KORunde getAktiveRunde() 
	{
		return runden.get(runden.size()-1);
	}
	@Override
	public int getNummerAktiveRunde()
	{
		return (runden.size()-1);
	}
	@Override
	public ArrayList<Spieler> getSpieler() 
	{
		return spieler;
	}
	@Override
	public void start() 
	{
		// TODO Auto-generated method stub	
	}
	@Override
	public boolean storniereAktiveRunde() 
	{
		// TODO Auto-generated method stub
		return false;
	}

}
