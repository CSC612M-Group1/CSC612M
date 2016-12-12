package Laurenz.Controllers.MIPSModules;

import Laurenz.GeneralUtility.Print;
import Laurenz.Models.Instruction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.swing.*;

public class Parser
{

    public static void main(String[] args)
    {
        String text = "J L1 \nL1: DADDIU R1, R1, #4444 \nDADDIU R2, R0, #1111";
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
        String singleLine, command, label;
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
        lines = text.split("\\r?\\n");

        for(int i = 0; i < lines.length; i++ )
        {
            String[] registers = new String[3];
            singleLine = lines[i];
            Scanner scanner = new Scanner(singleLine);
            Print.ln(singleLine);

            String[] temp_0 = singleLine.split(" ");
            String[] temp;
            if(temp_0[0].endsWith(":"))
            {
                label = temp_0[0].split(":")[0];
                command = temp_0[1];
                temp = Arrays.copyOfRange(temp_0, 1,  temp_0.length);
            }
            else
            {
                label = null;
                command = temp_0[0];
                temp = temp_0;
            }
            
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
                JOptionPane.showMessageDialog(null, "Invalid code: " + singleLine, "Error", JOptionPane.ERROR_MESSAGE);
                Print.ln("Invalid code");
                return null;
            }
            Instruction ins;
            ins = formInstruction(i, label, singleLine, command, registers);


            instructions.add(ins);
          /* checks if the line has content */
          /*
          if (scanner.hasNext()) {
              String firstArg = scanner.next();
              String[] registers;
              command = scanner.next();

              Instruction ins = null;

                  ins = getInstruction(i, singleLine, command, registers);
                  instructions.add(ins);
              scanner.close();
          }
          */
        }

        return instructions;
    }

    public Instruction formInstruction(int i, String label, String singleLine, String command, String[] registers)
    {
        Instruction instruction;
        String rd, rs, rt, imm, offset, base;

        rd  = registers[0];
        rs  = registers[1];
        rt  = registers[2];
        imm = null;
        offset = null;
        base = null;

        if( command.equalsIgnoreCase( "DADDIU" ) )
        {
            imm = rt;
            if(!checkImmediate(singleLine, imm)) return null;
            rt = rd;
            if(!checkRegister(singleLine, rt)) return null;
            
            rd = null;
        }
        else if( command.equalsIgnoreCase( "DSUBU" ) || command.equalsIgnoreCase( "OR" ) || command.equalsIgnoreCase( "SLT" ))
        {
            if(!checkRegister(singleLine, rd)) return null;
            if(!checkRegister(singleLine, rs)) return null;
            if(!checkRegister(singleLine, rt)) return null;
        }
        else if( command.equalsIgnoreCase( "LD" ) || command.equalsIgnoreCase( "SD" ) )
        {
            base = rt;
            if(!checkRegister(singleLine, base)) return null;
            rt = rd;
            if(!checkRegister(singleLine, rt)) return null;
            offset = rs;
            if(!checkOffset(singleLine, offset)) return null;
            
            rd = null;
            rs = null;
        }
        else if( command.equalsIgnoreCase( "BNE" ) )
        {
            offset = rt;    //label
            rt = rs;
            if(!checkRegister(singleLine, rt)) return null;
            rs = rd;
            if(!checkRegister(singleLine, rs)) return null;
            
            rd = null;
        }
        else if( command.equalsIgnoreCase( "J" ) )
        {
            offset = rd;    //label
            
            rd = null;
            rs = null;
            rt = null;
        }
        else if( command.equalsIgnoreCase( "NOP" ) )
        {
            rd = null;
            rs = null;
            rt = null;
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Invalid instruction: " + singleLine, "Error", JOptionPane.ERROR_MESSAGE);
            Print.ln("Invalid Instruction");
            return null;
        }

        instruction = new Instruction(i, label, singleLine, command, null, rd, rs, rt, imm, offset, base);

        return instruction;
    }
    
    public boolean checkRegister(String singleLine, String R)
    {
        if( R.length()<2 || !(R.startsWith("r") || R.startsWith("R")) || Integer.parseInt(R.substring(1))<0 || Integer.parseInt(R.substring(1))>31)
        {
            JOptionPane.showMessageDialog(null, "Invalid register: " + R + " in " + singleLine, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    public boolean checkImmediate(String singleLine, String I)
    {
        if( I.startsWith("#") && I.substring(1).length() == 4 )
        {
            I = I.substring(1);
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Invalid immediate value: " + I + " in " + singleLine, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }    
    
    public boolean checkOffset(String singleLine, String O)
    {
        if( O.length() != 4 )
        {
            JOptionPane.showMessageDialog(null, "Invalid offset value: " + O + " in " + singleLine, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }  
}
