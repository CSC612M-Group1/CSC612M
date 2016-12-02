package Laurenz.Controllers.MIPSModules;

import Laurenz.GeneralUtility.Print;
import Laurenz.Models.Instruction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Parser
{

	public static void main(String[] args)
	{
		String text = "DADDIU R1, R0, #0009 \nDADDIU R2, R0, #1111";
		Parser p = new Parser();

		p.parse(text);
	}
	public Parser()
	{

	}

	public ArrayList<Instruction> parse(String text)
	{
		/* Declare variables */
		ArrayList<Instruction> instructions;
		String singleLine, command;
		String[] lines;
		/* Instruction set for the group */
		String[] instructionsSetB =
		{
				"OR", "DSUBU", "SLT", "NOP",
				"BNE", "LD", "SD", "SW", "DADDIU",
				"J"
		};
		/* values */
		instructions = new ArrayList<>();
		text = text.replaceAll("(?m)^[ \t]*\r?\n", "");
		lines 		 = text.split("\\r?\\n");

		for(int i = 0; i < lines.length; i++ )
		{
			String[] registers = new String[3];
			singleLine = lines[i];
			Scanner scanner = new Scanner(singleLine);
			Print.ln(singleLine);

			String[] temp = singleLine.split(" ");
			command = temp[0];
			registers[0] = temp[1].split(",")[0];
			registers[1] = temp[2].split(",")[0];
			registers[2] = temp[3];
			Print.ln("command: " + command );
			Print.ln("rd: " + registers[0]);
			Print.ln("rs: " + registers[1]);
			Print.ln("imm: " + registers[2]);

			Instruction ins;
			ins = formInstruction(i, singleLine, command, registers);


			instructions.add(ins);
//			/* checks if the line has content */
//			if (scanner.hasNext()) {
//				String firstArg = scanner.next();
//				String[] registers;
//				command = scanner.next();
//
//				Instruction ins = null;
//
//					ins = getInstruction(i, singleLine, command, registers);
//					instructions.add(ins);
//				scanner.close();
//			}
		}

		return instructions;
	}

	public Instruction formInstruction(int i, String singleLine, String command, String[] registers)
	{
		Instruction instruction;
		String rd, rs, rt, imm;

		rd  = registers[0];
		rs  = registers[1];
		rt  = registers[2];
		imm = registers[2];

		if( command.equals( "DADDIU" ) )
		{

			if( imm.startsWith("#") )
			{
				imm = imm.substring(1);
				rt = imm;
			}
		}

		instruction = new Instruction(i, singleLine, command, null, rd, rs, rt, imm, null, null);

		return instruction;
	}
}
