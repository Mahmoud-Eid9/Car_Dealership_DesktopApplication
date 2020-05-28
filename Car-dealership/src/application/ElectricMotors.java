package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.scene.image.Image;

public class ElectricMotors extends Car {
	
	private double batteryCapacity;
	private double milesPerCharge;
	private double chargingTime;
	
	public ElectricMotors(int id ,String brand, String model, double price, int horsePower, int doors, int seats,
			int topSpeed, String transmission, double trunkSize, String breaksType, double batteryCapacity, double milesPerCharge, double chargingTime,Image image) {
		
		super(id,brand, model, price, horsePower, doors, seats, topSpeed, transmission, trunkSize, breaksType,image);
		// TODO Auto-generated constructor stub
		this.batteryCapacity = batteryCapacity;
		this.milesPerCharge = milesPerCharge;
		this.chargingTime = chargingTime;
	}

	public double getBatteryCapacity() {
		return batteryCapacity;
	}

	public void setBatteryCapacity(double batteryCapacity) {
		this.batteryCapacity = batteryCapacity;
	}

	public double getMilesPerCharge() {
		return milesPerCharge;
	}

	public void setMilesPerCharge(double milesPerCharge) {
		this.milesPerCharge = milesPerCharge;
	}

	public double getChargingTime() {
		return chargingTime;
	}

	public void setChargingTime(double chargingTime) {
		this.chargingTime = chargingTime;
	}
	public int reserve(Customer customer) {
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "CAR_DEALERSHIP","348368");
	    	Statement stmt = con.createStatement();
	    	stmt.executeQuery("UPDATE CAR SET ACCOUNT_ID ="+customer.getId()+" WHERE ID ="+this.getid());
	    	customer.setReservation(1);
	    	return 1 ;
		}catch(Exception e) {
			return 0 ;
		}
	}	

}
