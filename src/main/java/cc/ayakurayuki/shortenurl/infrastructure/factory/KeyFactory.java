package cc.ayakurayuki.shortenurl.infrastructure.factory;

import java.security.SecureRandom;

/**
 * @author Ayakura Yuki
 * @date 2024/01/10-14:45
 */
public abstract class KeyFactory {

  private static final String chars = "ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678";

  public static String genKey(int length) {
    StringBuilder template = new StringBuilder(chars);
    int range = template.length();
    SecureRandom random = new SecureRandom();
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < length; i++) {
      stringBuilder.append(template.charAt(random.nextInt(range)));
    }
    return stringBuilder.toString();
  }

}
