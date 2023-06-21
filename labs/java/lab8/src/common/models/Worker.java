package common.models;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Класс работника
 */
public class Worker implements Comparable<Worker>, Serializable {
    private Integer id = 0; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    //id = 0 изначально, чтоб понимать, что ему не задали значение на сервере
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    public java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Float salary; //Поле может быть null, Значение поля должно быть больше 0
    private Position position; //Поле может быть null
    private Status status; //Поле может быть null
    private Person person; //Поле не может быть null
    private int creatorId;

    public Worker(String name, Coordinates coordinates, Float salary, Position position, Status status, Person person) {
        this.name = name;
        this.coordinates = coordinates;
        creationDate = LocalDateTime.now();
        this.salary = salary;
        this.position = position;
        this.status = status;
        this.person = person;
    }

    public Worker(Integer id, String name, Coordinates coordinates, LocalDateTime creationDate, Float salary,
                  Position position, Status status, Person person) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.salary = salary;
        this.position = position;
        this.status = status;
        this.person = person;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Float getSalary() {
        return salary;
    }

    public Position getPosition() {
        return position;
    }

    public Status getStatus() {
        return status;
    }

    public Person getPerson() {
        return person;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * Проверка корректности всех полей
     */
    public boolean validate() {
        if (name == null || name.isBlank()) {
            return false;
        }
        if (coordinates == null) {
            return false;
        }
        if (creationDate == null) {
            return false;
        }
        if (salary != null && salary <= 0) {
            return false;
        }
        if (person == null) {
            return false;
        }
        return coordinates.validate() && person.validate();
    }

    /**
     * Изменяет поля работника на основе другого работника
     *
     * @param worker работник, поля которого мы возьмём для текущего
     */
    public void update(Worker worker) {
        setName(worker.getName());
        setCoordinates(worker.getCoordinates());
        setSalary(worker.getSalary());
        setPosition(worker.getPosition());
        setStatus(worker.getStatus());
        setPerson(worker.getPerson());
    }

    @Override
    public int compareTo(Worker other) {
        //сравниваем работников по зарплате
        if (salary == null && other.getSalary() == null) {
            return 0;
        }
        if (salary == null) {
            return -1;
        }
        if (other.getSalary() == null) {
            return 1;
        }
        float delta = salary - other.getSalary();
        if (delta > 0) {
            return 1;
        }
        if (delta == 0) {
            return 0;
        }
        return -1;
    }

    @Override
    public String toString() {
        return "Worker{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", coordinates=" + coordinates +
               ", creationDate=" + creationDate +
               ", salary=" + salary +
               ", position=" + position +
               ", status=" + status +
               ", person=" + person +
               ", creatorId=" + creatorId +
               '}';
    }
}