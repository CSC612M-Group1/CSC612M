package Laurenz.Pipelining;

import Laurenz.Models.Instruction;
import Laurenz.Models.InternalRegister;
import Laurenz.Controllers.MIPSModules.PipelineMapController;
import Laurenz.Controllers.MIPSModules.RegistersController;
import Laurenz.Controllers.MIPSModules.OpcodeMaker;
import org.apache.commons.lang3.StringUtils;


import java.math.BigInteger;
import java.util.NoSuchElementException;

public class Decode extends RunPipeline {

	RegistersController registersController = RegistersController.getInstance();
	private OpcodeMaker opcodeMaker = new OpcodeMaker();

	public Decode(InternalRegister ir, UpdatedPipeline pipelining) { super(ir, pipelining); }

	@Override
	public void run(int cycleNumber) {
		try {
			Instruction peek = queue.peek(); // Takes a look at the head of the queue
			if (peek != null && (registersController.isLocked(peek.getRs()) || registersController.isLocked(peek.getRt())
					|| registersController.getCycleNumberReleased(peek.getRs()) == cycleNumber
					|| registersController.getCycleNumberReleased(peek.getRt()) == cycleNumber )) {
				System.out.println("Data Hazard Stall"); // Data Hazard Stall - in tandem with IF for next instruction
			}
			/* DECODE START! */
			else if (peek != null && peek.isFetchFinished()) {
				/* Get instruction from queue */
				Instruction inst = queue.remove();
				String command = inst.getCommand().toUpperCase();
				BigInteger integer = new BigInteger(inst.getHexOpcode(), 16);
				String binary = StringUtils.leftPad(integer.toString(2), 32, "0");

				/* Retrieving A and B */
				int regA = Integer.parseInt(binary.substring(6, 11), 2);
				int regB = Integer.parseInt(binary.substring(11, 16), 2);
				String a, b;
				a = RegistersController.getInstance().getValue(regA, 1);
				b = RegistersController.getInstance().getValue(regB, 1);

				/* Retrieving Immediate */
				String imm, padChar;
				imm = binary.substring(16, 32);
				padChar = imm.substring(0, 1);
				imm = StringUtils.leftPad(imm, 64, padChar);
				imm = opcodeMaker.convertBinaryToHex(imm);

				/* Setting Values */
				ir.setIDEXA(StringUtils.leftPad(a, 16, "0"));
				ir.setIDEXB(StringUtils.leftPad(b, 16, "0"));
				ir.setIDEXIMM(imm.toUpperCase());
				ir.setIDEXIR(inst.getHexOpcode());

				/* Current instruction processing is finished, add to pipeline map */
				PipelineMapController.setMapValue("ID", inst.getIndex(), cycleNumber);
				inst.setDecodeFinished(true);
				pipelining.addInstructionTo("EX", inst.getIndex());
				registersController.lock(inst.getRd());
			}
		} catch (NoSuchElementException e) {

		}
	}

}
