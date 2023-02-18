package models;

import java.time.LocalDateTime;

/**
 * ����� ���������
 */
public class Worker implements Comparable <Worker> {
    private Integer id; //���� �� ����� ���� null, �������� ���� ������ ���� ������ 0, �������� ����� ���� ������ ���� ����������, �������� ����� ���� ������ �������������� �������������
    private String name; //���� �� ����� ���� null, ������ �� ����� ���� ������
    private Coordinates coordinates; //���� �� ����� ���� null
    public java.time.LocalDateTime creationDate; //���� �� ����� ���� null, �������� ����� ���� ������ �������������� �������������
    private Float salary; //���� ����� ���� null, �������� ���� ������ ���� ������ 0
    private Position position; //���� ����� ���� null
    private Status status; //���� ����� ���� null
	
    private Person person; //���� �� ����� ���� null
	
	private static int curId = 1;
	
	public Worker(String name, Coordinates coordinates, Float salary, Position position, Status status, Person person) {
		id = curId++;
		this.name = name;
		this.coordinates = coordinates;
		creationDate = LocalDateTime.now();
		this.salary = salary;
		this.position = position;
		this.status = status;
		this.person = person;
	}
	
	public Integer getId() {
		return id;
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
	
	/**
     * �������� �������� ���������, ���� �������� ���������
	 * @param salary ������ � ���������
	 * @return boolean true - �������� ���������, false - �� ���������
     */
	public boolean setSalary(String salary) {
		if (salary.isEmpty()) {
			this.salary = null;
			return true;
		}
		try {
			this.salary = Float.parseFloat(salary);
		}
		catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	public void setPosition(Position position) {
		this.position = position;
	}	
	
	/**
     * �������� ������� ���������, ���� ������� ���������
	 * @param position ������ � ��������
	 * @return boolean true - ������� ���������, false - �� ���������
     */
	public boolean setPosition(String position) {
		if (position.isEmpty()) {
			this.position = null;
			return true;
		}
		try {
			this.position = Position.valueOf(position);
		}
		catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	/**
     * �������� ������ ���������, ���� ������ ����������
	 * @param status ������ �� ��������
	 * @return boolean true - ������ ����������, false - ������������
     */
	public boolean setStatus(String status) {
		if (status.isEmpty()) {
			this.status = null;
			return true;
		}
		try {
			this.status = Status.valueOf(status);
		}
		catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}
	
	public void setPerson(Person person) {
		this.person = person;
	}
	
	public static void moveCurId(Integer newId) {
		if (newId.compareTo(curId) > 0)
			curId = newId;
	}
	
	public boolean validate() {
		if (id <= 0) {
			return false;
		}
		if (name == null || name.isBlank()) {
			return false;
		}
		if (coordinates == null) {
			return false;
		}
		if (creationDate == null) {
			return false;
		}
		if (salary <= 0) {
			return false;
		}
		if (person == null) {
			return false;
		}
		return coordinates.validate() && person.validate();
	}
	
    /**
     * �������� ���� ��������� �� ������ ������� ���������
	 * @param worker ��������, ���� �������� �� ������ ��� ��������
     */
	public void update(Worker worker) {
		setName(worker.getName());
		setCoordinates(worker.getCoordinates());
		setSalary(worker.getSalary());
		setPosition(worker.getPosition());
		setStatus(worker.getStatus());
		setPerson(worker.getPerson());
	}
   
    /**
     * ������� ������ ���������� � ���������
     */
	public void getInfo() {
		System.out.println("id: " + id);
		System.out.println("name: " + name);
		System.out.println("coordinates: " + coordinates.getX() + ", " + coordinates.getY());
		System.out.println("salary: " + salary);
		System.out.println("position: " + position);
		System.out.println("status: " + status);
	}
   
    @Override
    public int compareTo(Worker other) {
		//���������� ���������� �� ��������
		if (salary == null && other.getSalary() == null) {
			return 0;
		}
		if (salary == null) {
			return -1;
		}
		if (other.getSalary() == null) {
			return 1;
		}
		float delta = salary.floatValue() - other.getSalary().floatValue();
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
		return "Worker " + id + " (" + name + ")";
	}
}