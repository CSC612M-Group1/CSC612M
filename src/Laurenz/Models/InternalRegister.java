package Laurenz.Models;

/**
 * Created by laurenztolentino on 12/01/2016.
 */
public class InternalRegister
{
	String[][] InternalRegisterArray;

	public InternalRegister() {
		InternalRegisterArray = new String[15][3];
		InternalRegisterArray[0][0] = "IF";
		InternalRegisterArray[3][0] = "ID";
		InternalRegisterArray[7][0] = "EX";
		InternalRegisterArray[11][0] = "MEM";
		InternalRegisterArray[14][0] = "WB";
		InternalRegisterArray[0][1] = "IF/ID.IR =";
		InternalRegisterArray[1][1] = "IF/ID.NPC =";
		InternalRegisterArray[2][1] = "PC =";
		InternalRegisterArray[3][1] = "ID/EX.A =";
		InternalRegisterArray[4][1] = "ID/EX.B =";
		InternalRegisterArray[5][1] = "ID/EX.IMM =";
		InternalRegisterArray[6][1] = "ID/EX.IR =";
		InternalRegisterArray[7][1] = "EX/MEM.ALUOutput =";
		InternalRegisterArray[8][1] = "EX/MEM.COND =";
		InternalRegisterArray[9][1] = "EX/MEM.IR =";
		InternalRegisterArray[10][1] = "EX/MEM.B =";
		InternalRegisterArray[11][1] = "MEM/WB.LMD =";
		InternalRegisterArray[12][1] = "MEM/WB.IR =";
		InternalRegisterArray[13][1] = "MEM/WB.ALUOutput =";
		InternalRegisterArray[14][1] = "Rn =";
		for (int i = 0; i < InternalRegisterArray.length; i++) {
			InternalRegisterArray[i][2] = null;
		}
	}

	public String[][] getInternalRegisterArray() {
		return InternalRegisterArray;
	}

	public String getIFIDIR() {
		return InternalRegisterArray[1][2];
	}

	public void setIFIDIR(String value) {
		InternalRegisterArray[1][2] = value;
	}

	public String getIFIDNPC() {
		return InternalRegisterArray[2][2];
	}

	public void setIFIDNPC(String value) {
		InternalRegisterArray[2][2] = value;
	}

	public String getPC() {
		return InternalRegisterArray[2][2];
	}

	public void setPC(String value) {
		InternalRegisterArray[2][2] = value;
	}

	public String getIDEXA() {
		return InternalRegisterArray[3][2];
	}

	public void setIDEXA(String value) {
		InternalRegisterArray[3][2] = value;
	}

	public String getIDEXB() {
		return InternalRegisterArray[4][2];
	}

	public void setIDEXB(String value) {
		InternalRegisterArray[4][2] = value;
	}

	public String getIDEXIMM() {
		return InternalRegisterArray[5][2];
	}

	public void setIDEXIMM(String value) {
		InternalRegisterArray[5][2] = value;
	}

	public String getIDEXIR() {
		return InternalRegisterArray[6][2];
	}

	public void setIDEXIR(String value) {
		InternalRegisterArray[6][2] = value;
	}

	public void setEXMEMALUOutput(String value) {
		InternalRegisterArray[7][2] = value;
	}

	public String getEXMEMALUOutput() {
		return InternalRegisterArray[7][2];
	}

	public String getEXMEMCond() {
		return InternalRegisterArray[8][2];
	}

	public void setEXMEMCond(String value) {
		InternalRegisterArray[8][2] = value;
	}

	public String getEXMEMIR() {
		return InternalRegisterArray[9][2];
	}

	public void setEXMEMIR(String value) {
		InternalRegisterArray[9][2] = value;
	}

	public String getEXMEMB() {
		return InternalRegisterArray[10][2];
	}

	public void setEXMEMB(String value) {
		InternalRegisterArray[10][2] = value;
	}

	public String getMEMWBLMD() {
		return InternalRegisterArray[11][2];
	}

	public void setMEMWBLMD(String value) {
		InternalRegisterArray[11][2] = value;
	}

	public String getMEMWBIR() {
		return InternalRegisterArray[12][2];
	}

	public void setMEMWBIR(String value) {
		InternalRegisterArray[12][2] = value;
	}

	public String getMEMWBALUOutput() {
		return InternalRegisterArray[13][2];
	}

	public void setMEMWBALUOutput(String value) {
		InternalRegisterArray[13][2] = value;
	}

	public String getRn() {
		return InternalRegisterArray[14][2];
	}

	public void setRn(String value) {
		InternalRegisterArray[14][2] = value;
	}
}
