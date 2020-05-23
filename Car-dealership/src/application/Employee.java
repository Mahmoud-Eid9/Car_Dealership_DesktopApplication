package application;

public class Employee extends Account {
	//adeeny aho bgrab tany
	private String address;
	private String shift;
	
	public Employee(String firstName, String lastName, int age, String userName, String password, String address, String shift, boolean employee) {
		super(firstName, lastName, age, userName, password, employee);
		// TODO Auto-generated constructor stub
		this.address = address;
		this.shift = shift;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}
	
	
}
