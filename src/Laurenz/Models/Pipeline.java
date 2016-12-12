package Laurenz.Models;

import Laurenz.Views.MainWindow;
import org.apache.commons.lang3.StringUtils;

import javax.swing.table.AbstractTableModel;

public class Pipeline extends AbstractTableModel
{
	MainWindow mw;
	String[][] pipeline;

	public Pipeline(MainWindow mw)
	{
		super();
		this.mw = mw;
		createPipeline();

	}

	private void createPipeline()
	{
		pipeline = new String[1][6];
	}

	@Override
	public String getColumnName(int index)
	{
		if (index == 0)
			return "Instruction";
		else
			return "Cycle " + index;
	}

	@Override
	public int getRowCount() {
		return pipeline.length;
	}

	@Override
	public int getColumnCount() {
		return pipeline[0].length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		return pipeline[rowIndex][columnIndex];
	}

	public void setValueAt(Object val, int row, int col)
	{
		int newRowSize = pipeline.length;
		int newColumnSize = pipeline[0].length;
		boolean hasChanged = false;
		if (row + 1 > newRowSize) {
			newRowSize = row + 1;
			hasChanged = true;
		}
		if (col + 1 > newColumnSize) {
			newColumnSize = col + 1;
			hasChanged = true;
		}

		if (hasChanged == true) {
			String[][] newPipeline = new String[newRowSize][newColumnSize];
			for (int i = 0; i < pipeline.length; i++) {
				for (int j = 0; j < pipeline[0].length; j++) {
					newPipeline[i][j] = pipeline[i][j];
				}
			}
			pipeline = newPipeline;
		}
		pipeline[row][col] = (String) val;

		this.fireTableDataChanged();
		this.fireTableStructureChanged();
	}

	public void addInstructions(String[] inst) {
		String[][] newTable = new String[inst.length][6];
		for (int i = 0; i < inst.length; i++) {
			newTable[i][0] = inst[i];
		}
		pipeline = newTable;
		this.fireTableDataChanged();
		this.fireTableStructureChanged();
	}

	public void clearTable()
	{
		createPipeline();
		this.fireTableDataChanged();
	}

	public MainWindow getMw() {
		return mw;
	}

	public void setMw(MainWindow mw) {
		this.mw = mw;
	}
}
