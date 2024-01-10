package cc.ayakurayuki.extension.gson;

import com.google.gson.Gson;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import org.junit.jupiter.api.Test;

/**
 * @author Ayakura Yuki
 * @date 2024/01/10-15:43
 */
public class AYGsonTest {

  public record Foo(
      LocalDateTime localDateTime,
      LocalTime localTime,
      Date date
  ) {}

  @Test
  public void testTypeAdapters() {
    Gson gson = AYGson.pretty();

    Foo foo = new Foo(LocalDateTime.now(), LocalTime.now(), new Date());
    String prettyJson = gson.toJson(foo);
    System.out.println(prettyJson);
    System.out.println();

    Foo newFoo = gson.fromJson(prettyJson, Foo.class);
    System.out.println(newFoo.localDateTime);
    System.out.println(newFoo.localTime);
    System.out.printf("%s, %d%n", newFoo.date, newFoo.date.getTime());
  }

}
