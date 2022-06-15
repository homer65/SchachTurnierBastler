package org.myoggradio.stbjgj;
import org.myoggradio.stbjgj.impl.SimpleJGJErgebnisDialog;
import org.myoggradio.stbjgj.impl.SimpleJGJRunde;
import org.myoggradio.stbjgj.impl.SimpleJGJTurnier;
import org.myoggradio.stbjgj.impl.SimpleJGJTurnierManager;
import org.myoggradio.stbjgj.impl.SimpleJGJTurnierMenu;
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
		return null;
	}
	public static JGJTurnierSaver getJGJTurnierSaver()
	{
		return null;
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
}
