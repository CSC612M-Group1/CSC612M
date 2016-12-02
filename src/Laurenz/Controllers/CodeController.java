package Laurenz.Controllers;

import Laurenz.Models.Instruction;
import Laurenz.Views.MainWindow;
import Laurenz.Views.Panels.CodePanel;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.util.ArrayList;

public class CodeController
{

	MainWindow mw;
	private CodeController codeController;
	private CodePanel codePanel;


	public CodeController(MainWindow mw)
	{
		this.mw = mw;
		this.codeController = this;

		codePanel = mw.getCodePanelView();
	}

	public void setCodeValues(ArrayList<Instruction> instructions)
	{
		codePanel.clearTable();
		codePanel.setCodeValues(instructions);
	}

	public CodeController getCodeController() {
		return codeController;
	}

	public void setCodeController(CodeController codeController) {
		this.codeController = codeController;
	}



}
