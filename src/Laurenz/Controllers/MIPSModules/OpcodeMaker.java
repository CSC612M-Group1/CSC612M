package Laurenz.Controllers.MIPSModules;

import Laurenz.GeneralUtility.Print;
import Laurenz.Models.Instruction;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.util.ArrayList;

public class OpcodeMaker
{

	ArrayList<Instruction> instructions;

	public OpcodeMaker()
	{

	}

	public ArrayList<Instruction> populateInstructionOpcodes(ArrayList<Instruction> instructions)
	{
		ArrayList<Instruction> updatedInstructions = instructions;
		int opcodeValue, funcCodeValue;
		String command = "", opcode = "";
		Instruction instruction;

		for (int i = 0; i < instructions.size(); i++)
		{
			instruction = updatedInstructions.get(i);
			opcode = setHexOpcode(i, instruction);
			instruction.setOpcode( opcode );
			updatedInstructions.set(i, instruction);
		}

		return updatedInstructions;
	}

	public String setHexOpcode(int i, Instruction instruction)
	{
		String opcode, command, rs ,rd, rt, imm, hexOpcode;
		int opcodeDecimal = 0;

		command = instruction.getCommand();

		/* list down all opcodes */
		/*
		* please follow the format below in DADDIU
		* */
		if( command.equalsIgnoreCase("DADDIU") )
		{
			opcodeDecimal = 25;
			opcode 	= StringUtils.leftPad( Integer.toBinaryString(opcodeDecimal), 6, "0");
			rs 		= getRsToBinary( instruction.getRs(), 5 );
			rd 		= getRsToBinary( instruction.getRd(), 5);
			imm		= convertHexToBinary( instruction.getImm(), 16);

			hexOpcode = opcode + rs + rd + imm;

		}
		else
		{
			hexOpcode = "NaN";
		}

		hexOpcode = convertBinaryToHex(hexOpcode);
		hexOpcode = hexOpcode.toUpperCase();
		Print.ln("OPCODE in Hex: " + hexOpcode);
		return hexOpcode;
	}

	private String getRsToBinary(String R, int length)
	{
		String reg  = R.substring(1);
		String temp = Integer.toBinaryString( Integer.parseInt(reg) );
		return StringUtils.leftPad(temp, length, "0");
	}

	public String convertBinaryToHex(String opBinary)
	{
		String opHex;
		BigInteger bigInt;
		bigInt 	= new BigInteger(opBinary, 2);
		opHex 	= bigInt.toString(16);
		opHex  	= StringUtils.leftPad(opHex, 8, "0");

		return opHex;
	}

	public String convertHexToBinary(String hex, int length)
	{
		String result;
		result = Integer.toBinaryString( Integer.parseInt(hex, 16) );
		result = StringUtils.leftPad(result, length, "0");
		return result;
	}
}
