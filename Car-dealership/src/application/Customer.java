package application;

public class Customer extends Account {
	
	private boolean reservation;

	public Customer(String firstName, String lastName, int age, String userName, String password, boolean employee, boolean reservation) {
		super(firstName, lastName, age, userName, password, employee);
		// TODO Auto-generated constructor stub
	}

	public boolean isReservation() {
		return reservation;
	}

	public void setReservation(boolean reservation) {
		this.reservation = reservation;
	}
	
	

}
