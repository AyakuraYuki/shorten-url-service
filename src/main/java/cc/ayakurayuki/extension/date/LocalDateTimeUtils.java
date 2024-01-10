package cc.ayakurayuki.extension.date;

import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Ayakura Yuki
 * @date 2024/01/10-15:15
 */
public abstract class LocalDateTimeUtils {

  public static final String DATE_TIME_0 = "0000-00-00 00:00:00";

  public static final String FORMAT_HYPHEN_yyyy_MM_DD_HH_mm_SS = "yyyy-MM-dd HH:mm:ss";
  public static final String FORMAT_HYPHEN_yyyy_MM_DD_HH_mm    = "yyyy-MM-dd HH:mm";
  public static final String FORMAT_HYPHEN_yyyy_MM_DD          = "yyyy-MM-dd";
  public static final String FORMAT_SLASH_yyyy_MM_DD_HH_mm_SS  = "yyyy/MM/dd HH:mm:ss";
  public static final String FORMAT_SLASH_yyyy_MM_DD_HH_mm     = "yyyy/MM/dd HH:mm";
  public static final String FORMAT_SLASH_yyyy_MM_DD           = "yyyy/MM/dd";
  public static final String FORMAT_NOSEP_yyyy_MM_DD_HH_mm_SS  = "yyyyMMddHHmmss";
  public static final String FORMAT_NOSEP_yyyy_MM_DD_HH_mm     = "yyyyMMddHHmm";
  public static final String FORMAT_NOSEP_yyyy_MM_DD           = "yyyyMMdd";

  /**
   * 生肖名称
   */
  private static final String[] zodiacNames = {"猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊"};

  /**
   * 最大年份
   */
  public static final int MAX_YEAR = 9999;

  /**
   * 最大日期
   */
  public static final LocalDateTime MAX_DATE_TIME = LocalDateTime.of(LocalDate.of(MAX_YEAR, 12, 31), LocalTime.MAX);

  /**
   * 秒数转LocalDate
   */
  public static LocalDate second2LocalDate(long epochSecond) {
    return second2LocalDateTime(epochSecond).toLocalDate();
  }

  /**
   * 秒数转LocalDateTime
   */
  public static LocalDateTime second2LocalDateTime(long epochSecond) {
    return Instant.ofEpochSecond(epochSecond).atZone(ZoneId.systemDefault()).toLocalDateTime();
  }

  /**
   * LocalDate转秒数
   */
  public static long toSecond(LocalDate localDate) {
    if (localDate == null) {
      return -1L;
    }
    return toSecond(LocalDateTime.of(localDate, LocalTime.MIN));
  }

  /**
   * LocalDateTime转秒数
   */
  public static long toSecond(LocalDateTime localDateTime) {
    if (localDateTime == null) {
      return -1L;
    }
    return localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
  }

  /**
   * 毫秒数转LocalDate
   */
  public static LocalDate parseMillisToLocalDate(long epochMillis) {
    return Instant.ofEpochMilli(epochMillis).atZone(ZoneId.systemDefault()).toLocalDate();
  }

  /**
   * 毫秒数转LocalDateTime
   */
  public static LocalDateTime parseMillisToLocalDateTime(long epochMillis) {
    return Instant.ofEpochMilli(epochMillis).atZone(ZoneId.systemDefault()).toLocalDateTime();
  }

  public static long toMillis(LocalDate localDate) {
    if (localDate == null) {
      return -1L;
    }
    return toMillis(LocalDateTime.of(localDate, LocalTime.MIN));
  }

  /**
   * LocalDateTime转毫秒数
   */
  public static long toMillis(LocalDateTime localDateTime) {
    if (localDateTime == null) {
      return -1L;
    }
    return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
  }

  /**
   * 转换 LocalDateTime 到指定格式的字符串
   *
   * @param localDateTime {@link LocalDateTime} 类型的时间
   * @param format        格式，默认格式：yyyy-MM-dd HH:mm:ss
   *
   * @return 按格式转换后的时间字符串
   */
  public static String toString(LocalDateTime localDateTime, String format) {
    String parseFormat = Objects.toString(format, FORMAT_HYPHEN_yyyy_MM_DD_HH_mm_SS);
    DateTimeFormatter df = DateTimeFormatter.ofPattern(parseFormat);
    return df.format(localDateTime);
  }

  /**
   * 解析字符串到 LocalDateTime 时间
   *
   * @param localDateTime 时间字符串
   * @param format        格式，默认格式：yyyy-MM-dd HH:mm:ss
   *
   * @return {@link LocalDateTime} 类型的时间
   */
  public static LocalDateTime parse(String localDateTime, String format) {
    String parseFormat = Objects.toString(format, FORMAT_HYPHEN_yyyy_MM_DD_HH_mm_SS);
    DateTimeFormatter df = DateTimeFormatter.ofPattern(parseFormat);
    return LocalDateTime.parse(localDateTime, df);
  }

