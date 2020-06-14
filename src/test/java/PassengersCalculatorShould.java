import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import s3.GetObject;
import utils.JsonExampleCase;
import utils.JsonExampleFactory;

public class PassengersCalculatorShould {

  private final static Logger LOGGER = LoggerFactory.getLogger(PassengersCalculatorShould.class);

  PassengersCalculator passengersCalculator;

  @BeforeEach
  public void setUp(){
    this.passengersCalculator = new PassengersCalculator();
  }

  @Test
  public void parseAJsonObjectToCarAndCalculateCarPassengers(){
    String jsonExample = JsonExampleFactory.createJsonExample(JsonExampleCase.CAR,1);
    String resultJson = this.passengersCalculator.calculatePassengers(jsonExample);
    String expectedJson = "{\r\n" +
        "  \"planes\" : 0,\r\n" +
        "  \"trains\" : 0,\r\n" +
        "  \"cars\" : 4\r\n"
        + "}";
    assertEquals(expectedJson,resultJson);
  }

  @Test
  public void parseAJsonObjectToTrainAndCalculateTrainPassengers(){
    String jsonExample = JsonExampleFactory.createJsonExample(JsonExampleCase.TRAIN,1);
    String resultJson = this.passengersCalculator.calculatePassengers(jsonExample);
    String expectedJson = "{\r\n" +
        "  \"planes\" : 0,\r\n" +
        "  \"trains\" : 150,\r\n" +
        "  \"cars\" : 0\r\n"
        + "}";
    assertEquals(expectedJson,resultJson);
  }

  @Test
  public void parseAJsonObjectToPlaneAndCalculatePlanePassengers(){
    String jsonExample = JsonExampleFactory.createJsonExample(JsonExampleCase.PLANE, 1);
    String resultJson = this.passengersCalculator.calculatePassengers(jsonExample);
    String expectedJson = "{\r\n" +
        "  \"planes\" : 314,\r\n" +
        "  \"trains\" : 0,\r\n" +
        "  \"cars\" : 0\r\n"
        + "}";
    assertEquals(expectedJson,resultJson);
  }

  @Test
  public void parseAJsonObjectOfEachKindAndCalculateAllPassengers(){
    String jsonExample = JsonExampleFactory.createJsonExample(JsonExampleCase.CAR_TRAIN_PLANE, 1);
    String resultJson = this.passengersCalculator.calculatePassengers(jsonExample);
    String expectedJson = "{\r\n" +
        "  \"planes\" : 314,\r\n" +
        "  \"trains\" : 150,\r\n" +
        "  \"cars\" : 4\r\n"
        + "}";
    assertEquals(expectedJson,resultJson);
  }

  @Test
  public void parseAJsonObjectOfEachKindAndCalculateAllPassengersTwoTimesEachTransport(){
    String jsonExample = JsonExampleFactory.createJsonExample(JsonExampleCase.CAR_TRAIN_PLANE, 2);
    String resultJson = this.passengersCalculator.calculatePassengers(jsonExample);
    String expectedJson = "{\r\n" +
        "  \"planes\" : 628,\r\n" +
        "  \"trains\" : 300,\r\n" +
        "  \"cars\" : 8\r\n"
        + "}";
    assertEquals(expectedJson,resultJson);
  }

  @Test
  public void parseAJsonObjectOfEachKindAndCalculateAllPassengersHundredTimesEachTransport(){
    String jsonExample = JsonExampleFactory.createJsonExample(JsonExampleCase.CAR_TRAIN_PLANE, 100);
    String resultJson = this.passengersCalculator.calculatePassengers(jsonExample);
    String expectedJson = "{\r\n" +
        "  \"planes\" : 31400,\r\n" +
        "  \"trains\" : 15000,\r\n" +
        "  \"cars\" : 400\r\n"
        + "}";
    assertEquals(expectedJson,resultJson);
  }

  @Test
  public void parseAJsonFileFromFileAndCalculateCarPassengers(){
    String jsonExample = this.passengersCalculator.readJson("transports.json");
    String resultJson = this.passengersCalculator.calculatePassengers(jsonExample);
    String expectedJson = "{\r\n" +
        "  \"planes\" : 314,\r\n" +
        "  \"trains\" : 150,\r\n" +
        "  \"cars\" : 4\r\n"
        + "}";
    assertEquals(expectedJson,resultJson);
  }

  @Test
  public void parseAJsonFileFromS3BucketAndCalculateCarPassengers(){
    try {
      String jsonExample = GetObject.getSelectedObject(GetObject.getProperty("bucket.name"), GetObject.getProperty("bucket.file.key"));
      String resultJson = this.passengersCalculator.calculatePassengers(jsonExample);
      String expectedJson = "{\r\n" +
          "  \"planes\" : 314,\r\n" +
          "  \"trains\" : 150,\r\n" +
          "  \"cars\" : 4\r\n"
          + "}";
      assertEquals(expectedJson,resultJson);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
