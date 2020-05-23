package application;

public class ElectricMotors extends Car {
	
	private double batteryCapacity;
	private double milesPerCharge;
	private double chargingTime;
	
	public ElectricMotors(int id, String brand, String model, double price, int horsePower, int doors, int seats,
			int topSpeed, String transmission, double trunkSize, String breaksType, double batteryCapacity, double milesPerCharge, double chargingTime) {
		
		super(id, brand, model, price, horsePower, doors, seats, topSpeed, transmission, trunkSize, breaksType);
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
	
	//public chargerInstall()
	

}
