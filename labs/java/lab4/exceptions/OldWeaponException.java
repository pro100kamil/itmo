package exceptions;

import things.*;

//бросается, когда муми-троллю даётся оружие, которое у него уже есть
//возникает по ошибке программиста
public class OldWeaponException extends RuntimeException {
	@Override
	public String toString() {
		return "OldWeaponException!!!";
	}
}