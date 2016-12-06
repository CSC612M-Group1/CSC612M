package Laurenz.Views.Panels;

import Laurenz.Views.MainWindow;

import javax.swing.*;
import java.awt.*;

import static javax.swing.BorderFactory.*;

/* TEST LAURENZ WAS HERE */

public class InputPanel
{
	/* Main Stuff */
	MainWindow mw;
	InputPanel inputPanel;
	/* Jstuff */
	JPanel txtAreaPanel;
	JPanel btnPanel;
	JPanel mainPanel;
	JButton loadBtn;
	JButton singleBtn;
	JButton fullBtn;
	JTextArea inputArea;
	/* Text box content */
	String inputText;

	public InputPanel(MainWindow mw)
	{
		this.mw = mw;
		this.createPanel("");
	}

	public void createPanel(String input)
	{
		JScrollPane jScrollPane;

		txtAreaPanel = new JPanel();
		btnPanel 	 = new JPanel();
		mainPanel 	 = new JPanel();
		inputArea 	 = new JTextArea();
		jScrollPane  = new JScrollPane(inputArea);
		/* Buttons */
		loadBtn 	 = new JButton("Load Code Above");
		singleBtn 	 = new JButton("Single Execution");
		fullBtn 	 = new JButton("Full Execution");
		/* Set Text */
		inputArea.setText(input);
		/* Panels */
		txtAreaPanel.setLayout( new GridLayout(1,1) );
		mainPanel.setLayout( new GridLayout(2, 1) );
		mainPanel.setBorder( createTitledBorder("INPUT"));
		btnPanel.setLayout( new GridLayout(1, 3) );
		/* Panel Settings */
		txtAreaPanel.setPreferredSize(new Dimension(200, 200));
		btnPanel.setPreferredSize(new Dimension(100, 40));
		/* add panels */
		txtAreaPanel.add(jScrollPane);
		btnPanel.add(loadBtn);
		btnPanel.add(singleBtn);
		btnPanel.add(fullBtn);
		mainPanel.add(txtAreaPanel);
		mainPanel.add(btnPanel);
	}

	public MainWindow getMw() {
		return mw;
	}

	public void setMw(MainWindow mw) {
		this.mw = mw;
	}

	public InputPanel getInputPanel() {
		return inputPanel;
	}

	public void setInputPanel(InputPanel inputPanel) {
		this.inputPanel = inputPanel;
	}

	public JPanel getTxtAreaPanel() {
		return txtAreaPanel;
	}

	public void setTxtAreaPanel(JPanel txtAreaPanel) {
		this.txtAreaPanel = txtAreaPanel;
	}

	public JTextArea getInputArea() {
		return inputArea;
	}

	public void setInputArea(JTextArea inputArea) {
		this.inputArea = inputArea;
	}

	public JPanel getBtnPanel() {
		return btnPanel;
	}

	public void setBtnPanel(JPanel btnPanel) {
		this.btnPanel = btnPanel;
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

	public void setMainPanel(JPanel mainPanel) {
		this.mainPanel = mainPanel;
	}

	public String getInputText()
	{
		this.inputText = getInputArea().getText();
		return inputText;
	}

	public void setInputText(String inputText) {
		this.inputText = inputText;
	}

	public JButton getLoadBtn() {
		return loadBtn;
	}

	public void setLoadBtn(JButton loadBtn) {
		this.loadBtn = loadBtn;
	}

	public JButton getSingleBtn() {
		return singleBtn;
	}

	public void setSingleBtn(JButton singleBtn) {
		this.singleBtn = singleBtn;
	}

	public JButton getFullBtn() {
		return fullBtn;
	}

	public void setFullBtn(JButton fullBtn) {
		this.fullBtn = fullBtn;
	}


}
