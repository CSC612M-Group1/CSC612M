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

//  "OR", "DSUBU", "SLT", "NOP",
//  "BNE", "LD", "SD", "SW", "DADDIU",
//  "J"

    public String setHexOpcode(int i, Instruction instruction)
    {
        String opcode, command, rs = "" ,rd = "", rt = "", imm = "", hexOpcode = "", func = "";
        int opcodeDecimal = 0;

        command = instruction.getCommand();

        /* list down all opcodes */
        /*
        * please follow the format below in DADDIU
        * */
        if( command.equalsIgnoreCase("DADDIU") )
        {
            opcodeDecimal = 25; // decimal equivalent of the opcode
            opcode  = StringUtils.leftPad( Integer.toBinaryString(opcodeDecimal), 6, "0");
            rs      = getRsToBinary( instruction.getRs(), 5 );
            rt      = getRsToBinary( instruction.getRt(), 5);
            rd      = "";
            imm     = convertHexToBinary( instruction.getImm(), 16);

            // hexOpcode = opcode + rs + rd + imm;

        }
        else if ( command.equalsIgnoreCase("DSUBU") )
        {
            opcode  = "000000";
            rs      = getRsToBinary( instruction.getRs(), 5);
            rt      = getRsToBinary( instruction.getRt(), 5);
            rd      = getRsToBinary( instruction.getRd(), 5);
            imm     = "00000";
            func    = "101111";
        }
        else if( command.equalsIgnoreCase("OR") )
		{
			opcode 	= "000000";
			rs 		= getRsToBinary( instruction.getRs(), 5);
			rt 		= getRsToBinary( instruction.getRt(), 5);
			rd 		= getRsToBinary( instruction.getRd(), 5);
			imm     = "00000";
			func	= "100101";
		}
		else if( command.equalsIgnoreCase("SLT") )
		{
			opcode 	= "000000";
			rs 		= getRsToBinary( instruction.getRs(), 5);
			rt 		= getRsToBinary( instruction.getRt(), 5);
			rd 		= getRsToBinary( instruction.getRd(), 5);
			imm     = "00000";
			func	= "101010";
		}
		else if( command.equalsIgnoreCase("LD") )
		{
			opcodeDecimal = 55;
			opcode 	= StringUtils.leftPad( Integer.toBinaryString(opcodeDecimal), 6, "0");
			rs 	    = getRsToBinary( instruction.getBase(), 5);
			rt 		= getRsToBinary( instruction.getRt(), 5);
			imm  	= convertHexToBinary( instruction.getOffset(), 16);
		}
		else if( command.equalsIgnoreCase("SD") )
		{
			opcodeDecimal = 63;
			opcode 	= StringUtils.leftPad( Integer.toBinaryString(opcodeDecimal), 6, "0");
			rs 	    = getRsToBinary( instruction.getBase(), 5);
			rt 		= getRsToBinary( instruction.getRt(), 5);
			imm	    = convertHexToBinary( instruction.getOffset(), 16);
		}
		else if( command.equalsIgnoreCase("BNE") )
		{
			opcodeDecimal = 5;
			opcode 	= StringUtils.leftPad( Integer.toBinaryString(opcodeDecimal), 6, "0");
			rs 		= getRsToBinary( instruction.getRs(), 5);
			rt 		= getRsToBinary( instruction.getRt(), 5);
			imm  	= convertHexToBinary( instruction.getImm(), 16);
		}
		else if( command.equalsIgnoreCase("J") )
		{
			opcodeDecimal = 2;
			opcode 	= StringUtils.leftPad( Integer.toBinaryString(opcodeDecimal), 6, "0");
			//instr_index
			imm  	= convertHexToBinary( instruction.getOffset(), 26);
		}
		else if( command.equalsIgnoreCase("NOP") )
		{
			opcodeDecimal = 0;
			opcode 	= StringUtils.leftPad( Integer.toBinaryString(opcodeDecimal), 32, "0");
		}
        else
        {
            hexOpcode = "NaN";
            opcode = "";
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
        bigInt  = new BigInteger(opBinary, 2);
        opHex   = bigInt.toString(16);
        opHex   = StringUtils.leftPad(opHex, 8, "0");

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
