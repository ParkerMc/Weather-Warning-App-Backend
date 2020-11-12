package utd.group12.weatherwarning.errors;

public class BadRequestError extends Exception  {
	private static final long serialVersionUID = 2728867520030811206L;

	public BadRequestError(String string) {
		super(string);
	}
}
