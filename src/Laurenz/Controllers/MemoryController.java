package Laurenz.Controllers;

import Laurenz.Models.MemoryTable;
import Laurenz.Views.MainWindow;
import Laurenz.Views.Panels.MemoryPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MemoryController
{

	private MainWindow mw;
	private MemoryPanel memoryPanel;
	private MemoryTable memoryTable;
	private MemoryController memoryController;

	public MemoryController(MainWindow mw)
	{
		this.mw = mw;
		this.memoryPanel = mw.getMemoryPanelView();
		this.memoryTable = memoryPanel.getMemoryTable();

		addListenerToSearch();
	}

	private void addListenerToSearch()
	{
		this.memoryPanel.getJsearchField().addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e)
					{
						String searchQuery 	= memoryPanel.getJsearchField().getText();
						Integer address	 	= Integer.parseInt( searchQuery, 16);
						Double rowIndex 	= address * 1.0 / 8 - 1536;

						if ( rowIndex < 0 || rowIndex % 1 != 0)
						{
							// show error
						}
						else
						{
							JTable table = memoryPanel.getjTable();
							table.scrollRectToVisible( table.getCellRect(rowIndex.intValue(), 1, true) );
						}
					}
				}
		);

	}

	public String getHexWordFromMemory(String baseAddress) {
		Integer address = Integer.parseInt(baseAddress, 16);
		Double rowIndex = address * 1.0 / 8 - 1536;
		System.out.println(address + " " + rowIndex);
		if (rowIndex < 0 || rowIndex % 1 != 0)
		{

			throw new RuntimeException("Invalid memory, Program should stop");
		} else {
			String word = (String) memoryTable.getValueAt(rowIndex.intValue(), 1);
			word = word.substring(word.length() - 8, word.length());
			return word;
		}

	}

	public void storeWordToMemory(String bit32HexValue, String baseAddress)
	{
		Integer address = Integer.parseInt(baseAddress, 16);
		Double rowIndex = address * 1.0 / 8 - 1536;
		if (rowIndex < 0 || rowIndex % 1 != 0)
		{
			throw new RuntimeException("Invalid memory, Program should stop");
		} else {
			memoryTable.setValueAt(bit32HexValue, rowIndex.intValue(), 1);
		}
	}

	public void clearTable()
	{
		memoryPanel.clearTable();
	}
}
