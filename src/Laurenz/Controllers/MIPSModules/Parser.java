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
        String text = "DADDIU R1, R1, #4444 \nDADDIU R2, R0, #1111";
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
                "BNE", "LD", "SD", "DADDIU",
                "J"
        };
        /* values */
        instructions = new ArrayList<>();
        text = text.replaceAll("(?m)^[ \t]*\r?\n", "");
        lines        = text.split("\\r?\\n");

        for(int i = 0; i < lines.length; i++ )
        {
            String[] registers = new String[3];
            singleLine = lines[i];
            Scanner scanner = new Scanner(singleLine);
            Print.ln(singleLine);

            String[] temp = singleLine.split(" ");
            command = temp[0];
            if(temp.length == 1 && command.equalsIgnoreCase("NOP"))
            {
                Print.ln("command: " + command );
            }
            else if(temp.length == 2 && command.equalsIgnoreCase("J"))
            {
                registers[0] = temp[1];
                Print.ln("command: " + command );
                Print.ln("offset: " + registers[0]);
            }
            else if(temp.length == 3 && (command.equalsIgnoreCase("LD") || command.equalsIgnoreCase("SD")))
            {
                registers[0] = temp[1].split(",")[0];
                registers[1] = temp[2].split("\\(")[0];
                registers[2] = temp[2].split("\\(")[1].split("\\)")[0];
                Print.ln("command: " + command );
                Print.ln("rt: " + registers[0]);
                Print.ln("offset: " + registers[1]);
                Print.ln("base: " + registers[2]);
            }
            else if (temp.length == 4 && (command.equalsIgnoreCase("DADDIU") || command.equalsIgnoreCase("OR")
                                       || command.equalsIgnoreCase("DSUBU") || command.equalsIgnoreCase("SLT")
                                       || command.equalsIgnoreCase("BNE")))
            {
                registers[0] = temp[1].split(",")[0];
                registers[1] = temp[2].split(",")[0];
                registers[2] = temp[3];
                Print.ln("command: " + command );
                Print.ln("rd: " + registers[0]);
                Print.ln("rs: " + registers[1]);
                Print.ln("imm: " + registers[2]);
            }
            else
            {
                Print.ln("Syntax Error");
                return null;
            }
            Instruction ins;
            ins = formInstruction(i, singleLine, command, registers);


            instructions.add(ins);
//          /* checks if the line has content */
//          if (scanner.hasNext()) {
//              String firstArg = scanner.next();
//              String[] registers;
//              command = scanner.next();
//
//              Instruction ins = null;
//
//                  ins = getInstruction(i, singleLine, command, registers);
//                  instructions.add(ins);
//              scanner.close();
//          }
        }

        return instructions;
    }

    public Instruction formInstruction(int i, String singleLine, String command, String[] registers)
    {
        Instruction instruction;
        String rd, rs, rt, imm, offset, base;

        rd  = registers[0];
        rs  = registers[1];
        rt  = registers[2];
        imm = registers[2];
        offset = null;
        base = null;

        if( command.equalsIgnoreCase( "DADDIU" ) )
        {
            rt = rd;
            rd = null;
            if( imm.startsWith("#") )
            {
                imm = imm.substring(1);
            }
        }
        else if( command.equalsIgnoreCase( "DSUBU" ) || command.equalsIgnoreCase( "OR" ) || command.equalsIgnoreCase( "SLT" ))
        {
            imm = null;
        }
        else if( command.equalsIgnoreCase( "LD" ) || command.equalsIgnoreCase( "SD" ) )
        {
            base = rt;
            rt = rd;
            rd = null;
            offset = rs;
            if( offset.startsWith("#") )
            {
                offset = offset.substring(1);
            }
            rs = null;
            imm = null;
        }
        else if( command.equalsIgnoreCase( "BNE" ) )
        {
            offset = rt;
            rt = rs;
            rs = rd;
            rd = null;
            imm = null;
        }
        else if( command.equalsIgnoreCase( "J" ) )
        {
            offset = rd;
            rd = null;
            rs = null;
            rt = null;
            imm = null;
        }
        else if( command.equalsIgnoreCase( "NOP" ) )
        {
            rd = null;
            rs = null;
            rt = null;
            imm = null;
        }
        else
        {
            Print.ln("Invalid Instruction");
            return null;
        }

        instruction = new Instruction(i, singleLine, command, null, rd, rs, rt, imm, offset, base);

        return instruction;
    }
}
