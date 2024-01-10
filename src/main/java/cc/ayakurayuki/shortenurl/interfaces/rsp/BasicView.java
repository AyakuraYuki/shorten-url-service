package cc.ayakurayuki.shortenurl.interfaces.rsp;

import cc.ayakurayuki.shortenurl.infrastructure.errors.ServerCode;
import java.util.Collections;
import java.util.Map;

/**
 * @author Ayakura Yuki
 * @date 2024/01/10-16:52
 */
public class BasicView<T> extends BasicRsp {

  private T data;

  public BasicView(T data) {
    this(0, "", data);
  }

  public BasicView(Integer code, T data) {
    this(code, "", data);
  }

  public BasicView(Integer code, String message, T data) {
    super(code, message);
    this.data = data;
  }

  BasicView(ServerCode serverCode) {
    this(serverCode, null);
  }

  BasicView(ServerCode serverCode, T data) {
    super(serverCode.getCode(), serverCode.getMessage());
    this.data = data;
  }

  public static <T> BasicView<T> create(ServerCode serverCode) {
    return new BasicView<>(serverCode);
  }

  public static <T> BasicView<T> create(ServerCode serverCode, T data) {
    return new BasicView<>(serverCode, data);
  }

  public static <T> BasicView<T> create(int code, String message) {
    return new BasicView<>(code, message, null);
  }

  public static <T> BasicView<T> create(int code, String message, T data) {
    return new BasicView<>(code, message, data);
  }

  public static <T> BasicView<T> success(T data) {
    return new BasicView<>(ServerCode.SUCCESS, data);
  }

  public static BasicView<Map<String, Object>> success() {
    return new BasicView<>(Collections.emptyMap());
  }

}
