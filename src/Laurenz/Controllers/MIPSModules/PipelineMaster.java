package Laurenz.Controllers.MIPSModules;

import Laurenz.Controllers.MIPSModules.MIPSCycles.*;
import Laurenz.GeneralUtility.Print;
import Laurenz.Models.Instruction;
import Laurenz.Models.InternalRegister;
import Laurenz.Views.MainWindow;

import java.util.ArrayList;

public class PipelineMaster
{
	MainWindow mw;
	private Fetch fetch;
	private Decode decode;
	private Execute execute;
	private Memory memory;
	private Writeback writeback;
	private RunPipeline runPipeline;
	private UpdatedPipeline updatedPipeline;

	private ArrayList<Instruction> instructionsList;
	private InternalRegister internalRegister;
	private int cycle = 0;

	public PipelineMaster(MainWindow mw, InternalRegister internalRegister)
	{
		this.internalRegister = internalRegister;
		this.mw = mw;

		fetch 		= new Fetch(mw, internalRegister, updatedPipeline);
		decode 		= new Decode(mw, internalRegister, updatedPipeline);
		execute 	= new Execute(mw, internalRegister, updatedPipeline);
		memory 		= new Memory(mw, internalRegister, updatedPipeline);
		writeback 	= new Writeback(mw, internalRegister, updatedPipeline);
	}

	public void singleRun()
	{
		cycle++;

		fetch.run(cycle);
		decode.run(cycle);
		execute.run(cycle);
		memory.run(cycle);
		writeback.run(cycle);




	}

	public void fullRun()
	{
		boolean finished = false;

		while ( hasNext() )
		{
			singleRun();
		}
	}

	public boolean hasNext()
	{
		if( fetch.peek() == null && decode.peek() == null && execute.peek() == null && memory.peek() == null && writeback == null)
		{
			return false;
		}
		return true;
	}

	public void addInstructionIn(String function, int instructionNo)
	{
		Instruction instruction = instructionsList.get(instructionNo);

		if ( instructionNo < instructionsList.size() )
		{
			if( function.equalsIgnoreCase("IF") || function.equalsIgnoreCase("fetch") )
			{
				fetch.addInstructionToQueue( instruction );
			}
			else if ( function.equalsIgnoreCase("ID") || function.equalsIgnoreCase("decode") )
			{
				decode.addInstructionToQueue( instruction );
			}
			else if ( function.equalsIgnoreCase("EX") || function.equalsIgnoreCase("execute") )
			{
				execute.addInstructionToQueue( instruction );
			}
			else if ( function.equalsIgnoreCase("MEM") || function.equalsIgnoreCase("memory") )
			{
				memory.addInstructionToQueue( instruction );
			}
			else if ( function.equalsIgnoreCase("wb") || function.equalsIgnoreCase("writeback") )
			{
				writeback.addInstructionToQueue( instruction );
			}
		}
	}

	public void setInstructionList(ArrayList<Instruction> instructionsList)
	{
		this.instructionsList = instructionsList;

		Instruction last = instructionsList.get( instructionsList.size() - 1 );
		Instruction nop	 = new Instruction( instructionsList.size(), "DADDIU r0, r0, #0", "DADDIU", "r0", "r0", "r0", "#0", null, null, null);

		if( last.getCommand().equalsIgnoreCase("J") || last.getCommand().equalsIgnoreCase("BNE") )
		{
			instructionsList.add(nop);
		}

		fetch.addInstructionToQueue( instructionsList.get(0) );
	}

	public Instruction peekAtIDService() {
		Print.ln("Started peekAtIDService");
		return decode.peek();
	}
}
