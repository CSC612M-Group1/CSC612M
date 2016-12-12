package Laurenz.Controllers;

import Laurenz.Controllers.MIPSModules.PipelineMaster;
import Laurenz.Models.Instruction;
import Laurenz.Models.InternalRegister;
import Laurenz.Views.MainWindow;
import Laurenz.Views.Panels.InputPanel;
import Laurenz.Views.Panels.PipelinePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PipelineController
{

	private MainWindow mw;
	private PipelinePanel pipelinePanel;
	private PipelineMaster pipelineMaster;
	private PipelineController pipelineController;
	private InternalRegister internalRegister;
	private InternalRegisterController internalRegisterController;

	private InputPanel inputPanel;

	private String[] instructionsS;

	public PipelineController(MainWindow mw)
	{
		this.mw 				= mw;
		this.pipelinePanel 		= mw.getPipelinePanelView();
		this.pipelineController = this;


		this.internalRegister			= new InternalRegister();
		this.internalRegisterController = mw.getInternalRegisterController();

		this.internalRegisterController.setInternalRegister( this.internalRegister );

		this.pipelineMaster		= new PipelineMaster( this.mw, this.internalRegister );

		inputPanel = mw.getInputPanelView();

		addButtonListeners();
	}

	public void setInstructionsValues(ArrayList<Instruction> instructionsList)
	{
		ArrayList<String> instructions = new ArrayList<>();

		for( int i = 0; i < instructionsList.size(); i++ )
		{
			instructions.add( i, instructionsList.get(i).getLine() );
		}
		this.pipelinePanel.addInstructions( instructions );
		this.pipelineMaster.setInstructionList( instructionsList );
	}

	public void setMapValue(String value, int lineNum, int cycle)
	{
		pipelinePanel.addCycleValue(value, lineNum, cycle);
	}

	public void singleRun()
	{
		pipelineMaster.singleRun();
		internalRegisterController.fireDataChanged();
	}

	public void fullRun()
	{
		pipelineMaster.fullRun();
		internalRegisterController.fireDataChanged();
	}

	public void addButtonListeners()
	{
		inputPanel.getSingleBtn().addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						singleRun();
					}
				}
		);

		inputPanel.getFullBtn().addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						fullRun();
					}
				}
		);
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

	public PipelineMaster getPipelineMaster() {
		return pipelineMaster;
	}

	public void setPipelineMaster(PipelineMaster pipelineMaster) {
		this.pipelineMaster = pipelineMaster;
	}

	public PipelineController getPipelineController() {
		return pipelineController;
	}

	public void setPipelineController(PipelineController pipelineController) {
		this.pipelineController = pipelineController;
	}

	public InternalRegister getInternalRegister() {
		return internalRegister;
	}

	public void setInternalRegister(InternalRegister internalRegister) {
		this.internalRegister = internalRegister;
	}

	public InternalRegisterController getInternalRegisterController() {
		return internalRegisterController;
	}

	public void setInternalRegisterController(InternalRegisterController internalRegisterController) {
		this.internalRegisterController = internalRegisterController;
	}

	public String[] getInstructionsS() {
		return instructionsS;
	}

	public void setInstructionsS(String[] instructionsS) {
		this.instructionsS = instructionsS;
	}


}
