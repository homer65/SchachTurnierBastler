package org.myoggradio.stb.impl;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.myoggradio.stb.*;
public class SimpleAuswertungDialog extends JDialog implements AuswertungDialog, ListSelectionListener
{
	private static final long serialVersionUID = 1L;
	private int rundeNummer = 0;
	private ArrayList<Auswertung> ausw = null;
	private JTable table = null;
	public SimpleAuswertungDialog()
	{
		setModal(true);
	}
	@Override
	public void setRunde(int n) 
	{
		this.rundeNummer = n;
		TurnierManager manager = Factory.getTurnierManager();
		ausw = manager.getAuswertung(rundeNummer);
		Collections.sort(ausw,new AuswertungComparator());
	}
	@Override
	public void anzeigen()
	{
		JPanel cpan = new JPanel();
		cpan.setLayout(new FlowLayout());
		String[] columnNames = new String[7];
		columnNames[0] = "Platzierung";
		columnNames[1] = "Spieler";
		columnNames[2] = "Punkte";
		columnNames[3] = "Buchholz";
		columnNames[4] = "DWZ";
		columnNames[5] = "Anzahl Weiss";
		columnNames[6] = "Anzahl Schwarz";
		String[][] rowData = new String[ausw.size()][7];
		for (int i=0;i<ausw.size();i++)
		{
			Auswertung auswertung = ausw.get(i);
			Spieler spieler = auswertung.getSpieler();
			rowData[i][0] = (i+1) + "";
			rowData[i][1] = spieler.getVorname() + " " + spieler.getName();
			rowData[i][2] = auswertung.getPunkte() + "";
			rowData[i][3] = auswertung.getBuchholz() + "";
			rowData[i][4] = spieler.getDWZ() + "";
			rowData[i][5] = auswertung.getAnzahlWeiss() + "";
			rowData[i][6] = auswertung.getAnzahlSchwarz() + "";
		}
		table = new JTable(rowData,columnNames);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(this);
		JScrollPane scrpan = new JScrollPane(table);
		scrpan.setPreferredSize(new Dimension(Parameter.scrwidth,Parameter.scrheight));
		cpan.add(scrpan);
		setContentPane(cpan);
		pack();
		setVisible(true);
	}
	@Override
	public void valueChanged(ListSelectionEvent lse) 
	{
		boolean isAdjusting = lse.getValueIsAdjusting();
		if (!isAdjusting)
		{
			int x = table.getSelectedRow();
			Spieler s = ausw.get(x).getSpieler();
			SpielerAuswertungDialog nsm = Factory.getSpielerAuswertungDialog();
			nsm.setRunde(rundeNummer);
			nsm.setSpieler(s);
			nsm.anzeigen();
		}
	}
}
