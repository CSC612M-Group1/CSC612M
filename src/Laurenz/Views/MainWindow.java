package Laurenz.Views;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Laurenz.Controllers.InputController;
import Laurenz.Models.Instruction;
import Laurenz.Views.Panels.*;

public class MainWindow
{
	/* Main Variables */
	private MainWindow mw;
	/* JStuff */
	private JFrame mainFrame;
	private JPanel contentPaneUpper;
	private JPanel contentPaneLower;
	private JPanel memoryPanel;
	private JPanel registerPanel;
	private JPanel codePanel;
	private JPanel pipelinePanel;
	private JPanel internalRegisterPanel;
	/* Panel Views */
	MemoryPanel memoryPanelView;
	RegisterPanel registerPanelView;
	CodePanel codePanelView;
	PipelinePanel pipelinePanelView;
	InputPanel inputPanelView;
	InternalRegisterPanel internalRegisterPanelView;
	/* input */
	private JPanel inputPanel;
	/* Controller */
	private InputController inputController;

	public MainWindow()
	{
		startMainWindow();
	}

	public void startMainWindow()
	{
		/* create the panels that will be placed in the frame */
		createRequiredPanels();

		if (mainFrame == null)
		{
			this.createFrame();
		}

		inputController = new InputController(this);
	}

	private void createRequiredPanels()
	{
		/* MemoryPanel */
		memoryPanelView = new MemoryPanel(this);
		registerPanelView = new RegisterPanel(this);
		codePanelView = new CodePanel(this);
		pipelinePanelView = new PipelinePanel(this);
		inputPanelView = new InputPanel(this);
		internalRegisterPanelView = new InternalRegisterPanel(this);
		/* place to global variable */
		memoryPanel 	 = memoryPanelView.getjPanel();
		registerPanel	 = registerPanelView.getjPanel();
		codePanel 		 = codePanelView.getjPanel();
		pipelinePanel 	 = pipelinePanelView.getjPanel();
		inputPanel 		 = inputPanelView.getMainPanel();
		internalRegisterPanel = internalRegisterPanelView.getjPanel();
	}

	private void createFrame()
	{
		/*
		  * Super panel that will contain all panels.
		  * JFrame only allows a single panel to be set inside it. JPanel allows nested JPanels inside.
		 */
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout( new GridLayout(2,1) );

		/* initialize mainFrame settings */
		mainFrame = new JFrame("CSC612M CP2 microMIPS 2016-17 T2 - GROUP 1 - 6:00PM-9:00PM - A1103 - SENSEI ROGER");
		mainFrame.setResizable(true);
		mainFrame.setBackground(Color.pink); // does not work :(
		mainFrame.setSize(Toolkit.getDefaultToolkit().getScreenSize());

		/* Initialize nested JPanels inside mainFrame and mainPanel*/
		contentPaneUpper = new JPanel();
		contentPaneLower = new JPanel();
		contentPaneUpper.setLayout( new GridLayout(1,3) );
		contentPaneLower.setLayout( new GridLayout(1,3) );
		/* render the upper panels */
		contentPaneUpper.add(inputPanel);
		contentPaneUpper.add(pipelinePanel);
		contentPaneUpper.add(internalRegisterPanel);
		/* render the lower panels */
		contentPaneLower.add(registerPanel);
		contentPaneLower.add(memoryPanel);
		contentPaneLower.add(codePanel);
		/* add uper row and lower row of panels into one main panel */
		mainPanel.add( contentPaneUpper );
		mainPanel.add( contentPaneLower );
		/* add the main panel here at the main window (mainFrame) */
		mainFrame.setContentPane( mainPanel );
	}



	public JFrame getFrame()
	{
		return mainFrame;
	}


	public MainWindow getMw() {
		return mw;
	}

	public void setMw(MainWindow mw) {
		this.mw = mw;
	}

	public JFrame getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(JFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public JPanel getContentPaneUpper() {
		return contentPaneUpper;
	}

	public void setContentPaneUpper(JPanel contentPaneUpper) {
		this.contentPaneUpper = contentPaneUpper;
	}

	public JPanel getContentPaneLower() {
		return contentPaneLower;
	}

	public void setContentPaneLower(JPanel contentPaneLower) {
		this.contentPaneLower = contentPaneLower;
	}

	public JPanel getMemoryPanel() {
		return memoryPanel;
	}

	public void setMemoryPanel(JPanel memoryPanel) {
		this.memoryPanel = memoryPanel;
	}

	public JPanel getRegisterPanel() {
		return registerPanel;
	}

	public void setRegisterPanel(JPanel registerPanel) {
		this.registerPanel = registerPanel;
	}

	public JPanel getCodePanel() {
		return codePanel;
	}

	public void setCodePanel(JPanel codePanel) {
		this.codePanel = codePanel;
	}

	public JPanel getPipelinePanel() {
		return pipelinePanel;
	}

	public void setPipelinePanel(JPanel pipelinePanel) {
		this.pipelinePanel = pipelinePanel;
	}

	public JPanel getInternalRegisterPanel() {
		return internalRegisterPanel;
	}

	public void setInternalRegisterPanel(JPanel internalRegisterPanel) {
		this.internalRegisterPanel = internalRegisterPanel;
	}

	public JPanel getInputPanel() {
		return inputPanel;
	}

	public void setInputPanel(JPanel inputPanel) {
		this.inputPanel = inputPanel;
	}

	public MemoryPanel getMemoryPanelView() {
		return memoryPanelView;
	}

	public void setMemoryPanelView(MemoryPanel memoryPanelView) {
		this.memoryPanelView = memoryPanelView;
	}

	public RegisterPanel getRegisterPanelView() {
		return registerPanelView;
	}

	public void setRegisterPanelView(RegisterPanel registerPanelView) {
		this.registerPanelView = registerPanelView;
	}

	public CodePanel getCodePanelView() {
		return codePanelView;
	}

	public void setCodePanelView(CodePanel codePanelView) {
		this.codePanelView = codePanelView;
	}

	public PipelinePanel getPipelinePanelView() {
		return pipelinePanelView;
	}

	public void setPipelinePanelView(PipelinePanel pipelinePanelView) {
		this.pipelinePanelView = pipelinePanelView;
	}

	public InputPanel getInputPanelView() {
		return inputPanelView;
	}

	public void setInputPanelView(InputPanel inputPanelView) {
		this.inputPanelView = inputPanelView;
	}

	public InternalRegisterPanel getInternalRegisterPanelView() {
		return internalRegisterPanelView;
	}

	public void setInternalRegisterPanelView(InternalRegisterPanel internalRegisterPanelView) {
		this.internalRegisterPanelView = internalRegisterPanelView;
	}
}
