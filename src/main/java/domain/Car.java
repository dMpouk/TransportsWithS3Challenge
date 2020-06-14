package domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Car extends Transport{

  public static final String UNIQUE_JSON_IDENTIFIER = "manufacturer";

  private String manufacturer;

  private Integer passengerCapacity;

  public Car() {
  }

  @Override
  public int calculatePassengers() {
    return passengerCapacity;
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  @JsonProperty("passenger-capacity")
  public Integer getPassengerCapacity() {
    return passengerCapacity;
  }

  public void setPassengerCapacity(Integer passengerCapacity) {
    this.passengerCapacity = passengerCapacity;
  }
}
