package Laurenz.Pipelining;

import Laurenz.Models.Instruction;
import Laurenz.Models.InternalRegister;
import controller.PipelineMapController;
import controller.RegistersController;
import Laurenz.Controllers.MIPSModules.OpcodeMaker;
import Laurenz.Pipelining.UpdatedPipeline;
import org.apache.commons.lang3.StringUtils;

import java.util.NoSuchElementException;

public class Fetch extends RunPipeline {

	int prevLineNumber = -1;
	private OpcodeMaker opcodeMaker = new OpcodeMaker();

	public Fetch(InternalRegister ir, UpdatedPipeline pipelining) {
		super(ir, pipelining);
	}

	@Override
	public void run(int cycleNumber) {
		try {
			// Fix such tht IF of instruction 1 should stall when ID of
			// instruction 0 has not yet executed.
			// Possible solution would be to lock IF/ID.IR in IF and release in
			// ID.
			Instruction peek = queue.peek();

			Instruction idPeek = pipelining.peekAtIDService();

			if (idPeek != null && idPeek.getIndex() == prevLineNumber) {
				System.out.println("Waiting for Decode");
			} else if (peek != null) {
				if ((peek.isDecodeFinished() == true && peek.isWritebackFinished() == false) || (peek.isDecodeFinished()
						&& peek.isWritebackFinished() && peek.getWritebackFinishedAtCycleNumber() == cycleNumber)) {
					System.out.println("Need to wait");
				} else {
					Instruction ins = queue.remove();
					PipelineMapController.setMapValue("IF", ins.getIndex(), cycleNumber);

					String opcode = ir.getIFIDIR();
					String binary = null;
					if (opcode != null) {
						binary = opcodeMaker.convertHexToBinary(opcode, 32);
					}
					if (binary != null && binary.substring(0, 6).equals("000100")
							&& isNotEqualRegisters(binary.substring(6, 11), binary.substring(11, 16))) { // BNE
						int npc;
						if (ir.getIFIDNPC() != null) {
							npc = Integer.parseInt(ir.getIFIDNPC(), 16);
						} else {
							npc = 0;
						}

						String immediate = StringUtils.leftPad(binary.substring(16), 32, binary.substring(16, 17));
						long l = Long.parseLong(immediate, 2);
						int imm = (int) l * 4;
						String hex = StringUtils.leftPad(Integer.toHexString(npc + imm), 16, "0").toUpperCase();
						ir.setIFIDNPC(hex);
						ir.setPC(hex);
					} else if (binary != null && binary.substring(0, 6).equals("000010")) { // J
						String address = binary.substring(8) + "00";
						String hex = opcodeMaker.convertBinaryToHex(address).toUpperCase();
						ir.setIFIDNPC(hex);
						ir.setPC(hex);
					} else {
						String npc = StringUtils.leftPad(Integer.toHexString((ins.getIndex() + 1) * 4), 16, "0")
								.toUpperCase();
						ir.setIFIDNPC(npc);
						ir.setPC(npc);
						pipelining.addInstructionTo("ID", ins.getIndex());
					}
					ir.setIFIDIR(ins.getHexOpcode());

					prevLineNumber = ins.getIndex();
					int nextInsLineNumber = Integer.parseInt(ir.getIFIDNPC(), 16) / 4;
					pipelining.addInstructionTo("IF", nextInsLineNumber);
					ins.resetPipelineStatus();
					ins.setFetchFinished(true);
				}
			}
		} catch (NoSuchElementException e) {
		}

	}

	private void addRemainingFunctionsToQueue(int instructionNumber) {

		pipelining.addInstructionTo("WB", instructionNumber);
	}

	private boolean isNotEqualRegisters(String regBin1, String regBin2) {
		// for BNE
		if (regBin1 == null || regBin2 == null)
			return false;
		else {
			int index1 = Integer.parseInt(regBin1, 2);
			int index2 = Integer.parseInt(regBin2, 2);
			String val1 = RegistersController.getInstance().getValue(index1, 1);
			String val2 = RegistersController.getInstance().getValue(index2, 1);
			if (!val1.equals(val2))
				return true;
			else
				return false;
		}

	}
}
