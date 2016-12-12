package Laurenz.Controllers.MIPSModules.MIPSCycles;

import Laurenz.Models.Instruction;
import Laurenz.Models.InternalRegister;
import Laurenz.Controllers.PipelineController;
import Laurenz.Controllers.RegisterController;
import Laurenz.Views.MainWindow;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.util.NoSuchElementException;

public class Execute extends RunPipeline
{
	private MainWindow mw;
	private RegisterController registerController;
	private PipelineController pipelineController;

	public Execute(MainWindow mw, InternalRegister ir, UpdatedPipeline pipelining)
	{
		super(ir, pipelining);
		this.mw = mw;
		this.registerController = mw.getRegisterController();
		this.pipelineController = mw.getPipelineController();
	}

	@Override
	public void run(int cycleNumber) {
		try {
			if (queue.peekFirst() != null && queue.peekFirst().isDecodeFinished()) {
				/* Get instruction from queue */
				Instruction inst = queue.remove();

				String command = inst.getCommand().toUpperCase();
				String aluOutput = "";
				String cond = "0";
				BigInteger imm = new BigInteger(ir.getIDEXIMM(), 16);

				/* I-Type */
				if (command.equals("LD") || command.equals("SD") || command.equals("DADDIU")) {
					/* A + Imm */
					BigInteger a = new BigInteger(ir.getIDEXA(), 16);
					aluOutput = a.add(imm).toString(16).toUpperCase();
					aluOutput = StringUtils.leftPad(aluOutput, 16, "0");
				}
				/* R-Type */
				else if (command.equals("OR") || command.equals("DSUBU") || command.equals("SLT")
						|| command.equals("NOP")) {
					/* A op B */
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

				/* BNE */
				//System.out.println(ir.getIDEXA() + " " + ir.getIDEXB());
				if(command.equals("BNE") && !ir.getIDEXA().equals(ir.getIDEXB()))
					cond = "1";
				else if (command.equals("BNE") && ir.getIDEXA().equals(ir.getIDEXB()))
					cond = "0";

				/* Setting Values */
				ir.setEXMEMALUOutput(aluOutput);
				ir.setEXMEMCond(cond);
				ir.setEXMEMIR(ir.getIDEXIR());
				ir.setEXMEMB(ir.getIDEXB());

				/* Current instruction processing is finished, add to pipeline map */
				pipelineController.setMapValue("EX", inst.getIndex(), cycleNumber);
				inst.setExecuteFinished(1);
				pipelining.addInstructionTo("MEM", inst.getIndex());
				pipelining.addInstructionTo("WB", inst.getIndex());
			}
		} catch (NoSuchElementException e) {

		}
	}

}
