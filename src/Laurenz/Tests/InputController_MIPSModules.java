package Laurenz.Tests;

import Laurenz.Controllers.MIPSModules.OpcodeMaker;
import Laurenz.Controllers.MIPSModules.Parser;
import Laurenz.GeneralUtility.Print;
import Laurenz.Models.Instruction;
import Laurenz.Views.MainWindow;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

public class InputController_MIPSModules
{

	private MainWindow mw;
	private OpcodeMaker opcodeMaker;
	private Parser parser;

	public InputController_MIPSModules()
	{
		mw = new MainWindow();
		opcodeMaker = new OpcodeMaker();
		parser = new Parser();
	}

	public static void main(String[] args)
	{
		InputController_MIPSModules ipc = new InputController_MIPSModules();
		ipc.testConversion();
//		ipc.testHexToBinary("#1111", 16);
	}

	public void testConversion()
	{
		String sampleInput = "DADDIU R2, R0, #1111";
		ArrayList<Instruction> ins;

		ins =	parser.parse(sampleInput);

		if( ins.size() >= 1 )
		{
			ins = opcodeMaker.populateInstructionOpcodes(ins);
		}


	}

	public void testHexToBinary(String hex, int length)
	{
		hex = hex.substring(1);
		String test = opcodeMaker.convertHexToBinary(hex, 16);
		Print.ln("IMM = " + test);
	}


}
