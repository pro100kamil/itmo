package things;

public abstract class Thing {
	abstract public String getName();
	
	@Override
	public String toString() {
		return getName();
	}
	
	@Override
	public int hashCode() {
		return toString().hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if (getClass() != other.getClass()) {
			return false;
		}
		return hashCode() == other.hashCode();
	}
}