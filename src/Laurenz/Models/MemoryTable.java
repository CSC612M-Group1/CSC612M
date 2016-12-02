package Laurenz.Models;

import Laurenz.GeneralUtility.Print;
import Laurenz.Views.MainWindow;
import java.awt.*;
import java.awt.event.KeyEvent;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.math.BigInteger;
import javax.swing.table.AbstractTableModel;

/**
 * Created by laurenztolentino on 11/30/2016.
 */
public class MemoryTable extends AbstractTableModel
{
	MainWindow mw;
	private String[][] memory = new String[1024][2];


	public MemoryTable(MainWindow mw)
	{
		Print.ln("MemoryTable was created");
		this.mw = mw;
		createMemory();
	}

	public void createMemory()
	{
		String hex = "";
		int memoryInt;

		for(int i = 0; !hex.equals("4FF8"); i++ )
		{
			memoryInt 	 = ( i + 1536) * 8;
			hex 		 = Integer.toHexString(memoryInt).toUpperCase();
			hex 		 = StringUtils.leftPad(hex, 4, "0");

			this.memory[i][0] = hex;
			this.memory[i][1] = "0000000000000000";
		}

		// this.memory = memory;

	}

	public String[][] getMemory()
	{
		return memory;
	}

	@Override
	public int getRowCount() {
		return memory.length;
	}

	@Override
	public int getColumnCount() {
		return memory[0].length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return memory[rowIndex][columnIndex];
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		if (col == 1 || col == 3)
			return true;
		return false;
	}

	@Override
	public void setValueAt(Object value, int row, int col)
	{
		String val = (String) value;
		try
		{
			if (val.length() <= 16) {
				val = StringUtils.leftPad(val, 16, "0");

				new BigInteger(val, 16);
				memory[row][col] = val;
				this.fireTableCellUpdated(row, col);
			} else if (val.length() > 16)
				throw new Exception();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(mw.getFrame(), val + " is an invalid memory value!");
		}
	}

	@Override
	public String getColumnName(int index)
	{
		if( index == 0 )
		{
			return "ADDRESS";
		}
		else if ( index == 1 )
		{
			return "VALUE";
		}
		else {
			return "";
		}
	}

	public void clearTable() {
		createMemory();
		this.fireTableDataChanged();
	}
}
