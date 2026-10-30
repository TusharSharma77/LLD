

public abstract class Vehicle{

    private String VehicleNumber;
    private VehicleType vehicleType;
    public Vehicle(String vehicleNumber, VehicleType vehicleType) {
        this.VehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
    }

    public String getVehicleNumber(){
        return VehicleNumber;
    }

    public VehicleType getVehicleType(){
        return vehicleType;
    }
}