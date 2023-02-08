package things;

//то, что может включаться/выключаться (2 состояния)
public interface OnOffable {
	boolean is_work();
	void changeStatus();
}