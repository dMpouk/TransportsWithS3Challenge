package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class JsonExampleFactory {

  private final static Logger LOGGER = LoggerFactory.getLogger(JsonExampleFactory.class);

  private JsonExampleFactory() {
  }

  public static String createJsonExample(JsonExampleCase exampleCase, int numberOfEachTransport) {
    try {
      ObjectMapper mapper = new ObjectMapper();

      ObjectNode root = mapper.createObjectNode();
      ArrayNode transports = mapper.createArrayNode();

      List<ObjectNode> currentExampleNodes = new ArrayList<>();

      for (int i = 0; i < numberOfEachTransport; i++) {
        if (exampleCase.equals(JsonExampleCase.PLANE)
            || exampleCase.equals(JsonExampleCase.CAR_PLANE)
            || exampleCase.equals(JsonExampleCase.TRAIN_PLANE)
            || exampleCase.equals(JsonExampleCase.CAR_TRAIN_PLANE)) {
          ObjectNode plane = mapper.createObjectNode();
          plane.put("model", "Boeing 777");
          plane.put("b-passenger-capacity", 14);
          plane.put("e-passenger-capacity", 300);
          currentExampleNodes.add(plane);
        }

        if (exampleCase.equals(JsonExampleCase.CAR)
            || exampleCase.equals(JsonExampleCase.CAR_PLANE)
            || exampleCase.equals(JsonExampleCase.CAR_TRAIN)
            || exampleCase.equals(JsonExampleCase.CAR_TRAIN_PLANE)) {
          ObjectNode car = mapper.createObjectNode();
          car.put("manufacturer", "BMW");
          car.put("model", "M3");
          car.put("passenger-capacity", 4);
          currentExampleNodes.add(car);
        }

        if (exampleCase.equals(JsonExampleCase.TRAIN)
            || exampleCase.equals(JsonExampleCase.TRAIN_PLANE)
            || exampleCase.equals(JsonExampleCase.CAR_TRAIN)
            || exampleCase.equals(JsonExampleCase.CAR_TRAIN_PLANE)) {
          ObjectNode train = mapper.createObjectNode();
          train.put("model", "ICE");
          train.put("number-wagons", 5);
          train.put("w-passenger-capacity", 30);
          currentExampleNodes.add(train);
        }
      }

      transports.addAll(currentExampleNodes);

      root.set("transports", transports);

      String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);

      return json;
    } catch (JsonProcessingException e) {
      LOGGER.error("There was an error while creating JSON examples {}", e.getMessage());
      throw new RuntimeException("There was an error while creating JSON examples");
    }
  }

}
