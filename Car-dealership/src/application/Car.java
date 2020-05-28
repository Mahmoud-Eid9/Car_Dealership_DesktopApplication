package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.scene.image.Image;

public class Car {
	
	private int id;
	private String brand;
	private String model;
	private double price;
	private int horsePower;
	private int doors;
	private int seats;
	private int topSpeed;
	private String transmission;
	private double trunkSize;
	private String breaksType;
	private Image image;
	
	
	
	public Car( int id,String brand, String model, double price, int horsePower, int doors, int seats, int topSpeed,
			String transmission, double trunkSize, String breaksType, Image image) {
		super();
		this.id = id ;
		this.brand = brand;
		this.model = model;
		this.price = price;
		this.horsePower = horsePower;
		this.doors = doors;
		this.seats = seats;
		this.topSpeed = topSpeed;
		this.transmission = transmission;
		this.trunkSize = trunkSize;
		this.breaksType = breaksType;
		this.image = image;
	}
	
	public int getid() {
		return this.id;
	}
	public void setoid(int id) {
		this.id = id;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getHorsePower() {
		return horsePower;
	}
	public void setHorsePower(int horsePower) {
		this.horsePower = horsePower;
	}
	public int getDoors() {
		return doors;
	}
	public void setDoors(int doors) {
		this.doors = doors;
	}
	public int getSeats() {
		return seats;
	}
	public void setSeats(int seats) {
		this.seats = seats;
	}
	public int getTopSpeed() {
		return topSpeed;
	}
	public void setTopSpeed(int topSpeed) {
		this.topSpeed = topSpeed;
	}
	public String getTransmission() {
		return transmission;
	}
	public void setTransmission(String transmission) {
		this.transmission = transmission;
	}
	public double getTrunkSize() {
		return trunkSize;
	}
	public void setTrunkSize(double trunkSize) {
		this.trunkSize = trunkSize;
	}
	public String getBreaksType() {
		return breaksType;
	}
	public void setBreaksType(String breaksType) {
		this.breaksType = breaksType;
	}
	public Image getImage() {
		return this.image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public int reserve(Customer customer, String date, String date2) {
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "CAR_DEALERSHIP","348368");
	    	Statement stmt = con.createStatement();
	    	stmt.executeQuery("UPDATE CAR SET ACCOUNT_ID ="+customer.getId()+" WHERE ID ="+this.id);
	    	customer.setReservation(1, date);
	    	return 1 ;
		}catch(Exception e) {
			return 0 ;
		}
	}
	
	//public reserve()
	

}
