package Laurenz.Controllers.MIPSModules;

import Laurenz.GeneralUtility.Print;
import Laurenz.Models.Instruction;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class OpcodeMaker
{

	ArrayList<Instruction> instructions;



	public OpcodeMaker()
	{

	}

	public static void opcodeMap()
	{
		HashMap<String, Integer> opcodeMap = new HashMap<>();

 	}

	public ArrayList<Instruction> populateInstructionOpcodes(ArrayList<Instruction> instructions)
	{
		ArrayList<Instruction> updatedInstructions = instructions;
		int opcodeValue, funcCodeValue;
		String command = "", opcode = "";
		Instruction instruction;

		for (int i = 0; i < instructions.size(); i++)
		{
			instruction = updatedInstructions.get( i );
			opcode = setHexOpcode( i, instruction );
			instruction.setOpcode( opcode );
			updatedInstructions.set(i, instruction);
		}

		return updatedInstructions;
	}

	//	"OR", "DSUBU", "SLT", "NOP",
	//			"BNE", "LD", "SD", "SW", "DADDIU",
	//			"J"

	public String setHexOpcode(int i, Instruction instruction)
	{
		String opcode = "______", command, rs = "" ,rd = "", rt = "", imm = "", hexOpcode = "", func = "", instr_index = "";
		int opcodeDecimal = 0;

		command = instruction.getCommand();

		/* list down all opcodes */

		/*
		* please follow the format below in DADDIU
		*/

		/* I TYPE COMMANDS */
		if( command.equalsIgnoreCase( "DADDIU" ) || command.equalsIgnoreCase( "BNE" ) || command.equalsIgnoreCase( "SD" ) || command.equalsIgnoreCase( "LD ") || command.equalsIgnoreCase( "SW" ) )
		{
			if( command.equalsIgnoreCase( "DADDIU" ) )
			{
				opcode  = "011001";
			}
			else if ( command.equalsIgnoreCase( "BNE" ) )
			{
				opcode	= "000101";
			}
			else if ( command.equalsIgnoreCase( "SD" ) )
			{
				opcode 	= "111111";
			}
			else if ( command.equalsIgnoreCase( "SW") )
			{
				opcode  = "101011";
			}
			else if ( command.equalsIgnoreCase( "LD" ) )
			{
				opcode  = "110111";
			}

			rs 		= getRsToBinary( instruction.getRs(), 5 );
			rt 		= "";
			rd 		= getRsToBinary( instruction.getRd(), 5);
			imm		= convertHexToBinary( instruction.getImm(), 16);
		}
		/* R TYPE COMMANDS */
		else if ( command.equalsIgnoreCase( "DSUBU" ) || command.equalsIgnoreCase( "SLT" ) || command.equalsIgnoreCase( "OR" ) )
		{
			if( command.equalsIgnoreCase( "DSUBU" ) )
			{
				func = "101111";
			}
			else if ( command.equalsIgnoreCase( "SLT" ) )
			{
				func = "101010";
			}
			else if ( command.equalsIgnoreCase( "OR" ) )
			{
				func = "100101";
			}
			opcode	= "000000";
			rs 		= getRsToBinary( instruction.getRs(), 5);
			rt 		= getRsToBinary( instruction.getRt(), 5);
			rd 		= getRsToBinary( instruction.getRd(), 5);
			imm 	= "00000";

		}
		/* J TYPE COMMANDS */
		else if ( command.equalsIgnoreCase( "J" ) )
		{
			opcode 	= "000010";
			rs 		= "00000";
			rt 		= "00000";
			imm		= "0000000000000000";
		}
		/* ELSE */
		else
		{

			hexOpcode = "NaN";
			opcode = "";
			rs = "";
			rd = "";
			imm = "";
		}

		hexOpcode = opcode + rs + rt + rd + imm + func;
		Print.ln("OPCODE in Binary: " + opcode + " " + rs + " " + rt + " " + rd + " " + imm + " " +  func);
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
