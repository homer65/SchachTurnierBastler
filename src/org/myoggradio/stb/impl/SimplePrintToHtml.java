package org.myoggradio.stb.impl;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import org.myoggradio.stb.Auswertung;
import org.myoggradio.stb.ErgebnisDarsteller;
import org.myoggradio.stb.Factory;
import org.myoggradio.stb.Parameter;
import org.myoggradio.stb.Partie;
import org.myoggradio.stb.PrintToHtml;
import org.myoggradio.stb.Protokol;
import org.myoggradio.stb.Runde;
import org.myoggradio.stb.Spieler;
import org.myoggradio.stb.TurnierManager;
import org.myoggradio.stbjgj.JGJRunde;
import org.myoggradio.stbko.KORunde;
public class SimplePrintToHtml implements PrintToHtml
{
	private File file = new File(Parameter.autoSaveDirectory + File.separator + "SchachTurnierBastler.html");
	private Writer wrt = null;
	public SimplePrintToHtml()
	{
		try
		{
			OutputStream os = new FileOutputStream(file);
			wrt = new OutputStreamWriter(os,"UTF-8");
			wrt.write("<html lang=\"de-DE\">" + "\n");
			wrt.write("<head>" + "\n");
			wrt.write("<meta charset=\"UTF-8\">" + "\n");
			wrt.write("<style>" + "\n");
			wrt.write("td {border:1px solid;}" + "\n");
			wrt.write("th {border:2px solid;}" + "\n");
			wrt.write("</style>" + "\n");
			wrt.write("</head>" + "\n");
			wrt.write("<body>" + "\n");
		}
		catch (Exception e)
		{
			Protokol.write("SimplePrintToHtml::Exception:");
			Protokol.write(e.toString());
		}
	}
	private void finish() throws Exception
	{
		wrt.write("</body>" + "\n");
		wrt.write("</html>" + "\n");
		wrt.close();
		Desktop desktop = Desktop.getDesktop();
		desktop.browse(file.toURI());
	}
	@Override
	public void print(Runde runde,int rundenummer) 
	{
		try
		{
			wrt.write("<p>");
			wrt.write("<b>Runde: " + rundenummer + "</b>");
			wrt.write("</p>");
			wrt.write("<table>" + "\n");
			wrt.write("<tr>" + "\n");
			wrt.write("<th>" + "\n");
			wrt.write("<b>Tisch</b>");
			wrt.write("</th>" + "\n");			
			wrt.write("<th>" + "\n");
			wrt.write("<b>Weiss</b>");
			wrt.write("</th>" + "\n");
			wrt.write("<th>" + "\n");
			wrt.write("<b>Schwarz</b>");
			wrt.write("</th>" + "\n");
			wrt.write("<th>" + "\n");
			wrt.write("<b>Ergebnis</b>");
			wrt.write("</th>" + "\n");
			wrt.write("</tr>" + "\n");
			for (int i=0;i<runde.getMaxPartien();i++)
			{
				Partie partie = runde.getPartie(i);
				Spieler weiss = partie.getWeiss();
				Spieler schwarz = partie.getSchwarz();
				int ergebnis = partie.getErgebnis();
				String serg = ErgebnisDarsteller.get(ergebnis);
				wrt.write("<tr>" + "\n");
				wrt.write("<td>" + "\n");
				wrt.write(" " + (i + 1));
				wrt.write("</td>" + "\n");
				wrt.write("<td>" + "\n");
				wrt.write(weiss.getVorname() + " " + weiss.getName() + " " + weiss.getDWZ() + " (" + getPunkte(weiss,rundenummer) + ")");
				wrt.write("</td>" + "\n");
				wrt.write("<td>" + "\n");
				wrt.write(schwarz.getVorname() + " " + schwarz.getName() + " " + schwarz.getDWZ() + " (" + getPunkte(schwarz,rundenummer) + ")");
				wrt.write("</td>" + "\n");
				wrt.write("<td>" + "\n");
				wrt.write(serg);
				wrt.write("</td>" + "\n");
				wrt.write("</tr>" + "\n");
			}
			wrt.write("</table>" + "\n");
			finish();
		}
		catch (Exception e)
		{
			Protokol.write("SimplePrintToHtml:print:Exception:");
			Protokol.write(e.toString());
		}
	}
	@Override
	public void print(KORunde runde) 
	{
		try
		{
			wrt.write("<table>" + "\n");
			wrt.write("<tr>" + "\n");
			wrt.write("<th>" + "\n");
			wrt.write("<b>Tisch</b>");
			wrt.write("</th>" + "\n");
			wrt.write("<th>" + "\n");
			wrt.write("<b>Weiss</b>");
			wrt.write("</th>" + "\n");
			wrt.write("<th>" + "\n");
			wrt.write("<b>Schwarz</b>");
			wrt.write("</th>" + "\n");
			wrt.write("<th>" + "\n");
			wrt.write("<b>Ergebnis</b>");
			wrt.write("</th>" + "\n");
			wrt.write("</tr>" + "\n");
			for (int i=0;i<runde.getMaxPartien();i++)
			{
				Partie partie = runde.getPartie(i);
				Spieler weiss = partie.getWeiss();
				Spieler schwarz = partie.getSchwarz();
				int ergebnis = partie.getErgebnis();
				String serg = ErgebnisDarsteller.get(ergebnis);
				wrt.write("<tr>" + "\n");
				wrt.write("<td>" + "\n");
				wrt.write(" " + (i + 1));
				wrt.write("</td>" + "\n");
				wrt.write("<td>" + "\n");
				wrt.write(weiss.getVorname() + " " + weiss.getName() + " " + weiss.getDWZ());
				wrt.write("</td>" + "\n");
				wrt.write("<td>" + "\n");
				wrt.write(schwarz.getVorname() + " " + schwarz.getName() + " " + schwarz.getDWZ());
				wrt.write("</td>" + "\n");
				wrt.write("<td>" + "\n");
				wrt.write(serg);
				wrt.write("</td>" + "\n");
				wrt.write("</tr>" + "\n");
			}
			wrt.write("</table>" + "\n");
			finish();
		}
		catch (Exception e)
		{
			Protokol.write("SimplePrintToHtml:print:Exception:");
			Protokol.write(e.toString());
		}
	}
	@Override
	public void print(JGJRunde runde) 
	{
		try
		{
			wrt.write("<table>" + "\n");
			wrt.write("<tr>" + "\n");
			wrt.write("<th>" + "\n");
			wrt.write("<b>Tisch</b>");
			wrt.write("</th>" + "\n");
			wrt.write("<th>" + "\n");
			wrt.write("<b>Weiss</b>");
			wrt.write("</th>" + "\n");
			wrt.write("<th>" + "\n");
			wrt.write("<b>Schwarz</b>");
			wrt.write("</th>" + "\n");
			wrt.write("<th>" + "\n");
			wrt.write("<b>Ergebnis</b>");
			wrt.write("</th>" + "\n");
			wrt.write("</tr>" + "\n");
			for (int i=0;i<runde.getMaxPartien();i++)
			{
				Partie partie = runde.getPartie(i);
				Spieler weiss = partie.getWeiss();
				Spieler schwarz = partie.getSchwarz();
				int ergebnis = partie.getErgebnis();
				String serg = ErgebnisDarsteller.get(ergebnis);
				wrt.write("<tr>" + "\n");
				wrt.write("<td>" + "\n");
				wrt.write(" " + (i + 1));
				wrt.write("</td>" + "\n");
				wrt.write("<td>" + "\n");
				wrt.write(weiss.getVorname() + " " + weiss.getName() + " " + weiss.getDWZ());
				wrt.write("</td>" + "\n");
				wrt.write("<td>" + "\n");
				wrt.write(schwarz.getVorname() + " " + schwarz.getName() + " " + schwarz.getDWZ());
				wrt.write("</td>" + "\n");
				wrt.write("<td>" + "\n");
				wrt.write(serg);
				wrt.write("</td>" + "\n");
				wrt.write("</tr>" + "\n");
			}
			wrt.write("</table>" + "\n");
			finish();
		}
		catch (Exception e)
		{
			Protokol.write("SimplePrintToHtml:print:Exception:");
			Protokol.write(e.toString());
		}	
	}
	@Override
	public void print(ArrayList<Auswertung> auswertungen) 
	{
		try
		{
			wrt.write("<table>" + "\n");
			wrt.write("<tr>" + "\n");
			wrt.write("<th>" + "\n");
			wrt.write("<b>Spieler</b>");
			wrt.write("</th>" + "\n");
			wrt.write("<th>" + "\n");
			wrt.write("<b>Punkte</b>");
			wrt.write("</th>" + "\n");
			wrt.write("<th>" + "\n");
			wrt.write("<b>Buchholz</b>");
			wrt.write("</th>" + "\n");
			wrt.write("<th>" + "\n");
			wrt.write("<b>Sonneberger</b>");
			wrt.write("</th>" + "\n");
			wrt.write("<th>" + "\n");
			wrt.write("<b>Anzahl Weiss</b>");
			wrt.write("</th>" + "\n");
			wrt.write("<th>" + "\n");
			wrt.write("<b>Anzahl Schwarz</b>");
			wrt.write("</th>" + "\n");
			wrt.write("</tr>" + "\n");
			for (int i=0;i<auswertungen.size();i++)
			{
				Auswertung auswertung = auswertungen.get(i);
				Spieler spieler = auswertung.getSpieler();
				double punkte = auswertung.getPunkte();
				double buchholz = auswertung.getBuchholz();
				double sonneberger = auswertung.getSonneberger();
				int anzahlWeiss = auswertung.getAnzahlWeiss();
				int anzahlSchwarz = auswertung.getAnzahlSchwarz();
				wrt.write("<tr>" + "\n");
				wrt.write("<td>" + "\n");
				wrt.write(spieler.getVorname() + " " + spieler.getName() + " " + spieler.getDWZ());
				wrt.write("</td>" + "\n");
				wrt.write("<td>" + "\n");
				wrt.write("" + punkte);
				wrt.write("</td>" + "\n");
				wrt.write("<td>" + "\n");
				wrt.write("" + buchholz);
				wrt.write("</td>" + "\n");
				wrt.write("<td>" + "\n");
				wrt.write("" + sonneberger);
				wrt.write("</td>" + "\n");
				wrt.write("<td>" + "\n");
				wrt.write("" + anzahlWeiss);
				wrt.write("</td>" + "\n");
				wrt.write("<td>" + "\n");
				wrt.write("" + anzahlSchwarz);
				wrt.write("</td>" + "\n");
				wrt.write("</tr>" + "\n");
			}
			wrt.write("</table>" + "\n");
			finish();
		}
		catch (Exception e)
		{
			Protokol.write("SimplePrintToHtml:print:Exception:");
			Protokol.write(e.toString());
		}
		
	}
	private double getPunkte(Spieler spieler,int runde)
	{
		double erg = 0.0;
		if (runde > 0)
		{
			TurnierManager manager = Factory.getTurnierManager();
			ArrayList<Auswertung> auswertungen = manager.getAuswertung(runde - 1);
			for (int i=0;i<auswertungen.size();i++)
			{
				Spieler test = auswertungen.get(i).getSpieler();
				if (test.istGleich(spieler))
				{
					erg += auswertungen.get(i).getPunkte();
				}
			}
		}
		return erg;
	}
}
