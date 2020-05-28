package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Customer extends Account {
	
	private boolean reservation;

	public Customer(int id ,String firstName, String lastName, int age, String userName, String password, boolean employee, boolean reservation) {
		super(id,firstName, lastName, age, userName, password, employee);
		// TODO Auto-generated constructor stub
		this.reservation = reservation ;
	}
	public Customer() {
		
	}

	public boolean getReservation() {
		return reservation;
	}

	public void setReservation(int reservation) {
		try {
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "CAR_DEALERSHIP","348368");
    	Statement stmt = con.createStatement();
    	stmt.executeQuery("UPDATE Account SET RESERVATION ="+reservation+" WHERE ID ="+this.getId());
    	this.reservation = true ;
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	

}
