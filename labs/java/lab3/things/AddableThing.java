package things;

//то, что может добавлять вещь
//например, вещи могут быть на столе, на кровати или в комнате
public interface AddableThing {
	void addThings(Object ... things);
}