package domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Train extends Transport{

  public static final String UNIQUE_JSON_IDENTIFIER = "number-wagons";

  private Integer numberWagons;

  private Integer wPassengerCapacity;

  public Train() {
  }

  @Override
  public int calculatePassengers() {
    return numberWagons * wPassengerCapacity;
  }

  @JsonProperty("number-wagons")
  public Integer getNumberWagons() {
    return numberWagons;
  }

  public void setNumberWagons(Integer numberWagons) {
    this.numberWagons = numberWagons;
  }

  @JsonProperty("w-passenger-capacity")
  public Integer getwPassengerCapacity() {
    return wPassengerCapacity;
  }

  public void setwPassengerCapacity(Integer wPassengerCapacity) {
    this.wPassengerCapacity = wPassengerCapacity;
  }
}
