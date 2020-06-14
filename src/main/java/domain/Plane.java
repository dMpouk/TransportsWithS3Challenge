package domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Plane extends Transport {

  public static final String UNIQUE_JSON_IDENTIFIER = "b-passenger-capacity";

  private Integer bPassengerCapacity;

  private Integer ePassengerCapacity;

  public Plane() {
  }

  @Override
  public int calculatePassengers() {
    return bPassengerCapacity + ePassengerCapacity;
  }

  @JsonProperty("b-passenger-capacity")
  public Integer getbPassengerCapacity() {
    return bPassengerCapacity;
  }

  public void setbPassengerCapacity(Integer bPassengerCapacity) {
    this.bPassengerCapacity = bPassengerCapacity;
  }

  @JsonProperty("e-passenger-capacity")
  public Integer getePassengerCapacity() {
    return ePassengerCapacity;
  }

  public void setePassengerCapacity(Integer ePassengerCapacity) {
    this.ePassengerCapacity = ePassengerCapacity;
  }
}
