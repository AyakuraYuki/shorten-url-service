package cc.ayakurayuki.extension.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Date;

/**
 * @author Ayakura Yuki
 * @date 2024/01/10-14:54
 */
public class DateTimestampTypeAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {

  @Override
  public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
    if (src == null) {
      return new JsonPrimitive(0);
    }
    return new JsonPrimitive(src.getTime());
  }

  @Override
  public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    return new Date(json.getAsJsonPrimitive().getAsLong());
  }

}
