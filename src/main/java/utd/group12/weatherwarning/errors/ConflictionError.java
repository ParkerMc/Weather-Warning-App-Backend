package utd.group12.weatherwarning.errors;

public class ConflictionError extends Exception  {
	private static final long serialVersionUID = -479489513639935749L;
	
	public ConflictionError(String string) {
		super(string);
	}
}
