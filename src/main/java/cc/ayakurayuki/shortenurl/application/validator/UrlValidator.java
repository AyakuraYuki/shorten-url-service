package cc.ayakurayuki.shortenurl.application.validator;

import cc.ayakurayuki.extension.crypto.MD5;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ayakura Yuki
 * @date 2024/01/10-14:49
 */
public abstract class UrlValidator {

  // url pattern for validating url linkPo
  private static final Pattern      URL_PATTERN = Pattern.compile("^http(s)?://(.*@)?([\\w-]+\\.)*[\\w-]+([_\\-.,~!*:#()\\w/?%&=]*)?$");
  // host white list
  public static final  List<String> WHITE_LIST  = List.of("ayakurayuki.cc");

  public static boolean checkURL(String url) {
    Matcher matcher = URL_PATTERN.matcher(url);
    if (matcher.matches()) {
      return url.startsWith("h");
    } else {
      return false;
    }
  }

  public static boolean checkWhiteList(String host) {
    for (String h : WHITE_LIST) {
      if (host.equals(h) || host.endsWith("." + h)) {
        return true;
      }
    }
    return false;
  }

  public static boolean checkHash(String url, String hash) {
    if (hash == null || hash.isEmpty()) {
      return false;
    }
    String md5 = MD5.digestToString(url);
    return hash.equalsIgnoreCase(md5);
  }

}
