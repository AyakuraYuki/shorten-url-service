package cc.ayakurayuki.shortenurl.application.validator;

import cc.ayakurayuki.shortenurl.infrastructure.errors.ServerCode;
import cc.ayakurayuki.shortenurl.infrastructure.errors.ServerError;
import cc.ayakurayuki.shortenurl.interfaces.req.SaveUrlReq;

/**
 * @author Ayakura Yuki
 * @date 2024/01/10-16:25
 */
public abstract class RequestValidator {

  public static void validateSaveUrlReq(SaveUrlReq req) {
    if (req == null) {
      throw ServerError.code(ServerCode.BAD_REQUEST);
    }
    if (req.url() == null || req.url().isBlank()) {
      throw ServerError.code(ServerCode.BAD_REQUEST);
    }
  }

}
