package Laurenz.Controllers;

import Laurenz.Models.Register;
import Laurenz.Models.RegisterTable;
import Laurenz.Views.MainWindow;
import Laurenz.Views.Panels.RegisterPanel;

import java.util.ArrayList;

public class RegisterController
{
	private MainWindow mw;
	private RegisterController registerController;
	private RegisterPanel registerPanel;
	private RegisterTable registerTable;
	private boolean[][] locks;

	public RegisterController(MainWindow mw)
	{
		this.mw = mw;
		this.registerController = this;
		this.registerPanel 		= mw.getRegisterPanelView();
		this.registerTable 		= this.registerPanel.getRegisterTable();
		createLocks();
	}

	public void setValue(String value, int row, int col)
	{
		this.registerTable.setValueAt(value, row, col);
	}

	public String getValue( int row, int col )
	{
		return registerTable.getValueAt(row, col).toString();
	}

	public void setRegisterValues(ArrayList<Register> registers)
	{
		clearTable();
	}

	private void createLocks()
	{
		this.locks = new boolean[32][2];
		for( int i = 0; i < 32; i++ )
		{
			this.locks[i][0] = false;
			this.locks[i][1] = false;
		}
	}

	public void lock(String rd)
	{
		if (rd != null)
			if ( !rd.substring(1).equals("0") )
			{
				if (rd.substring(0, 1).toLowerCase().equals("r"))
				{
					registerTable.lock( Integer.parseInt(rd.substring(1)) );
				}
			}

	}

	public boolean isLocked(String reg)
	{
		if (reg != null) {
			if (reg.substring(0, 1).toLowerCase().equals("r"))
			{
				return registerTable.isLocked( Integer.parseInt(reg.substring(1)) );
			}
		}
		return false;
	}

	public void unlock(String reg, int cycleNumber) {
		if (reg != null)
			if (!reg.substring(1).equals("0")) {
				if (reg.substring(0, 1).toLowerCase().equals("r")) {
					registerTable.unlock(Integer.parseInt(reg.substring(1)), cycleNumber);
				}
			}
	}

	public int getCycleNumberReleased(String reg)
	{
		if( reg != null )
		{
			if (reg.substring(0, 1).toLowerCase().equals("r"))
			{
				return registerTable.getCycleNumberReleased(Integer.parseInt(reg.substring(1)));
			}
		}
		return -1;
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
