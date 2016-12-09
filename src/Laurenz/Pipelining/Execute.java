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

public class Execute extends RunPipeline {

	public Execute(InternalRegister ir, UpdatedPipeline pipelineService) {
		super(ir, pipelineService);
	}

	@Override
	public void run(int cycleNumber) {
		try {
			if (queue.peekFirst() != null && queue.peekFirst().isDecodeFinished()) {
				Instruction ins = queue.remove();

				String command = ins.getCommand().toUpperCase();
				String aluOutput = "";
				String cond = "0";

				BigInteger imm = new BigInteger(ir.getIDEXIMM(), 16);

				/* I-Type */
				if (command.equals("LD") || command.equals("SD") || command.equals("DADDIU")) {
					BigInteger a = new BigInteger(ir.getIDEXA(), 16);
					aluOutput = a.add(imm).toString(16).toUpperCase();
					aluOutput = StringUtils.leftPad(aluOutput, 16, "0");
				}
				/* R-Type */
				else if (command.equals("OR") || command.equals("DSUBU") || command.equals("SLT")
						|| command.equals("NOP")) {
					BigInteger a = new BigInteger(ir.getIDEXA(), 16);
					BigInteger b = new BigInteger(ir.getIDEXB(), 16);
					if (command.equals("OR")) {
						aluOutput = a.or(b).toString(16).toUpperCase();
					} else if (command.equals("DSUBU")) {
						aluOutput = a.subtract(b).toString(16).toUpperCase();
					} else if (command.equals("SLT")) {
						if (a.compareTo(b) == -1)
							aluOutput = StringUtils.leftPad("1", 16, "0");
						else
							aluOutput = StringUtils.leftPad("0", 16, "0");
					} else if (command.equals("NOP")) { // 0 + 0
						aluOutput = a.add(b).toString(16).toUpperCase();
					}
					aluOutput = StringUtils.leftPad(aluOutput, 16, "0");
				}
				/* J-Type */
				if (command.equals("J"))
					cond = "1";

				/* BNE? */

				ir.setEXMEMALUOutput(aluOutput);
				ir.setEXMEMCond(cond);
				ir.setEXMEMIR(ir.getIDEXIR());
				ir.setEXMEMB(ir.getIDEXB());

				PipelineMapController.setMapValue("EX", ins.getIndex(), cycleNumber);
				ins.setExecuteFinished(1);
				pipelining.addInstructionTo("MEM", ins.getIndex());
				pipelining.addInstructionTo("WB", ins.getIndex());
			}
		} catch (NoSuchElementException e) {

		}
	}

}
