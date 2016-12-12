package Laurenz.Models;

/**
 * Created by laurenztolentino on 12/01/2016.
 */
public class Register
{

	private String value;
	private String R;
	private String instructionType;
	private int index;
	private boolean isLocked;
	private int cycleNumberReleased;

	public Register(int index, String value )
	{
		this.index 	= index;
		this.value 	= value;
		this.R 	 	= "R" + index;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getValue()
	{
		return this.value;
	}

	public void setValue( String value )
	{
		this.value = value;
	}

	public String getR() {
		return R;
	}

	public void setR(String r) {
		R = r;
	}

	public String getInstructionType() {
		return instructionType;
	}

	public void setInstructionType(String instructionType) {
		this.instructionType = instructionType;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean locked) {
		isLocked = locked;
	}

	public int getCycleNumberReleased() {
		return cycleNumberReleased;
	}

	public void setCycleNumberReleased(int cycleNumberReleased) {
		this.cycleNumberReleased = cycleNumberReleased;
	}
}
