package cc.ayakurayuki.shortenurl.infrastructure.errors;

import com.google.common.base.Strings;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Ayakura Yuki
 * @date 2024/01/10-16:31
 */
public class ServerCode {

  private static final Map<Integer, String> CODES = new ConcurrentHashMap<>();

  public static final ServerCode SUCCESS              = asCode(0); // success
  public static final ServerCode APP_NOT_EXIST        = asCode(-1); // 应用程序不存在或已被封禁
  public static final ServerCode SIGN_ERROR           = asCode(-3); // API校验密匙错误
  public static final ServerCode NOT_LOGIN            = asCode(-101); // 未登录
  public static final ServerCode ACCOUNT_BANED        = asCode(-102); // 账号被封停
  public static final ServerCode TOKEN_ILLEGAL        = asCode(-111); // csrf 校验失败
  public static final ServerCode SERVICE_UPDATE       = asCode(-112); // 系统升级中，敬请谅解
  public static final ServerCode NOT_MODIFIED         = asCode(-304); // 木有改动
  public static final ServerCode BAD_REQUEST          = asCode(-400); // 请求错误
  public static final ServerCode UNAUTHORIZED         = asCode(-401); // 未授权
  public static final ServerCode FORBIDDEN            = asCode(-403); // 访问权限不足
  public static final ServerCode NOT_FOUND            = asCode(-404); // 啥都木有
  public static final ServerCode METHOD_NOT_SUPPORT   = asCode(-405); // 不支持该方法
  public static final ServerCode CONFLICT             = asCode(-409); // 请求冲突
  public static final ServerCode TOO_MANY_REQUESTS    = asCode(-429); // 请求过快
  public static final ServerCode CLIENT_CLOSE_REQUEST = asCode(-499); // 请求被关闭

  public static final ServerCode SERVER_ERROR        = asCode(-500); // 服务器错误
  public static final ServerCode NOT_IMPLEMENTED     = asCode(-501); // 服务未实现
  public static final ServerCode BAD_CONNECTION      = asCode(-502); // 连接错误
  public static final ServerCode SERVICE_UNAVAILABLE = asCode(-503); // 服务暂不可用
  public static final ServerCode DEADLINE_EXCEEDED   = asCode(-504); // 处理超时
  public static final ServerCode LIMIT_EXCEEDED      = asCode(-509); // 请求过快


  public static final ServerCode FAIL_FILE_NOT_EXIST = asCode(-616); // 上传文件不存在
  public static final ServerCode FAIL_FILE_TOO_LARGE = asCode(-617); // 上传文件太大

  static {
    CODES.putAll(defaults());
  }

  private final int    code;
  private final String message;

  private ServerCode(int code) {
    this(code, null);
  }

  private ServerCode(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  public String getMessage() {
    if (Strings.isNullOrEmpty(this.message)) {
      return CODES.getOrDefault(this.code, String.valueOf(this.code));
    }
    return this.message;
  }

  public static ServerCode create(int code, String message) {
    return new ServerCode(code, message);
  }

  public static ServerCode asCode(int code) {
    return new ServerCode(code);
  }

  public static void addCodes(Map<Integer, String> codes) {
    CODES.putAll(codes);
  }


  private static Map<Integer, String> defaults() {
    Map<Integer, String> codes = new HashMap<>();
    codes.put(SUCCESS.getCode(), "");
    codes.put(APP_NOT_EXIST.getCode(), "应用程序不存在或已被封禁");
    codes.put(SIGN_ERROR.getCode(), "API校验密匙错误");
    codes.put(NOT_LOGIN.getCode(), "未登录");
    codes.put(ACCOUNT_BANED.getCode(), "账号被封停");
    codes.put(TOKEN_ILLEGAL.getCode(), "csrf 校验失败");
    codes.put(SERVICE_UPDATE.getCode(), "系统升级中，敬请谅解");
    codes.put(NOT_MODIFIED.getCode(), "木有改动");
    codes.put(BAD_REQUEST.getCode(), "请求错误");
    codes.put(UNAUTHORIZED.getCode(), "未授权");
    codes.put(FORBIDDEN.getCode(), "访问权限不足");
    codes.put(NOT_FOUND.getCode(), "啥都木有");
    codes.put(METHOD_NOT_SUPPORT.getCode(), "不支持该方法");
    codes.put(CONFLICT.getCode(), "请求冲突");
    codes.put(TOO_MANY_REQUESTS.getCode(), "请求过快");
    codes.put(CLIENT_CLOSE_REQUEST.getCode(), "请求被关闭");
    codes.put(SERVER_ERROR.getCode(), "服务器错误");
    codes.put(NOT_IMPLEMENTED.getCode(), "服务未实现");
    codes.put(BAD_CONNECTION.getCode(), "连接错误");
    codes.put(SERVICE_UNAVAILABLE.getCode(), "服务暂不可用");
    codes.put(DEADLINE_EXCEEDED.getCode(), "处理超时");
    codes.put(LIMIT_EXCEEDED.getCode(), "请求过快");
    codes.put(FAIL_FILE_NOT_EXIST.getCode(), "上传文件不存在");
    codes.put(FAIL_FILE_TOO_LARGE.getCode(), "上传文件太大");
    return Collections.unmodifiableMap(codes);
  }

}
