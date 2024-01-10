package cc.ayakurayuki.extension.pool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

/**
 * @author Ayakura Yuki
 * @date 2024/01/10-10:38
 */
public class ThreadPoolsFactory {

  /**
   * 默认阻塞系数
   */
  private static final double DEFAULT_BLOCKING_COEFFICIENT = 0.9;

  /**
   * 默认线程池并发度 核数 / (1 - 阻塞系数) * 2
   */
  private static final int DEFAULT_PARALLELISM = (int) (Runtime.getRuntime().availableProcessors() / (1 - DEFAULT_BLOCKING_COEFFICIENT)) * 2;

  public static final ForkJoinPool common;

  static {
    common = newForkJoinPool("fork-join-common-pool", DEFAULT_PARALLELISM);
  }

  // ---------------------------------------------------------------------------------------------------- //

  public static ScheduledThreadPoolExecutor newScheduledThreadPool(String name, int corePoolSize) {
    return newScheduledThreadPool(name, corePoolSize, true);
  }

  public static ScheduledThreadPoolExecutor newScheduledThreadPool(String name, int corePoolSize, boolean daemon) {
    return new ScheduledThreadPoolExecutor(corePoolSize, getFactory(name, daemon));
  }

  public static ScheduledThreadPoolExecutor newScheduledThreadPool(String name, int corePoolSize, RejectedExecutionHandler handler) {
    return newScheduledThreadPool(name, corePoolSize, handler, true);
  }

  public static ScheduledThreadPoolExecutor newScheduledThreadPool(String name, int corePoolSize, RejectedExecutionHandler handler, boolean daemon) {
    return new ScheduledThreadPoolExecutor(corePoolSize, getFactory(name, daemon), handler);
  }

  // ---------------------------------------------------------------------------------------------------- //

  public static ForkJoinPool newForkJoinPool(String name, int parallelism) {
    return new ForkJoinPool(parallelism, getForkJoinFactory(name), null, true);
  }

  public static ForkJoinPool newForkJoinPool(String name, int parallelism, UncaughtExceptionHandler handler, boolean asyncMode) {
    return new ForkJoinPool(parallelism, getForkJoinFactory(name), handler, asyncMode);
  }

  // ---------------------------------------------------------------------------------------------------- //

  private static ForkJoinThreadFactory getForkJoinFactory(String name) {
    return new ForkJoinThreadFactory(name);
  }

  private static ThreadFactory getFactory(String name, boolean daemon) {
    return new ThreadFactoryBuilder()
        .setNameFormat(name + "-%d")
        .setDaemon(daemon)
        .build();
  }

}
