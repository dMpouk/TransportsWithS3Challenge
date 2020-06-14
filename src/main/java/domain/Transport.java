package domain;

public abstract class Transport {

  private String model;

  public abstract int calculatePassengers();

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }
}
