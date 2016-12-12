package Laurenz.Controllers;

import Laurenz.GeneralUtility.Print;
import Laurenz.Models.InternalRegister;
import Laurenz.Views.MainWindow;
import Laurenz.Views.Panels.InternalRegisterPanel;

public class InternalRegisterController
{
	private MainWindow mw;
	private InternalRegister internalRegister;
	private InternalRegisterPanel internalRegisterPanel;
	private InternalRegisterController internalRegisterController;

	public InternalRegisterController(MainWindow mw)
	{
		this.mw = mw;
		this.internalRegisterPanel = mw.getInternalRegisterPanelView();
		this.internalRegisterController = this;
		Print.ln("test internal register controller");
	}

	public void clearTable()
	{
		internalRegisterPanel.clearTable();
	}

	public InternalRegister getInternalRegister()
	{
		return internalRegisterPanel.getInternalRegister();
	}

	public void fireDataChanged()
	{
		this.internalRegisterPanel.fireDataChanged();
	}

	public MainWindow getMw() {
		return mw;
	}

	public void setMw(MainWindow mw) {
		this.mw = mw;
	}

	public InternalRegisterPanel getInternalRegisterPanel() {
		return internalRegisterPanel;
	}

	public void setInternalRegisterPanel(InternalRegisterPanel internalRegisterPanel) {
		this.internalRegisterPanel = internalRegisterPanel;
	}

	public InternalRegisterController getInternalRegisterController() {
		return internalRegisterController;
	}

	public void setInternalRegisterController(InternalRegisterController internalRegisterController) {
		this.internalRegisterController = internalRegisterController;
	}

	public void setInternalRegister(InternalRegister internalRegister) {
		this.internalRegister = internalRegister;
	}
}
