package Laurenz.Views.Panels;

import Laurenz.Models.InternalRegister;
import Laurenz.Models.InternalRegisterTable;
import Laurenz.Views.MainWindow;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableColumnModel;
import java.awt.*;

/**
 * Created by laurenztolentino on 11/30/2016.
 */
public class InternalRegisterPanel
{
	/* Main Stuff*/
	MainWindow mw;
	private InternalRegisterPanel internalRegisterPanel;
	/* JStuff */
	private JPanel jPanel;
	private JTable table;
	private InternalRegisterTable tableModel;

	public InternalRegisterPanel(MainWindow mw)
	{
		this.mw = mw;
		this.createPanel();
	}

	public void createPanel()
	{
		JLabel imageLabel = new JLabel();
		jPanel = new JPanel();
		jPanel.setLayout( new BorderLayout() );
		Border b = BorderFactory.createLineBorder(Color.black);
		TitledBorder title = BorderFactory.createTitledBorder(
				b, "Internal Registers", TitledBorder.CENTER, TitledBorder.TOP);
		jPanel.setBorder(title);


		tableModel = new InternalRegisterTable(mw);
		table = new JTable(tableModel);
		table.setFont(new Font("Courier", Font.PLAIN, 12));
		table.setTableHeader(null);
		// table.setShowGrid(false);
		TableColumnModel tcm = table.getColumnModel();
		tcm.getColumn(0).setPreferredWidth(60);
		tcm.getColumn(1).setPreferredWidth(300);
		tcm.getColumn(2).setPreferredWidth(500);

		JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setSize(jPanel.getSize());
		jPanel.add(scrollPane);
	}

	public MainWindow getMw() {
		return mw;
	}

	public void setMw(MainWindow mw) {
		this.mw = mw;
	}

	public InternalRegister getInternalRegister(){
		return tableModel.getInternalRegisters();
	}

	public void setInternalRegisterPanel(InternalRegisterPanel internalRegisterPanel) {
		this.internalRegisterPanel = internalRegisterPanel;
	}

	public JPanel getjPanel() {
		return jPanel;
	}

	public void setjPanel(JPanel jPanel) {
		this.jPanel = jPanel;
	}
}
