package cc.ayakurayuki.shortenurl.infrastructure.config;

import cc.ayakurayuki.extension.pool.ThreadPoolsFactory;
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
    configuration.setExecutor(ThreadPoolsFactory.newVirtualThreadPerTaskExecutor("redisson-thread"));
  }

}
