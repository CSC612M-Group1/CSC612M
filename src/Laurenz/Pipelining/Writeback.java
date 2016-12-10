package Laurenz.Pipelining;

import Laurenz.Models.Instruction;
import Laurenz.Models.InternalRegister;
import controller.PipelineMapController;
import controller.RegistersController;
import org.apache.commons.lang3.StringUtils;
import Laurenz.Pipelining.UpdatedPipeline;

import java.math.BigInteger;
import java.util.NoSuchElementException;

public class Writeback extends RunPipeline {

	RegistersController registersController = RegistersController.getInstance();

	public Writeback(InternalRegister ir, UpdatedPipeline pipelineService) {
		super(ir, pipelineService);
	}

	@Override
	public void run(int cycleNumber) {
		try {
			if (queue.peekFirst() != null && queue.peekFirst().isMemoryFinished()) {
				/* Get instruction from queue */
				Instruction inst = queue.remove();

				String command = inst.getCommand().toUpperCase();

				/* No Writeback for Store, Branch and Jump */
				/* Load Instruction */
				if (command.equals("LD")) {
					int registerIndex = Integer.parseInt(inst.getRd().substring(1));
					RegistersController.setValue(ir.getMEMWBLMD(), registerIndex, 1);
					ir.setRn("R" + registerIndex + " = " + ir.getMEMWBLMD());
				} else if (command.equals("OR") || command.equals("DSUBU") || command.equals("SLT") ||
						command.equals("NOP") || command.equals("DADDIU")) {
					BigInteger integer = new BigInteger(inst.getHexOpcode(), 16);
					String binary = StringUtils.leftPad(integer.toString(2), 32, "0");
					int rd;
					/* Register - Immediate ALU Instruction */
					if (command.equals("DADDIU")) {
						rd = Integer.parseInt(binary.substring(11, 16), 2);
						RegistersController.setValue(ir.getMEMWBALUOutput(), rd, 1);
						ir.setRn("R" + rd + " = " + ir.getMEMWBALUOutput());
					}
					/* Register - Register ALU Instruction */
					else {
						rd = Integer.parseInt(binary.substring(16, 21), 2);
						RegistersController.setValue(ir.getMEMWBALUOutput(), rd, 1);
						ir.setRn("R" + rd + " = " + ir.getMEMWBALUOutput());
					}
				}

				/* Current instruction processing is finished, add to pipeline map */
				PipelineMapController.setMapValue("WB", inst.getIndex(), cycleNumber);
				inst.setWritebackFinished(true);
				inst.setWritebackFinishedAtCycleNumber(cycleNumber);
				registersController.unlock(inst.getRd(), cycleNumber);
			}
		} catch (NoSuchElementException e) {

		}
	}

}
