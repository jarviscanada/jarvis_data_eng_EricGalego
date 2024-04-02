package ca.jrvs.apps.trading.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class JsonParser {

  public static String toJson(Object object)
      throws JsonProcessingException {

    ObjectMapper m = new ObjectMapper();
    try {
      return m.writeValueAsString(object);
    } catch (JsonProcessingException e) {
//      logger.error("Cannot serialize " + object.getClass().getCanonicalName() + " to JSON string");
      throw e;
    }
  }

  public static <T> T toObjectFromJson(String json, Class clazz) throws IOException {
    ObjectMapper m = new ObjectMapper();
    m.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    try {
      return (T) m.readValue(json, clazz);
    } catch (IOException e) {
      //  logger.error("Cannot parse JSON string as a " + clazz.getCanonicalName());
      throw e;
    }
  }
}
