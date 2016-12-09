package Laurenz.Pipelining;

import Laurenz.Models.Instruction;
import Laurenz.Models.InternalRegister;
import controller.MemoryController;
import controller.PipelineMapController;
import org.apache.commons.lang3.StringUtils;
import Laurenz.Controllers.MIPSModules.OpcodeMaker;
import Laurenz.Pipelining.UpdatedPipeline;

import java.util.NoSuchElementException;

public class Memory extends RunPipeline {

	private OpcodeMaker opcodeMaker = new OpcodeMaker();

	public Memory(InternalRegister ir, UpdatedPipeline pipelineService) {
		super(ir, pipelineService);
	}

	@Override
	public void run(int cycleNumber) {
		try {
			Instruction peek = queue.peekFirst();
			if (peek != null && isExFinished(peek)) {
				Instruction ins = queue.remove();

				String command = ins.getCommand().toUpperCase();

				ir.setMEMWBIR(ir.getEXMEMIR());
				ir.setMEMWBALUOutput(ir.getEXMEMALUOutput());
				if (command.equals("LD")) { // Check length from LW to LD
					String memHex = MemoryController.getHexWordFromMemory(ir.getEXMEMALUOutput());
					String memValBin = opcodeMaker.convertHexToBinary(memHex, 32);
					String padChar = "0";
					if (command.equals("LD")) // Check length from LW to LD
						padChar = memValBin.substring(0, 1);
					memValBin = StringUtils.leftPad(memValBin, 64, padChar);
					String hex = opcodeMaker.convertBinaryToHex(memValBin).toUpperCase();
					ir.setMEMWBLMD(hex);
				} else if (command.equals("SD")) {
					String word = ir.getEXMEMB().substring(8, 16); // Check length from LW to LD
					MemoryController.storeWordToMemory(word, ir.getEXMEMALUOutput());
				}

				PipelineMapController.setMapValue("MEM", ins.getIndex(), cycleNumber);
				ins.setMemoryFinished(true);
			}

		} catch (NoSuchElementException e) {

		}
	}


	// Not gonna use this I guess. Still thinking of the purpose of such functionessssusususussuusuaaaaaa
	private boolean isExFinished(Instruction peek) {
		if (peek.getCommand().equals("MUL.S") && peek.getExecuteFinished() == 6)
			return true;
		else if (peek.getCommand().equals("ADD.S") && peek.getExecuteFinished() == 4)
			return true;
		else if (!peek.getCommand().equals("MUL.S") && !peek.getCommand().equals("ADD.S") && peek.getExecuteFinished() == 1)
			return true;
		else
			return false;

	}
}
