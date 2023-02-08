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
		if (this == other) return true;
		if (other == null || getClass() != other.getClass())
			return false;
		Thing otherThing = (Thing) other;
		return toString().equals(otherThing.toString());
	}
}