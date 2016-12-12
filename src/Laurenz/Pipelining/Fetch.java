package Laurenz.Pipelining;

import Laurenz.Models.Instruction;
import Laurenz.Models.InternalRegister;
import Laurenz.Controllers.MIPSModules.PipelineMapController;
import Laurenz.Controllers.MIPSModules.RegistersController;
import Laurenz.Controllers.MIPSModules.OpcodeMaker;
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
			Instruction peek = queue.peek(); // Takes a look at the head of the queue
			Instruction idPeek = pipelining.peekAtIDService();

			if (idPeek != null && idPeek.getIndex() == prevLineNumber) {
				System.out.println("Waiting for Decode"); // Data Hazard Stall
			} else if (peek != null) {
				if ((peek.isDecodeFinished() == true && peek.isWritebackFinished() == false) || (peek.isDecodeFinished()
						&& peek.isWritebackFinished() && peek.getWritebackFinishedAtCycleNumber() == cycleNumber)) {
					System.out.println("Waiting for Writeback"); // Fetch is done after WB, stall till WB is finished
				}
				/* FETCH START! */
				else {
					/* Get instruction from queue */
					Instruction inst = queue.remove();

					String opcode = ir.getIFIDIR();
					String binary = null;
					if (opcode != null) {
						binary = opcodeMaker.convertHexToBinary(opcode, 32);
					}
					/* BNE - Branching Condition */
					if (binary != null && binary.substring(0, 6).equals("000101")
							&& isNotEqualRegisters(binary.substring(6, 11), binary.substring(11, 16))) {
						int npc;
						if (ir.getIFIDNPC() != null) {
							npc = Integer.parseInt(ir.getIFIDNPC(), 16);
						} else {
							npc = 0;
						}

						String immediate = StringUtils.leftPad(binary.substring(16), 32, binary.substring(16, 17));
						long l = Long.parseLong(immediate, 2);
						int imm = (int) l * 4;
						/* NPC + Imm << 2 */
						String hex = StringUtils.leftPad(Integer.toHexString(npc + imm), 16, "0").toUpperCase();
						ir.setIFIDNPC(hex);
						ir.setPC(hex);
					}
					/* J */
					else if (binary != null && binary.substring(0, 6).equals("000010")) {
						String address = binary.substring(8) + "00"; // Pad two 0s to get to the target address
						String hex = opcodeMaker.convertBinaryToHex(address).toUpperCase();
						ir.setIFIDNPC(hex);
						ir.setPC(hex);
					}
					/* If instruction is not Branch nor Jump */
					else {
						/* PC + 4 */
						String npc = StringUtils.leftPad(Integer.toHexString((inst.getIndex() + 1) * 4), 16, "0")
								.toUpperCase();
						ir.setIFIDNPC(npc);
						ir.setPC(npc);
						pipelining.addInstructionTo("ID", inst.getIndex());
					}
					ir.setIFIDIR(inst.getHexOpcode());

					/* Current instruction processing is finished, add to pipeline map */
					PipelineMapController.setMapValue("IF", inst.getIndex(), cycleNumber);
					prevLineNumber = inst.getIndex();
					int nextInstructionLineNumber = Integer.parseInt(ir.getIFIDNPC(), 16) / 4;
					pipelining.addInstructionTo("IF", nextInstructionLineNumber);
					inst.resetPipelineStatus();
					inst.setFetchFinished(true);
				}
			}
		} catch (NoSuchElementException e) {
		}

	}

	private void addRemainingFunctionsToQueue(int instructionNumber) {
		pipelining.addInstructionTo("WB", instructionNumber);
	}

	/* Not Equal Function for BNE */
	private boolean isNotEqualRegisters(String reg1, String reg2) {
		if (reg1 == null || reg2 == null)
			return false;
		else {
			int index1 = Integer.parseInt(reg1, 2);
			int index2 = Integer.parseInt(reg2, 2);
			String val1 = RegistersController.getInstance().getValue(index1, 1);
			String val2 = RegistersController.getInstance().getValue(index2, 1);
			if (!val1.equals(val2))
				return true;
			else return false;
		}

	}
}
