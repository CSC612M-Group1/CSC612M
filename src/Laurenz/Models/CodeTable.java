package Laurenz.Models;

import Laurenz.GeneralUtility.Print;
import Laurenz.Views.MainWindow;
import org.apache.commons.lang3.StringUtils;

import javax.swing.table.AbstractTableModel;

public class CodeTable extends AbstractTableModel
{
	MainWindow mw;
	private String[][] codes = new String[2048][4];
	private String[] columnNames = {"Memory", "Instruction"};
	public CodeTable(MainWindow mw)
	{
		super();
		this.mw = mw;
		createCodes();

	}

	private void createCodes()
	{
		String hex 	= "";
//		int i 		= 0;
		int memory;

		for( int i = 0; !hex.equals("2FFC"); i++ )
		{
			memory 		= (i + 1024) * 4;
			hex 		= Integer.toHexString(memory).toUpperCase();
			hex 		= StringUtils.leftPad(hex, 4, "0");
			this.codes[i][0] = hex;
			this.codes[i][1] = "00000000";
			this.codes[i][2] = "";
			this.codes[i][3] = "";
		}
	}

	public void setInstruction(Instruction inst, int row)
	{
		String line = inst.getLine().substring( 0, inst.getLine().length() );

		codes[row][1] 	= inst.getOpcode();
		codes[row][2]   = inst.getLabel();
		codes[row][3] 	= line;
	}

	public void setColumnName(int i, String name) {
		columnNames[i] = name;
		fireTableStructureChanged();
	}

	@Override
	public String getColumnName(int index)
	{
		if( index == 0 )
		{
			return "MEMORY";
		}
		else if ( index == 1 )
		{
			return "OPCODE";
		}
		else if ( index == 2 )
		{
			return "LABEL";
		}
		else if ( index == 3 )
		{
			return "INSTRUCTION";
		}
		else {
			return "";
		}
	}

	@Override
	public int getRowCount() {
		return codes.length;
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		return codes[rowIndex][columnIndex];
	}

	@Override
	public boolean isCellEditable(int row, int column)
	{ 	// custom isCellEditable function
		//setCellEditable(row, column, true);
		Print.ln("Clicked Button");
		return true;
	}

	public void setCellEditable(int row, int col, boolean value)
	{
		this.fireTableCellUpdated(row, col);
	}

	public void setValueAt(Object val, int row, int col)
	{
		String value = val.toString();
		codes[row][col] = value;
		this.fireTableCellUpdated(row, col);
	}

	public void clearTable()
	{
		createCodes();
		this.fireTableDataChanged();
	}

	public MainWindow getMw() {
		return mw;
	}

	public void setMw(MainWindow mw) {
		this.mw = mw;
	}
}
