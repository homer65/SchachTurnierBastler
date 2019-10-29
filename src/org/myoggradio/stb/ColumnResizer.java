package org.myoggradio.stb;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
public class ColumnResizer 
{
	public static void resize(JTable table)
	{
		TableColumnModel columnModel = table.getColumnModel();
		for (int col=0;col<table.getColumnCount();col++)
		{
			int maxwidth = 0;
			for (int row=0;row<table.getRowCount();row++)
			{
				TableCellRenderer rend = table.getCellRenderer(row,col);
				Object value = table.getValueAt(row,col);
				Component comp = rend.getTableCellRendererComponent(table,value,false,false,row,col);
				maxwidth = Math.max(comp.getPreferredSize().width,maxwidth);
			}
			TableColumn column = columnModel.getColumn(col);
			column.setPreferredWidth(maxwidth);
		}
	}
}
