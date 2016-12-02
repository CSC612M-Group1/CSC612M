package Laurenz.Views.Panels;

import Laurenz.GeneralUtility.Print;
import Laurenz.Models.CodeTable;
import Laurenz.Models.Instruction;
import Laurenz.Views.MainWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.ArrayList;

public class CodePanel
{
	/* Main Stuff */
	private MainWindow mw;
	private CodeTable codeTable;
	/* JStuff */
	JPanel jPanel;
	JTable jTable;

	public CodePanel(MainWindow mw)
	{
		this.mw = mw;
		createPanel();
	}

	public void createPanel()
	{
		this.jPanel = new JPanel();
		this.jPanel.setLayout( new BorderLayout() );
		this.jPanel.setBackground(Color.pink);
		this.jPanel.setBorder( BorderFactory.createTitledBorder(" OPCODE + Code Panel "));

		createTable();
	}

	public void createTable()
	{
		JScrollPane jScrollPane;
		TableColumnModel tcm;
		codeTable 	= new CodeTable(mw);
		jTable 		= new JTable(codeTable);
		jScrollPane = new JScrollPane(jTable);
		tcm 		= jTable.getColumnModel();

		tcm.getColumn(0).setPreferredWidth(10);
		tcm.getColumn(1).setPreferredWidth(40);
		tcm.getColumn(2).setPreferredWidth(80);
		tcm.getColumn(2).setPreferredWidth(130);

		jTable.setShowGrid(true);
		jScrollPane.setSize( jPanel.getSize());
		jPanel.add(jScrollPane);

		jTable.setAutoCreateColumnsFromModel(true);

		Print.ln("Created table for CodePanel()");
	}

	public void setCodeValues(ArrayList<Instruction> instructions)
	{
		for( int i = 0; i < instructions.size(); i++)
		{
			Instruction singleInstruction = instructions.get(i);
			codeTable.setInstruction( singleInstruction, i);
		}
		jTable.repaint();
	}

	public void clearTable()
	{
		codeTable.clearTable();
	}

	public MainWindow getMw() {
		return mw;
	}

	public void setMw(MainWindow mw) {
		this.mw = mw;
	}

	public CodeTable getCodeTable() {
		return codeTable;
	}

	public void setCodeTable(CodeTable codeTable) {
		this.codeTable = codeTable;
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
}
