package chess;

public class ExcecaoXadrez extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ExcecaoXadrez (String msg) {
		super(msg);
	}
}
