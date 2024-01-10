package cc.ayakurayuki.shortenurl.infrastructure.config;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import org.redisson.config.Config;
import org.redisson.spring.starter.RedissonAutoConfigurationCustomizer;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ayakura Yuki
 * @date 2024/01/10-14:18
 */
@Configuration
public class RedissonConfig implements RedissonAutoConfigurationCustomizer {

  @Override
  public void customize(Config configuration) {
    // 利用协程
    ThreadFactory factory = Thread.ofVirtual()
        .name("redisson-thread")
        .factory();
    configuration.setExecutor(Executors.newThreadPerTaskExecutor(factory));
  }

}
