package exceptions;

import things.*;

//бросается, когда муми-тролль не может что-то сделать из-за маленького количества жизней
public class FewLivesException extends Exception {
	@Override
	public String toString() {
		return "FewLives!!!";
	}
}