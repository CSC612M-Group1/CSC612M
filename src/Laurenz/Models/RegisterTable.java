package Laurenz.Models;

import Laurenz.Views.MainWindow;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.math.BigInteger;

/**
 * Created by laurenztolentino on 12/01/2016.
 */
public class RegisterTable extends AbstractTableModel
{
	MainWindow mw;
	private String[][] registersString;
	private Register[] registers;

	public RegisterTable(MainWindow mw)
	{
		super();
		this.mw = mw;
		createRegisters();
	}

	public void createRegisters()
	{
		String[][] registersString  = new String[32][2];
		Register[] registers 			= new Register[32];

		for( int i = 0; i < 32; i++)
		{
			Register singleRegister = new Register(i, "0000000000000000");
			registers[i] 		  = singleRegister;
			registersString[i][0] = singleRegister.getR();
			registersString[i][1]  = singleRegister.getValue();
		}

		this.registersString = registersString;
		this.registers 		 = registers;
	}


	@Override
	public int getRowCount()
	{
		return registersString.length;
	}

	@Override
	public int getColumnCount()
	{
		return 2;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		return registersString[rowIndex][columnIndex];
	}

	public void setValueAt(Object val, int row, int col)
	{
		String value = val.toString();
		value 		 = StringUtils.leftPad(value, 16, "0");

		/* Must not edit R0 */
		if( row == 0)
		{
			// warning message
			JOptionPane.showMessageDialog(mw.getFrame(), "Don't touch that :( ");
		}
		else if( col == 0 )
		{
			// warning message
			JOptionPane.showMessageDialog(mw.getFrame(), "Don't touch that :( ");
		}
		/* Input must not exceed 16 characters */
		else if ( value.length() > 16 )
		{
			// warning message
			JOptionPane.showMessageDialog(mw.getFrame(), "Exceeded 18 characters for register value");
		}
		else if ( value.length() <= 16 && col == 1 && row > 0)
		{
			try
			{
				BigInteger bigint = new BigInteger(value, 16); // if it creates an error, launch pop-up
				registersString[row][col] = value;
				this.fireTableCellUpdated(row, 1);
			}
			catch ( Exception e )
			{
				JOptionPane.showMessageDialog(mw.getFrame(), "Something's wrong with: " + value);
			}
		}
	}

	@Override
	public String getColumnName(int index)
	{
		if( index == 0 )
		{
			return "REGISTER";
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
		createRegisters();
		this.fireTableDataChanged();
	}

	public MainWindow getMw() {
		return mw;
	}

	public void setMw(MainWindow mw) {
		this.mw = mw;
	}
}
