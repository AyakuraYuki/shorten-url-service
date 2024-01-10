package cc.ayakurayuki.shortenurl.interfaces.rest;

import cc.ayakurayuki.shortenurl.application.service.LinkAppService;
import cc.ayakurayuki.shortenurl.application.validator.RequestValidator;
import cc.ayakurayuki.shortenurl.interfaces.req.SaveUrlReq;
import cc.ayakurayuki.shortenurl.interfaces.rsp.SaveUrlRsp;
import jakarta.annotation.Resource;
import java.net.MalformedURLException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ayakura Yuki
 * @date 2024/01/10-16:22
 */
@RestController
public class ShortenUrlApiController {

  @Resource
  private LinkAppService linkAppService;

  @PostMapping("/save-url")
  public SaveUrlRsp saveURL(@RequestBody SaveUrlReq req) throws MalformedURLException {
    RequestValidator.validateSaveUrlReq(req);
    return linkAppService.saveURL(req);
  }

}
