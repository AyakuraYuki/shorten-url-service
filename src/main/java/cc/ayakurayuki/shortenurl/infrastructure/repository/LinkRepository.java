package cc.ayakurayuki.shortenurl.infrastructure.repository;

import cc.ayakurayuki.shortenurl.application.dto.SaveResultDto;
import cc.ayakurayuki.shortenurl.infrastructure.factory.KeyFactory;
import cc.ayakurayuki.shortenurl.infrastructure.persistence.po.LinkPo;
import jakarta.annotation.Resource;
import java.time.Duration;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Repository;

/**
 * @author Ayakura Yuki
 * @date 2024/01/10-14:35
 */
@Repository
public class LinkRepository {

  @Resource
  private RedissonClient redisson;

  public LinkPo load(String key) {
    RBucket<LinkPo> bucket = redisson.getBucket(key);
    if (!bucket.isExists()) {
      return null;
    }
    return bucket.get();
  }

  public SaveResultDto save(String key, String url, Duration ttl, boolean fromAdmin, boolean isWhite) {
    LinkPo existLinkPo = load(key);
    if (existLinkPo != null) {
      int keyLength = key.length();
      String newKey = KeyFactory.genKey(keyLength);
      return save(newKey, url, ttl, fromAdmin, isWhite);
    }

    long nowSeconds = System.currentTimeMillis() / 1000;
    RBucket<LinkPo> bucket = redisson.getBucket(key);
    LinkPo linkPo;
    long expireAt;

    if (!fromAdmin && !isWhite) {
      // 普通模式 并且 链接不在无过期白名单内的

      Duration realTTL = ttl != null ? ttl : Duration.ofDays(30);
      expireAt = nowSeconds + realTTL.toSeconds();
      linkPo = new LinkPo(url, nowSeconds, expireAt, false);
      bucket.set(linkPo, ttl);

    } else {
      // 管理模式 或者 链接在无过期白名单内的

      expireAt = -1L;
      linkPo = new LinkPo(url, nowSeconds, expireAt, fromAdmin);
      bucket.set(linkPo);

    }

    return new SaveResultDto(linkPo, key, expireAt);
  }

}
