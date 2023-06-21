import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {
    private StringProperty firstName;
    private StringProperty lastName;
    private IntegerProperty age;

    public Person(String firstName, String lastName, int age) {
        setFirstName(firstName);
        setLastName(lastName);
        setAge(age);
    }

    public void setFirstName(String value) {
        firstNameProperty().set(value);
    }

    public String getFirstName() {
        return firstNameProperty().get();
    }

    public StringProperty firstNameProperty() {
        if (firstName == null) firstName = new SimpleStringProperty(this, "firstName");
        return firstName;
    }

    public void setLastName(String value) {
        lastNameProperty().set(value);
    }

    public String getLastName() {
        return lastNameProperty().get();
    }

    public StringProperty lastNameProperty() {
        if (lastName == null) lastName = new SimpleStringProperty(this, "lastName");
        return lastName;
    }

    public void setAge(int value) {
        ageProperty().set(value);
    }

    public int getAge() {
        return ageProperty().get();
    }

    public IntegerProperty ageProperty() {
        if (age == null) age = new SimpleIntegerProperty(this, "age");
        return age;
    }
}