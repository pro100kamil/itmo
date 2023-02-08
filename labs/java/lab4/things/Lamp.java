package things;

public class Lamp extends Thing implements OnOffable {
	private boolean work = false;  // светит ли лампа в текущий момент
	
	@Override
	public void changeStatus() { 
		work = !work;
		System.out.println(toString());
	}
	
	public void changeStatus(boolean work) { 
		this.work = work;
		System.out.println(toString());
	}
	
	@Override
	public boolean is_work() {
		return work;
	}
	
	public String getStrStatus() {
		return "is " + (work ? "on" : "off");
	}
	
	@Override
	public String getName() {
		return "Lamp";
	}
	
	@Override
	public String toString() {
		return "Lamp " + getStrStatus();
	}
}