package Laurenz;

/**
 * Created by laurenztolentino on 11/30/2016.
 */

import Laurenz.Controllers.InputController;
import Laurenz.GeneralUtility.Print;
import Laurenz.Views.MainWindow;

import javax.swing.*;

public class PushToStart
{
	public static void main(String[] args)
	{
		Print.ln("Launching app");
		MainWindow mw = new MainWindow();
		InputController inputController = new InputController(mw);
		JFrame frame;

		frame = mw.getFrame();

		frame.setVisible(true);
	}
}
