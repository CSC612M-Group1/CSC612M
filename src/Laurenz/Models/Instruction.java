package Laurenz.Models;

public class Instruction
{
	private int index;
	private String line;
	private String command;
	private String opcode;
	private String rd;
	private String rs;
	private String rt;
	private String imm;
	private String offset;
	private String base;
	private String label;
	public Instruction(int index, String label, String line, String command, String opcode, String rd, String rs, String rt, String imm, String offset, String base)
	{
		this.index = index;
		this.label = label;
		this.line = line;
		this.command = command;
		this.opcode = opcode;
		this.rd = rd;
		this.rs = rs;
		this.rt = rt;
		this.imm = imm;
		this.offset = offset;
		this.base = base;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getOpcode() {
		return opcode;
	}

	public void setOpcode(String opcode) {
		this.opcode = opcode;
	}

	public String getRd() {
		return rd;
	}

	public void setRd(String rd) {
		this.rd = rd;
	}

	public String getRs() {
		return rs;
	}

	public void setRs(String rs) {
		this.rs = rs;
	}

	public String getRt() {
		return rt;
	}

	public void setRt(String rt) {
		this.rt = rt;
	}

	public String getImm() {
		return imm;
	}

	public void setImm(String imm) {
		this.imm = imm;
	}

	public String getOffset() {
		return offset;
	}

	public void setOffset(String offset) {
		this.offset = offset;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
