package org.myoggradio.stbjgj;
import org.myoggradio.stbjgj.impl.SimpleJGJAuswertungsDialog;
import org.myoggradio.stbjgj.impl.SimpleJGJErgebnisDialog;
import org.myoggradio.stbjgj.impl.SimpleJGJPartieAuswertungsDialog;
import org.myoggradio.stbjgj.impl.SimpleJGJRunde;
import org.myoggradio.stbjgj.impl.SimpleJGJTurnier;
import org.myoggradio.stbjgj.impl.SimpleJGJTurnierLoader;
import org.myoggradio.stbjgj.impl.SimpleJGJTurnierManager;
import org.myoggradio.stbjgj.impl.SimpleJGJTurnierMenu;
import org.myoggradio.stbjgj.impl.SimpleJGJTurnierSaver;
public class JGJFactory 
{
	public static JGJTurnier getJGJTurnier()
	{
		return new SimpleJGJTurnier();
	}
	public static JGJTurnierMenu getJGJTurnierMenu()
	{
		return new SimpleJGJTurnierMenu();
	}
	public static JGJTurnierLoader getJGJTurnierLoader()
	{
		return new SimpleJGJTurnierLoader();
	}
	public static JGJTurnierSaver getJGJTurnierSaver()
	{
		return new SimpleJGJTurnierSaver();
	}
	public static JGJRunde getJGJRunde()
	{
		return new SimpleJGJRunde();
	}
	public static JGJTurnierManager getJGJTurnierManager()
	{
		return new SimpleJGJTurnierManager();
	}
	public static JGJErgebnisDialog getJGJErgebnisDialog()
	{
		return new SimpleJGJErgebnisDialog();
	}
	public static JGJAuswertungsDialog getJGJAuswertungsDialog()
	{
		return new SimpleJGJAuswertungsDialog();
	}
	public static JGJPartieAuswertungsDialog getJGJPartieAuswertungsDialog()
	{
		return new SimpleJGJPartieAuswertungsDialog();
	}
}
