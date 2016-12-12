package Laurenz.Pau;

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
		String opcode, command, rs ,rd, rt, imm, offset, base, func, hexOpcode;
		String fiveZeroes = "00000";
		int opcodeDecimal = 0;
		int funcDecimal = 0;

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
			rt 		= getRsToBinary( instruction.getRt(), 5);
			imm		= convertHexToBinary( instruction.getImm(), 16);

			hexOpcode = opcode + rs + rt + imm;

		}
		else if( command.equalsIgnoreCase("DSUBU") )
		{
			opcodeDecimal = 0;
			opcode 	= StringUtils.leftPad( Integer.toBinaryString(opcodeDecimal), 6, "0");
			rs 		= getRsToBinary( instruction.getRs(), 5 );
			rt 		= getRsToBinary( instruction.getRt(), 5);
			rd 		= getRsToBinary( instruction.getRd(), 5);
			funcDecimal = 47;
			func	= StringUtils.leftPad( Integer.toBinaryString(funcDecimal), 6, "0");

			hexOpcode = opcode + rs + rt + rd + fiveZeroes + func;

		}
		else if( command.equalsIgnoreCase("OR") )
		{
			opcodeDecimal = 0;
			opcode 	= StringUtils.leftPad( Integer.toBinaryString(opcodeDecimal), 6, "0");
			rs 		= getRsToBinary( instruction.getRs(), 5 );
			rt 		= getRsToBinary( instruction.getRt(), 5);
			rd 		= getRsToBinary( instruction.getRd(), 5);
			funcDecimal = 37;
			func	= StringUtils.leftPad( Integer.toBinaryString(funcDecimal), 6, "0");

			hexOpcode = opcode + rs + rt + rd + fiveZeroes + func;

		}
		else if( command.equalsIgnoreCase("SLT") )
		{
			opcodeDecimal = 25;
			opcode 	= StringUtils.leftPad( Integer.toBinaryString(opcodeDecimal), 6, "0");
			rs 		= getRsToBinary( instruction.getRs(), 5 );
			rt 		= getRsToBinary( instruction.getRt(), 5);
			rd 		= getRsToBinary( instruction.getRd(), 5);
			funcDecimal = 42;
			func	= StringUtils.leftPad( Integer.toBinaryString(funcDecimal), 6, "0");

			hexOpcode = opcode + rs + rt + rd + fiveZeroes + func;

		}
		else if( command.equalsIgnoreCase("LD") )
		{
			opcodeDecimal = 55;
			opcode 	= StringUtils.leftPad( Integer.toBinaryString(opcodeDecimal), 6, "0");
			base 	= getRsToBinary( instruction.getBase(), 5 );
			rt 		= getRsToBinary( instruction.getRt(), 5);
			offset	= convertHexToBinary( instruction.getOffset(), 16);

			hexOpcode = opcode + base + rt + offset;

		}
		else if( command.equalsIgnoreCase("SD") )
		{
			opcodeDecimal = 63;
			opcode 	= StringUtils.leftPad( Integer.toBinaryString(opcodeDecimal), 6, "0");
			base 	= getRsToBinary( instruction.getBase(), 5 );
			rt 		= getRsToBinary( instruction.getRt(), 5);
			offset	= convertHexToBinary( instruction.getOffset(), 16);

			hexOpcode = opcode + base + rt + offset;

		}
		else if( command.equalsIgnoreCase("BNE") )
		{
			opcodeDecimal = 5;
			opcode 	= StringUtils.leftPad( Integer.toBinaryString(opcodeDecimal), 6, "0");
			rs 		= getRsToBinary( instruction.getRs(), 5 );
			rt 		= getRsToBinary( instruction.getRt(), 5);
			offset	= convertHexToBinary( instruction.getImm(), 16);

			hexOpcode = opcode + rs + rt + offset;

		}
		else if( command.equalsIgnoreCase("J") )
		{
			opcodeDecimal = 2;
			opcode 	= StringUtils.leftPad( Integer.toBinaryString(opcodeDecimal), 6, "0");
			//instr_index
			offset	= StringUtils.leftPad( instruction.getOffset(), 26, "0");

			hexOpcode = opcode + offset;

		}
		else if( command.equalsIgnoreCase("NOP") )
		{
			opcodeDecimal = 0;
			opcode 	= StringUtils.leftPad( Integer.toBinaryString(opcodeDecimal), 32, "0");

			hexOpcode = opcode;

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
