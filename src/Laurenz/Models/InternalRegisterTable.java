package Laurenz.Models;

import Laurenz.Views.MainWindow;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.math.BigInteger;

/**
 * Created by laurenztolentino on 12/01/2016.
 */
public class InternalRegisterTable extends AbstractTableModel
{
	MainWindow mw;
	private InternalRegister ir;

	public InternalRegisterTable(MainWindow mw)
	{
		super();
		this.mw = mw;
		createInternalRegisters();
	}

	public void createInternalRegisters()
	{
		ir = new InternalRegister();
	}

	@Override
	public int getRowCount()
	{
		return ir.getInternalRegisterArray().length;
	}

	@Override
	public int getColumnCount()
	{
		return ir.getInternalRegisterArray()[1].length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		if (ir.getInternalRegisterArray()[rowIndex][columnIndex] == null)
			return "";
		else
			return ir.getInternalRegisterArray()[rowIndex][columnIndex];
	}

	public void setValueAt(Object val, int row, int col)
	{
		String value = (String) val;
		try {
			if (value == null)
				value = "";
			ir.getInternalRegisterArray()[row][col] = value;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(mw.getFrame(), "Invalid Memory Value: " + value + ".");
		}
	}

	public void clearTable() {
		createInternalRegisters();
		this.fireTableDataChanged();
	}

	public InternalRegister getInternalRegisters() {
		return ir;
	}

	public MainWindow getMw() {
		return mw;
	}

	public void setMw(MainWindow mw) {
		this.mw = mw;
	}
}
