package Laurenz.Views.Panels;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;

import Laurenz.GeneralUtility.*;
import Laurenz.Models.MemoryTable;
import Laurenz.Views.MainWindow;

/**
 * Created by laurenztolentino on 11/30/2016.
 */
public class MemoryPanel
{
	private MainWindow mw;
	private MemoryPanel memoryPanel;
	private MemoryTable memoryTable;

	/* JStuff*/
	private JPanel jPanel;
	private JTable jTable;
	private JTextField jsearchField;

	public MemoryPanel(MainWindow mw)
	{
		this.mw = mw;

		this.createPanel();
	}

	private void createPanel()
	{
		jPanel = new JPanel();
		jPanel.setLayout( new BorderLayout() );
		jPanel.setBorder( BorderFactory.createTitledBorder("Memory Panel") );

		createTable();
	}

	public void createTable()
	{
		TableColumnModel tcm;
		JScrollPane jScrollPane;

		memoryTable 	= new MemoryTable(this.mw);
		jTable = new JTable(memoryTable);
		tcm 			= jTable.getColumnModel();
		jsearchField	= new JTextField();
		jScrollPane		= new JScrollPane(jTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		tcm.getColumn(0).setPreferredWidth(40);
		tcm.getColumn(1).setPreferredWidth(200);
		jScrollPane.setSize(this.jPanel.getWidth(), this.jPanel.getHeight());
		jsearchField.setSize(80,40);

		jPanel.add(jScrollPane, BorderLayout.CENTER);
		jPanel.add(jsearchField, BorderLayout.SOUTH);
		Print.ln("Created table for MemoryPanel() ");
	}



	public void clearTable()
	{
		this.memoryTable.clearTable();
	}

	public MainWindow getMw() {
		return mw;
	}

	public void setMw(MainWindow mw) {
		this.mw = mw;
	}

	public MemoryPanel getMemoryPanel() {
		return memoryPanel;
	}

	public void setMemoryPanel(MemoryPanel memoryPanel) {
		this.memoryPanel = memoryPanel;
	}

	public MemoryTable getMemoryTable() {
		return memoryTable;
	}

	public void setMemoryTable(MemoryTable memoryTable) {
		this.memoryTable = memoryTable;
	}

	public JPanel getjPanel() {
		return jPanel;
	}

	public void setjPanel(JPanel jPanel) {
		this.jPanel = jPanel;
	}

	public JTable getjTable() {
		return jTable;
	}

	public void setjTable(JTable jTable) {
		this.jTable = jTable;
	}

	public JTextField getJsearchField() {
		return jsearchField;
	}

	public void setJsearchField(JTextField jsearchField) {
		this.jsearchField = jsearchField;
	}
}
