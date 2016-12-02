package Laurenz.Views.Panels;

import Laurenz.Views.MainWindow;

import javax.swing.*;
import java.awt.*;

/**
 * Created by laurenztolentino on 11/30/2016.
 */
public class PipelinePanel
{

	/* Main Stuff*/
	MainWindow mw;
	private PipelinePanel pipelinePanel;
	/* JStuff */
	private JPanel jPanel;

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
		jPanel.setBorder( BorderFactory.createTitledBorder("Corgi Panel") );

		// add the image label
		ImageIcon ii = new ImageIcon(this.getClass().getResource(
				"corgi-walk.gif"));
		imageLabel.setIcon(ii);
		jPanel.add(imageLabel, java.awt.BorderLayout.CENTER);
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
