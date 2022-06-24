package org.myoggradio.stb.impl;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.myoggradio.stb.ColumnResizer;
import org.myoggradio.stb.Factory;
import org.myoggradio.stb.NumberComparator;
import org.myoggradio.stb.Parameter;
import org.myoggradio.stb.Partie;
import org.myoggradio.stb.Runde;
import org.myoggradio.stb.RundeManuellMenu;
import org.myoggradio.stb.Spieler;
public class SimpleRundeManuellMenu extends JDialog implements RundeManuellMenu, ListSelectionListener, ActionListener
{
	private static final long serialVersionUID = 1L;
	private JTable table = null;
	private JTable tableGesetzte = null;
	private JPanel cpan = null;
	private JPanel bpan = null;
	private JButton butt1 = null;
	private Runde runde = null;
	private ArrayList<Spieler> spieler = new ArrayList<Spieler>();
	private ArrayList<Spieler> gesetzteSpieler = new ArrayList<Spieler>();
	public SimpleRundeManuellMenu()
	{
		setModal(true);
	}
	@Override
	public void anzeigen() 
	{
		ArrayList<Spieler> spielerList = Parameter.turnier.getSpieler();
		for (int i=0;i<spielerList.size();i++)
		{
			Spieler s = spielerList.get(i);
			spieler.add(s);
		}
		init();
	}
	@Override
	public void setRunde(Runde runde) 
	{
		this.runde = runde;		
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
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>();
		sorter.setModel(table.getModel());
		sorter.setComparator(0, new NumberComparator());
		sorter.setComparator(3, new NumberComparator());
		table.setRowSorter(sorter);
		ColumnResizer.resize(table);		
		tableGesetzte = new JTable(rowDataGesetzte,columnNamesGesetzte);
		tableGesetzte.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableGesetzte.getSelectionModel().addListSelectionListener(this);
		ColumnResizer.resize(table);		
		cpan.add(new JScrollPane(table),BorderLayout.CENTER);
		cpan.add(new JScrollPane(tableGesetzte),BorderLayout.EAST);
		buildbpan();
		cpan.add(bpan,BorderLayout.SOUTH);
		setContentPane(cpan);
		pack();
		setVisible(true);
	}
	private void buildbpan()
	{
		bpan = new JPanel();
		bpan.setLayout(new GridLayout(1,1));
		butt1 = new JButton("create Runde");
		bpan.add(butt1);
		butt1.addActionListener(this);
	}
	@Override
	public void valueChanged(ListSelectionEvent lse) 
	{
		boolean isAdjusting = lse.getValueIsAdjusting();
		if (!isAdjusting)
		{
			Object quelle = lse.getSource();
			if (quelle == table.getSelectionModel())
			{
				int x = table.getSelectedRow();
				x = table.getRowSorter().convertRowIndexToModel(x);
				Spieler s = spieler.get(x);
				spieler.remove(s);
				gesetzteSpieler.add(s);
				init();
			}
			if (quelle == tableGesetzte.getSelectionModel())
			{
				int x = tableGesetzte.getSelectedRow();
				Spieler s = gesetzteSpieler.get(x);
				gesetzteSpieler.remove(s);
				spieler.add(s);
				init();
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		Object quelle = ae.getSource();
		if (quelle == butt1)
		{
			if (spieler.size() > 0)
			{
				JOptionPane.showMessageDialog(null,"Noch nicht alle Spieler gesetzt","Fehler",JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				Partie partie = Factory.getPartie();
				for (int i=0;i<gesetzteSpieler.size();i++)
				{
					if (istGerade(i))
					{
						partie.setWeiss(gesetzteSpieler.get(i));
					}
					else
					{
						partie.setSchwarz(gesetzteSpieler.get(i));
						runde.setPartie(partie,i/2);
						partie = Factory.getPartie();
					}	
				}
				if (!istGerade(gesetzteSpieler.size()))
				{
					runde.removeFreilos();
					runde.addFreilos(gesetzteSpieler.get(gesetzteSpieler.size()-1));
				}
				dispose();
			}
		}
	}
	private boolean istGerade(int i)
	{
		boolean erg = false;
		int ih = i / 2;
		if (2*ih == i) erg = true;
		return erg;
	}
}
