package pe.edu.unfv.domain.exceptions;

public class StudentEmailAlreadyExistsException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StudentEmailAlreadyExistsException(String email) {
		super("Student email: ".concat(email).concat(" already exists!"));		
	}
}
