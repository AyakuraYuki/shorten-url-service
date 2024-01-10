package cc.ayakurayuki.shortenurl.infrastructure.errors;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author Ayakura Yuki
 * @date 2024/01/10-16:31
 */
public class ServerError extends RuntimeException {

  private Integer code;

  public ServerError(ServerCode serverCode) {
    super(serverCode.getMessage());
    this.code = serverCode.getCode();
  }

  public ServerError(Integer code, String message) {
    super(message);
    this.code = code;
  }

  public ServerError(Integer code, String message, Throwable cause) {
    super(message, cause);
    this.code = code;
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ServerError that = (ServerError) o;
    return Objects.equals(code, that.code);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", ServerError.class.getSimpleName() + "[", "]")
        .add("code=" + code)
        .toString();
  }

  public static ServerError code(int code, String message, Object... args) {
    if (args.length == 0) {
      return new ServerError(code, message);
    }
    return new ServerError(code, String.format(message, args));
  }

  public static ServerError code(ServerCode code, Object... args) {
    return code(code.getCode(), code.getMessage(), args);
  }

  public static ServerError code(ServerCode code, Throwable cause, Object... args) {
    return code(code.getCode(), code.getMessage(), cause, args);
  }

  public static ServerError code(int code, String message, Throwable cause, Object... args) {
    if (args.length == 0) {
      return new ServerError(code, message, cause);
    }
    return new ServerError(code, String.format(message, args), cause);
  }

}
