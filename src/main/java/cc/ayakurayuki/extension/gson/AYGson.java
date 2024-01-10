package cc.ayakurayuki.extension.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Date;

/**
 * @author Ayakura Yuki
 * @date 2024/01/10-14:53
 */
public abstract class AYGson {

  public static Gson create() {
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.disableHtmlEscaping();
    registerTypeAdapters(gsonBuilder);
    return gsonBuilder.create();
  }

  public static Gson pretty() {
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.setPrettyPrinting();
    gsonBuilder.disableHtmlEscaping();
    registerTypeAdapters(gsonBuilder);
    return gsonBuilder.create();
  }

  private static void registerTypeAdapters(GsonBuilder gsonBuilder) {
    gsonBuilder.registerTypeAdapter(Date.class, new DateTimestampTypeAdapter());
  }

}
