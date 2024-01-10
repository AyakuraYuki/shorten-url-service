package cc.ayakurayuki.shortenurl.application.service;

import cc.ayakurayuki.shortenurl.application.dto.SaveResultDto;
import cc.ayakurayuki.shortenurl.application.validator.UrlValidator;
import cc.ayakurayuki.shortenurl.infrastructure.constant.Consts;
import cc.ayakurayuki.shortenurl.infrastructure.factory.KeyFactory;
import cc.ayakurayuki.shortenurl.infrastructure.persistence.po.LinkPo;
import cc.ayakurayuki.shortenurl.infrastructure.repository.LinkRepository;
import cc.ayakurayuki.shortenurl.interfaces.req.SaveUrlReq;
import cc.ayakurayuki.shortenurl.interfaces.rsp.SaveUrlRsp;
import jakarta.annotation.Resource;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import org.springframework.stereotype.Service;

/**
 * @author Ayakura Yuki
 * @date 2024/01/10-16:15
 */
@Service
public class LinkAppService {

  @Resource
  private LinkRepository linkRepository;

  public SaveUrlRsp saveURL(SaveUrlReq req) throws MalformedURLException {
    boolean fromAdmin = UrlValidator.checkHash(req.url(), req.hash());

    URL url = URI.create(req.url()).toURL();
    String host = url.getHost();
    boolean isWhite = UrlValidator.checkWhiteList(host);

    String key = req.key();
    if (!fromAdmin || key == null || key.isBlank()) {
      key = KeyFactory.genKey(Consts.DEFAULT_KEY_LENGTH);
    }

    Duration ttl;
    if (req.ttlSecond() != null && req.ttlSecond() > 0) {
      ttl = Duration.ofSeconds(req.ttlSecond());
    } else {
      ttl = Duration.ofDays(30);
    }

    SaveResultDto dto = linkRepository.save(key, req.url(), ttl, fromAdmin, isWhite);
    return new SaveUrlRsp(dto.key(), dto.expireAt());
  }

  public LinkPo loadURL(String key) {
    return linkRepository.load(key);
  }

}
