package Laurenz.Pipelining;

import Laurenz.Models.Instruction;
import Laurenz.Models.InternalRegister;
import controller.PipelineMapController;
import controller.RegistersController;
import org.apache.commons.lang3.StringUtils;
import Laurenz.Controllers.MIPSModules.OpcodeMaker;
import Laurenz.Pipelining.UpdatedPipeline;

import java.math.BigInteger;
import java.util.NoSuchElementException;

public class Decode extends RunPipeline {

	RegistersController registersController = RegistersController.getInstance();
	private OpcodeMaker opcodeMaker = new OpcodeMaker();

	public Decode(InternalRegister ir, UpdatedPipeline pipelining) { super(ir, pipelining); }

	@Override
	public void run(int cycleNumber) {
		try {
			Instruction peek = queue.peek();
			if (peek != null
					&& (registersController.isLocked(peek.getRs()) || registersController.isLocked(peek.getRt())
							|| registersController.getCycleNumberReleased(peek.getRs()) == cycleNumber
							|| registersController.getCycleNumberReleased(peek.getRt()) == cycleNumber)) {

			} else if (peek != null && peek.isFetchFinished()) {
				Instruction ins = queue.remove();

				String command = ins.getCommand().toUpperCase();
				BigInteger integer = new BigInteger(ins.getHexOpcode(), 16);
				String binary = StringUtils.leftPad(integer.toString(2), 32, "0");

				String imm, padChar;

				imm = binary.substring(16, 32);
				padChar = imm.substring(0, 1);
				imm = StringUtils.leftPad(imm, 64, padChar);
				imm = opcodeMaker.convertBinaryToHex(imm);

				int regA = Integer.parseInt(binary.substring(6, 11), 2);
				int regB = Integer.parseInt(binary.substring(11, 16), 2);

				String a, b;

				a = RegistersController.getInstance().getValue(regA, 1);
				b = RegistersController.getInstance().getValue(regB, 1);
				ir.setIDEXA(StringUtils.leftPad(a, 16, "0"));
				ir.setIDEXB(StringUtils.leftPad(b, 16, "0"));

				ir.setIDEXIR(ins.getHexOpcode());
				ir.setIDEXIMM(imm.toUpperCase());

				PipelineMapController.setMapValue("ID", ins.getIndex(), cycleNumber);
				ins.setDecodeFinished(true);
				pipelining.addInstructionTo("EX", ins.getIndex());
				registersController.lock(ins.getRd());
			}
		} catch (NoSuchElementException e) {

		}
	}

}
