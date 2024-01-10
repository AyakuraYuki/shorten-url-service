package cc.ayakurayuki.shortenurl.infrastructure.errors;

/**
 * @author Ayakura Yuki
 * @date 2024/01/10-16:32
 */
public class ServerCodes {

  public static boolean is5xx(int code) {
    code = Math.abs(code);
    return code >= 500 && code < 600;
  }

  /**
   * @param code business code or http status code
   *
   * @return
   */
  public static boolean isInformationOrSuccess(int code) {
    return code == 0 || (code >= 100 && code < 300);
  }

}
