package Laurenz.Models;

import Laurenz.GeneralUtility.Print;
import Laurenz.Views.MainWindow;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class PipelineTable extends AbstractTableModel
{
	MainWindow mw;
	String[][] pipelines;
	private int row;
	private int col;

	public PipelineTable(MainWindow mw)
	{
		super();
		this.mw = mw;
		createStartingPipelines();
		Print.ln("Creating PipelineTable");
	}

	public void createStartingPipelines()
	{
		pipelines = new String[1][5];
	}

	public void addInstructionsToPipeline(ArrayList<String> instructions)
	{
		String[][] table = new String[ instructions.size() ][5];
		for( int i = 0; i < instructions.size(); i++ )
		{
			table[i][0] = instructions.get(i);
		}
		pipelines = table;
		this.fireTableDataChanged();
		this.fireTableStructureChanged();
	}

	public void clearTable()
	{
		createStartingPipelines();
		this.fireTableDataChanged();
	}

	@Override
	public int getRowCount()
	{
		return pipelines.length;
	}

	@Override
	public int getColumnCount()
	{
		return pipelines[0].length;
	}

	@Override
	public String getColumnName(int index)
	{
		if ( index == 0 )
		{
			return "Code";
		}
		else
		{
			return "Cycle #" + index;
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		return pipelines[rowIndex][columnIndex];
	}

	@Override
	public void setValueAt(Object value, int row, int col)
	{
		String val 	= value.toString();
		this.col 	= col;
		this.row 	= row;

		if( checkTableSizeChange(row, col) )
		{
			pipelines = addToTable( val );
		}
		else
		{
			pipelines[row][col] = val;
		}


		this.fireTableDataChanged();
		this.fireTableStructureChanged();
	}

	public boolean checkTableSizeChange(int row, int col)
	{
		int willReturn = 0;

		if ( pipelines.length < row + 1)
		{
			this.row++;
			willReturn++;
		}
		if ( pipelines[0].length < col + 1 )
		{
			this.col++;
			willReturn++;
		}

		if( willReturn > 0 )
		{
			return true;
		}
		else {
			return false;
		}
	}

	public String[][] addToTable(String value)
	{
		String[][] newPipelines = new String[this.row][this.col];

		for( int i = 0; i < pipelines.length; i++ )
		{
			for( int j = 0; j < pipelines[0].length; j++ )
			{
				newPipelines[i][j] = pipelines[i][j];
			}
		}
		newPipelines[this.row][this.col] = value;

		return newPipelines;
	}

}
