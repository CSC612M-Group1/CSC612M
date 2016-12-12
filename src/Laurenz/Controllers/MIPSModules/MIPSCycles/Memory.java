package Laurenz.Controllers.MIPSModules.MIPSCycles;

import Laurenz.Controllers.MemoryController;
import Laurenz.Controllers.PipelineController;
import Laurenz.Controllers.RegisterController;
import Laurenz.Models.Instruction;
import Laurenz.Models.InternalRegister;
import Laurenz.Controllers.MemoryController;
import Laurenz.Controllers.PipelineController;
import Laurenz.Controllers.MIPSModules.OpcodeMaker;
import Laurenz.Views.MainWindow;

import java.util.NoSuchElementException;

public class Memory extends RunPipeline
{

	private OpcodeMaker opcodeMaker = new OpcodeMaker();

	private MainWindow mw;
	private MemoryController memoryController;
	private PipelineController pipelineController;
	private RegisterController registerController;


	public Memory(MainWindow mw, InternalRegister ir, UpdatedPipeline pipelining)
	{
		super(ir, pipelining);
		this.mw = mw;
		this.pipelineController = mw.getPipelineController();
		this.registerController = mw.getRegisterController();
		this.memoryController 	= mw.getMemoryController();
	}

	@Override
	public void run(int cycleNumber) {
		try {
			Instruction peek = queue.peekFirst();
			/* MEMORY START! */
			if (peek != null && peek.getExecuteFinished() == 1) {
				/* Get instruction from queue */
				Instruction inst = queue.remove();

				String command = inst.getCommand().toUpperCase();
				ir.setMEMWBIR(ir.getEXMEMIR());
				ir.setMEMWBALUOutput(ir.getEXMEMALUOutput());

				if (command.equals("LD")) {
					/* Just getting the whole value in the designated memory/ies */
					String memHex = memoryController.getHexWordFromMemory(ir.getEXMEMALUOutput());
					// Add in the controller error handling restricting values that are out of bounds of the memory.
					// base + offset (the value in the register pointed) answer should be divisible by 8 hex
					// Change from word = word.substring(word.length()-16, word.length()); or word = word.substring(0, word.length());
					ir.setMEMWBLMD(memHex);
				} else if (command.equals("SD")) {
					String word = ir.getEXMEMB().substring(0, 16);
					memoryController.storeWordToMemory(word, ir.getEXMEMALUOutput());
				}

				/* Current instruction processing is finished, add to pipeline map */
				pipelineController.setMapValue("MEM", inst.getIndex(), cycleNumber);
				inst.setMemoryFinished(true);
			}

		} catch (NoSuchElementException e) {

		}
	}
}
