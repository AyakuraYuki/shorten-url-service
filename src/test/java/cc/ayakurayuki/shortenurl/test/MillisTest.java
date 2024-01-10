package cc.ayakurayuki.shortenurl.test;

import cc.ayakurayuki.extension.date.LocalDateTimeUtils;
import java.time.LocalDateTime;
import java.time.Month;
import org.junit.jupiter.api.Test;

/**
 * @author Ayakura Yuki
 * @date 2024/01/10-15:27
 */
public class MillisTest {

  @Test
  public void testMillis() {
    LocalDateTime localDateTime = LocalDateTime.of(3000, Month.JANUARY, 1, 23, 59, 59);
    System.out.println(LocalDateTimeUtils.toSecond(localDateTime));
    System.out.println(LocalDateTimeUtils.toMillis(localDateTime));

    LocalDateTime now = LocalDateTime.now();
    System.out.println(now);
    System.out.println(LocalDateTimeUtils.toSecond(now));
    System.out.println(LocalDateTimeUtils.toMillis(now));
  }

}
