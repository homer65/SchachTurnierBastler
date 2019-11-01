package org.myoggradio.stbko.impl;
import org.myoggradio.stbko.*;
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.myoggradio.stb.*;
public class SimpleKOTurnierMenu extends JDialog implements KOTurnierMenu,ListSelectionListener
{
	private static final long serialVersionUID = 1L;
	private JPanel cpan = new JPanel();
	private JTable table = null;
	private JTable tableGesetzte = null;
	private ArrayList<Spieler> spieler = null;
	private ArrayList<Spieler> gesetzteSpieler = null;
	public SimpleKOTurnierMenu()
	{
		setModal(true);
	}
	public void init()
	{
		cpan = new JPanel();
		cpan.setLayout(new BorderLayout());
		String[] columnNames = new String[4];
		columnNames[0] = "Nummer";
		columnNames[1] = "Vorname";
		columnNames[2] = "Name";
		columnNames[3] = "DWZ";
		String[] columnNamesGesetzte = new String[4];
		columnNamesGesetzte[0] = "Nummer";
		columnNamesGesetzte[1] = "Vorname";
		columnNamesGesetzte[2] = "Name";
		columnNamesGesetzte[3] = "DWZ";
		String[][] rowData = new String[spieler.size()][4];
		String[][] rowDataGesetzte = new String[gesetzteSpieler.size()][4];
		for (int i=0;i<spieler.size();i++)
		{
			Spieler einSpieler = spieler.get(i);
			rowData[i][0] = (i+1) + "";
			rowData[i][1] = einSpieler.getVorname();
			rowData[i][2] = einSpieler.getName();
			rowData[i][3] = einSpieler.getDWZ() + "";
		}
		for (int i=0;i<gesetzteSpieler.size();i++)
		{
			Spieler einSpieler = gesetzteSpieler.get(i);
			rowDataGesetzte[i][0] = (i+1) + "";
			rowDataGesetzte[i][1] = einSpieler.getVorname();
			rowDataGesetzte[i][2] = einSpieler.getName();
			rowDataGesetzte[i][3] = einSpieler.getDWZ() + "";
		}
		table = new JTable(rowData,columnNames);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(this);
		ColumnResizer.resize(table);		
		tableGesetzte = new JTable(rowDataGesetzte,columnNamesGesetzte);
		tableGesetzte.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableGesetzte.getSelectionModel().addListSelectionListener(this);
		ColumnResizer.resize(table);		
		cpan.add(new JScrollPane(table),BorderLayout.WEST);
		cpan.add(new JScrollPane(tableGesetzte),BorderLayout.EAST);
		setContentPane(cpan);
		pack();
	}
	@Override
	public void anzeigen() 
	{
		spieler = KOParameter.spieler;
		gesetzteSpieler = KOParameter.gesetzteSpieler;
		init();
		setVisible(true);
	}
	@Override
	public void valueChanged(ListSelectionEvent lse) 
	{
		System.out.println("SimpleKOTurnierMenu:Event");
		boolean isAdjusting = lse.getValueIsAdjusting();
		if (!isAdjusting)
		{
			Object quelle = lse.getSource();
			if (quelle == table.getSelectionModel())
			{
				System.out.println("SimpleKOTurnierMenu:Event:table");
				int x = table.getSelectedRow();
				Spieler s = spieler.get(x);
				spieler.remove(s);
				gesetzteSpieler.add(s);
				init();
			}
			if (quelle == tableGesetzte.getSelectionModel())
			{
				System.out.println("SimpleKOTurnierMenu:Event:gesetzteTable");
				int x = tableGesetzte.getSelectedRow();
				Spieler s = gesetzteSpieler.get(x);
				gesetzteSpieler.remove(s);
				spieler.add(s);
				init();	
			}
		}
	}
}
