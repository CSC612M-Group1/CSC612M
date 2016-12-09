package Laurenz.Models;

public class Instruction
{
	private int index;
	private String line;
	private String command;
	private String opcode;
	private String hexOpcode;
	private String rd;
	private String rs;
	private String rt;
	private String imm;
	private String offset;
	private String base;
	private String label;

	/* Store status of pipeline stages */
	private boolean fetchFinished = false;
	private boolean decodeFinished = false;
	private int executeFinished = 0;
	private boolean memoryFinished = false;
	private boolean writebackFinished = false;
	private int writebackFinishedAtCycleNumber = -1;

	public Instruction(int index, String line, String command, String opcode, String rd, String rs, String rt, String imm, String offset, String base)
	{
		this.index = index;
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

	public boolean isFetchFinished() { return fetchFinished; }

	public void setFetchFinished(boolean fetchFinished) { this.fetchFinished = fetchFinished; }

	public boolean isDecodeFinished() {
		return decodeFinished;
	}

	public void setDecodeFinished(boolean decodeFinished) {
		this.decodeFinished = decodeFinished;
	}

	public int getExecuteFinished() {
		return executeFinished;
	}

	public void setExecuteFinished(int exFinished) {
		this.executeFinished = executeFinished;
	}

	public boolean isMemoryFinished() {
		return memoryFinished;
	}

	public void setMemoryFinished(boolean memoryFinished) {
		this.memoryFinished = memoryFinished;
	}

	public boolean isWritebackFinished() {
		return writebackFinished;
	}

	public void setWritebackFinished(boolean writebackFinished) {
		this.writebackFinished = writebackFinished;
	}

	public int getWritebackFinishedAtCycleNumber() {
		return writebackFinishedAtCycleNumber;
	}

	public void setWritebackFinishedAtCycleNumber(int writebackFinishedAtCycleNumber) {
		this.writebackFinishedAtCycleNumber = writebackFinishedAtCycleNumber;
	}

	public void resetPipelineStatus() {
		fetchFinished = false;
		decodeFinished = false;
		executeFinished = 0;
		memoryFinished = false;
		writebackFinished = false;
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

	public String getHexOpcode() {
		return hexOpcode;
	}

	public void setHexOpcode(String hexOpcode) {
		this.hexOpcode = hexOpcode;
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
