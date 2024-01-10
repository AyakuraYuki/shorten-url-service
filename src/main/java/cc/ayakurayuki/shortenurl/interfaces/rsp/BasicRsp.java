package cc.ayakurayuki.shortenurl.interfaces.rsp;

import cc.ayakurayuki.shortenurl.infrastructure.errors.ServerCode;

/**
 * @author Ayakura Yuki
 * @date 2024/01/10-16:51
 */
public class BasicRsp {

  private Integer code;
  private String  message;

  public BasicRsp(Integer code, String message) {
    this.code = code;
    this.message = message;
  }

  public BasicRsp(ServerCode serverCode) {
    this.code = serverCode.getCode();
    this.message = serverCode.getMessage();
  }

}
