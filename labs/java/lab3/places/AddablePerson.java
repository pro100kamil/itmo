package places;

//то, что может добавлять людей (где люди могут находиться)
//например, человек может находиться в комнате
public interface AddablePerson {
	void addPeople(Object ... people);
}