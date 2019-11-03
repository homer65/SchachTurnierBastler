package org.myoggradio.stbko.impl;
import java.util.ArrayList;
import org.myoggradio.stb.Spieler;
import org.myoggradio.stbko.*;
public class SimpleKOTurnier implements KOTurnier
{
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
	}
	@Override
	public KORunde getRunde(int nummer) 
	{
		return runden.get(nummer-1);
	}
	@Override
	public KORunde getAktiveRunde() 
	{
		return runden.get(runden.size()-1);
	}
	@Override
	public int getNummerAktiveRunde()
	{
		return runden.size();
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
