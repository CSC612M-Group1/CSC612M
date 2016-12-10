package Laurenz.Controllers;

import Laurenz.Models.Register;
import Laurenz.Views.MainWindow;
import Laurenz.Views.Panels.RegisterPanel;

import java.util.ArrayList;

public class RegisterController
{
	private MainWindow mw;
	private RegisterController registerController;
	private RegisterPanel registerPanel;

	public RegisterController(MainWindow mw)
	{
		this.mw = mw;
		this.registerController = this;

		registerPanel = mw.getRegisterPanelView();
	}

	public void setValue(String value, int row, int col)
	{
		//
	}

	public void setRegisterValues(ArrayList<Register> registers)
	{
		clearTable();

	}

	/**
	 * Removes values in the register table model
	 */
	public void clearTable()
	{
		registerPanel.getRegisterTable().clearTable();
	}

	public MainWindow getMw() {
		return mw;
	}

	public void setMw(MainWindow mw) {
		this.mw = mw;
	}

	public RegisterController getRegisterController() {
		return registerController;
	}

	public void setRegisterController(RegisterController registerController) {
		this.registerController = registerController;
	}

	public RegisterPanel getRegisterPanel() {
		return registerPanel;
	}

	public void setRegisterPanel(RegisterPanel registerPanel) {
		this.registerPanel = registerPanel;
	}
}
