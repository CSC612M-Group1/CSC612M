package Laurenz.Controllers.MIPSModules.MIPSCycles;

import Laurenz.GeneralUtility.Print;
import Laurenz.Models.Instruction;
import Laurenz.Models.InternalRegister;

import java.util.LinkedList;

public abstract class RunPipeline {

	UpdatedPipeline pipelining;

	LinkedList<Instruction> queue = new LinkedList<>();
	InternalRegister ir;
	int cycleNumber = 0;

	public RunPipeline(InternalRegister ir, UpdatedPipeline pipelining)
	{
		this.ir = ir;
		this.pipelining = pipelining;
		Print.ln("mmhmm");
	}

	public void addInstructionToQueue(Instruction ins) {
		queue.add(ins);
	}

	public abstract void run(int cycleNumber);
	
	public Instruction peek() {
		return queue.peek();
	}
}
