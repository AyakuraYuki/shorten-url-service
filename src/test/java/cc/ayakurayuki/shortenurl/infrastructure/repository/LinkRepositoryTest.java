package cc.ayakurayuki.shortenurl.infrastructure.repository;

import cc.ayakurayuki.shortenurl.infrastructure.persistence.po.LinkPo;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Ayakura Yuki
 * @date 2024/01/10-14:38
 */
@SpringBootTest
public class LinkRepositoryTest {

  @Resource
  private LinkRepository linkRepository;

  @Test
  public void testLoad() {
    LinkPo linkPo = linkRepository.load("non:exist:key");
    Assertions.assertNull(linkPo);
  }

}
