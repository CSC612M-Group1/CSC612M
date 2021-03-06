package Laurenz.Pipelining;

import Laurenz.Models.Instruction;
import Laurenz.Models.InternalRegister;

import java.util.List;

public class UpdatedPipeline {

	Fetch fetch;
	Decode decode;
	Execute execute;
	Memory memory;
	Writeback writeback;

	InternalRegister ir;
	List<Instruction> instructions;
	int cycleNumber = 0;

	public UpdatedPipeline(InternalRegister ir) {
		this.ir = ir;
		fetch = new Fetch(ir, this);
		decode = new Decode(ir, this);
		execute = new Execute(ir, this);
		memory = new Memory(ir, this);
		writeback = new Writeback(ir, this);
	}

	public void setInstructions(List<Instruction> instructions) {
		this.instructions = instructions;

		Instruction lastInstruction = instructions.get(instructions.size() - 1);
		if (lastInstruction.getCommand().equals("BEQ") || lastInstruction.getCommand().equals("J")) {
			// CHANGE PARAMETERS OF INSTRUCTION OBJECT - This is btw for NOP = all zeroes value
			// Instruction nop = new Instruction(instructions.size(), "DADDU r0, r0, r0", "DADDU", "r0", "r0", "r0", null,
			//		null, null, null, null);
			// instructions.add(nop);
		}

		fetch.addInstructionToQueue(instructions.get(0));
	}

	public void singleCycleRun() {
		cycleNumber++;
		System.out.println("Cycle Number " + cycleNumber);
		writeback.run(cycleNumber);
		memory.run(cycleNumber);
		execute.run(cycleNumber);
		decode.run(cycleNumber);
		fetch.run(cycleNumber);
	}

	public void fullExecutionRun() {
		boolean isFinished = false;
		while (!isFinished) {
			singleCycleRun();
			if (fetch.peek() == null && decode.peek() == null && execute.peek() == null
					&& memory.peek() == null && writeback.peek() == null)
				isFinished = true;
		}
		// TODO Auto-generated method stub
	}

	public void addInstructionTo(String functionName, int instructionNumber) {
		if (instructionNumber < instructions.size()) {
			Instruction inst = instructions.get(instructionNumber);

			switch (functionName) {
			case ("IF"):
				fetch.addInstructionToQueue(inst);
				break;
			case ("ID"):
				decode.addInstructionToQueue(inst);
				break;
			case ("EX"):
				execute.addInstructionToQueue(inst);
				break;
			case ("MEM"):
				memory.addInstructionToQueue(inst);
				break;
			case ("WB"):
				writeback.addInstructionToQueue(inst);
				break;
			}
		}

	}

	public Instruction peekAtIDService() {
		return decode.peek();
	}

}
