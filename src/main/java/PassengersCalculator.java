import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import domain.Car;
import domain.Plane;
import domain.Train;
import domain.Transport;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PassengersCalculator {

  private static final Logger LOGGER = LoggerFactory.getLogger(PassengersCalculator.class);

  public String calculatePassengers(String jsonInput) {
    //The responsibility of parsing could be written outside the calculate method, however currently this way serves for simpler client code.
    List<Transport> transports = parseJson(jsonInput);

    int planesPassengers = 0;
    int trainsPassengers = 0;
    int carsPassengers = 0;
    for (Transport transport : transports) {
      if(transport.getClass().equals(Plane.class)){
        planesPassengers += transport.calculatePassengers();
      }
      else if(transport.getClass().equals(Train.class)){
        trainsPassengers += transport.calculatePassengers();
      }
      else if(transport.getClass().equals(Car.class)){
        carsPassengers += transport.calculatePassengers();
      }
    }

    return createJsonOutput(planesPassengers, trainsPassengers, carsPassengers);
  }

  public String readJson(String path) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      JsonNode rootNode = objectMapper.readTree(Paths.get(path).toFile());
      return rootNode.toString();
    } catch (IOException e) {
      LOGGER.error("Error while reading Json from file. {}",e.getMessage());
      throw new RuntimeException("Error while reading Json from file.");
    }
  }

  private List<Transport> parseJson(String json) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      JsonNode rootNode = objectMapper.readTree(json);
      JsonNode transportNode = rootNode.path("transports");
      List<Transport> transports = new ArrayList<>();
      if (transportNode.isArray()) {
        for (JsonNode childNode : transportNode) {
          Transport transport = mapToTheRightSubTransport(objectMapper, childNode);
          transports.add(transport);
        }
      }
      return transports;
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Error while parsing json.");
    }
  }

  private Transport mapToTheRightSubTransport(ObjectMapper objectMapper, JsonNode childNode) {
    Transport transport = null;
    if(childNode.has(Plane.UNIQUE_JSON_IDENTIFIER)){
      transport = objectMapper.convertValue(childNode, Plane.class);
    }else if(childNode.has(Train.UNIQUE_JSON_IDENTIFIER)){
      transport = objectMapper.convertValue(childNode, Train.class);
    }else if(childNode.has(Car.UNIQUE_JSON_IDENTIFIER)){
      transport = objectMapper.convertValue(childNode, Car.class);
    }
    return transport;
  }

  private String createJsonOutput(int planesPassengers, int trainsPassengers, int carsPassengers) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      ObjectNode resultNode = mapper.createObjectNode();
      resultNode.put("planes", planesPassengers);
      resultNode.put("trains", trainsPassengers);
      resultNode.put("cars", carsPassengers);
      return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(resultNode);
    } catch (JsonProcessingException e) {
      LOGGER.error("Error while creating Json Output. {}",e.getMessage());
      return "Error while creating Json Output";
    }
  }
}