  /**
   * 解析 proto Timestamp 到 LocalDateTime 类型的时间
   *
   * @param timestamp {@code google.protobuf.Timestamp} 类型的对象
   *
   * @return {@link LocalDateTime} 类型的时间
   */
  public static LocalDateTime parseTimestampToLocalDateTime(Timestamp timestamp) {
    return Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos())
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime();
  }

  /**
   * 以当前服务器时间为准，计算真实年龄
   *
   * @param year  传入年份，例如：2000年1月25日 传入 2000
   * @param month 传入月份，例如：2000年1月25日 传入 1
   * @param day   传入日期，例如：2000年1月25日 传入 25
   *
   * @return 计算后的真实年龄
   */
  public static long getAge(Number year, Number month, Number day) {
    return getAge(LocalDateTime.now(), year, month, day);
  }

  /**
   * 以传入的特定时间为准，计算真实年龄
   *
   * @param referenceTime 参照时间
   * @param year          传入年份，例如：2000年1月25日 传入 2000
   * @param month         传入月份，例如：2000年1月25日 传入 1
   * @param day           传入日期，例如：2000年1月25日 传入 25
   *
   * @return 计算后的真实年龄
   */
  public static long getAge(LocalDateTime referenceTime, Number year, Number month, Number day) {
    if (referenceTime == null || year == null || year.equals(0)) {
      return 0;
    }
    var nowYear = referenceTime.getYear();
    var nowMonth = referenceTime.getMonthValue();
    var nowDay = referenceTime.getDayOfMonth();
    var age = nowYear - year.intValue();
    if (age <= 0) {
      return 0;
    }
    if (month == null || nowMonth > month.intValue()) {
      return age;
    }
    if (day == null || nowDay > day.intValue()) {
      return age;
    }
    return age - 1;
  }

  // 星座名称，开始的空字符串占住索引的 0 位，把索引 0 位消除
  private static final String[] constellationNames = {"", "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座"};

  // 星座边界值，按各月顺序排序，开始的 -1 表示索引占位，把索引 0 位消除
  private static final List<Integer> constellationEdgeDayArray = List.of(-1, 20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22);

  /**
   * 获取星座
   *
   * @param year  年
   * @param month 月
   * @param day   日
   *
   * @return 星座名称
   */
  public static String constellation(Number year, Number month, Number day) {
    if (year == null || month == null || day == null) {
      return "";
    }
    var date = LocalDate.of(year.intValue(), month.intValue(), day.intValue());
    return doGetConstellation(date.getMonthValue(), date.getDayOfMonth());
  }

  /**
   * 获取星座
   *
   * @param localDateTime 时间
   *
   * @return 星座名称
   */
  public static String constellation(LocalDateTime localDateTime) {
    if (localDateTime == null) {
      return "";
    }
    var date = localDateTime.toLocalDate();
    return doGetConstellation(date.getMonthValue(), date.getDayOfMonth());
  }

  /**
   * 获取星座
   *
   * @param localDate 时间
   *
   * @return 星座名称
   */
  public static String constellation(LocalDate localDate) {
    if (localDate == null) {
      return "";
    }
    return doGetConstellation(localDate.getMonthValue(), localDate.getDayOfMonth());
  }

  private static String doGetConstellation(int monthValue, int dayOfMonth) {
    var edge = constellationEdgeDayArray.get(monthValue);
    if (dayOfMonth < edge) {
      monthValue -= 1; // 日小于当月边界值，当前一月来看
    }
    if (monthValue <= 0) {
      return constellationNames[12]; // 如果 m <= 0，表示回到上一年最后一月
    }
    return constellationNames[monthValue];
  }

  public static Date toDate(LocalDateTime localDateTime) {
    if (localDateTime == null) {
      return null;
    }
    return new Date(toMillis(localDateTime));
  }

  public static UnixTimeUnit guessUnixTimeUnit(long value) {
    long nowMillis = System.currentTimeMillis();
    long nowSeconds = nowMillis / 1000;

    int lengthOfValue = String.valueOf(value).length();
    int lengthOfNowMillis = String.valueOf(nowMillis).length();
    int lengthOfNowSeconds = String.valueOf(nowSeconds).length();
    if (lengthOfValue <= lengthOfNowSeconds) {
      return UnixTimeUnit.Unix;
    } else if (lengthOfValue <= lengthOfNowMillis) {
      return UnixTimeUnit.UnixMillis;
    }
    return null;
  }

}
