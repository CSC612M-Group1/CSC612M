package Laurenz.Views.Panels;

import Laurenz.Models.Instruction;
import Laurenz.Models.PipelineTable;
import Laurenz.Views.MainWindow;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by laurenztolentino on 11/30/2016.
 */
public class PipelinePanel
{

	/* Main Stuff */
	MainWindow mw;
	private PipelinePanel pipelinePanel;
	private PipelineTable pipelineModelTable;

	/* JStuff */
	private JPanel jPanel;
	private JTable jTable;
	private JScrollPane jScrollPane;

	public PipelinePanel(MainWindow mw)
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
				b, "Pipeline Map", TitledBorder.CENTER, TitledBorder.TOP);
		jPanel.setBorder(title);

		pipelineModelTable = new PipelineTable(mw);
		jTable = new JTable(pipelineModelTable);

		TableColumnModel tcm = jTable.getColumnModel();

		jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jScrollPane = new JScrollPane(jTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jScrollPane.setSize(jPanel.getSize());
		jPanel.add(jScrollPane);
	}

	public void addInstructions(ArrayList<String> inst)
	{
		pipelineModelTable = (PipelineTable) jTable.getModel();
		pipelineModelTable.addInstructionsToPipeline(inst);
	}

	public void addCycleValue(String val, int lineNumber, int cycleNumber) {
		pipelineModelTable.setValueAt(val, lineNumber, cycleNumber);
		jTable.scrollRectToVisible(jTable.getCellRect(jTable.getRowCount() - 1, jTable.getColumnCount(), true));
	}

	public MainWindow getMw() {
		return mw;
	}

	public void setMw(MainWindow mw) {
		this.mw = mw;
	}

	public PipelinePanel getPipelinePanel() {
		return pipelinePanel;
	}

	public void setPipelinePanel(PipelinePanel pipelinePanel) {
		this.pipelinePanel = pipelinePanel;
	}

	public JPanel getjPanel() {
		return jPanel;
	}

	public void setjPanel(JPanel jPanel) {
		this.jPanel = jPanel;
	}
}
