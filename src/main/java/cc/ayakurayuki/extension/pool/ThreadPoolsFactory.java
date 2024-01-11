package cc.ayakurayuki.extension.pool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ayakura Yuki
 * @date 2024/01/10-10:38
 */
public class ThreadPoolsFactory {

  private static final Logger log = LoggerFactory.getLogger(ThreadPoolsFactory.class);

  /**
   * 默认阻塞系数
   */
  private static final double DEFAULT_BLOCKING_COEFFICIENT = 0.9;

  /**
   * 默认线程池并发度 核数 / (1 - 阻塞系数) * 2
   */
  private static final int DEFAULT_PARALLELISM = (int) (Runtime.getRuntime().availableProcessors() / (1 - DEFAULT_BLOCKING_COEFFICIENT)) * 2;

  public static final ForkJoinPool common;
  public static final Executor     commonCompletableFutureAsyncPool;

  static {
    common = newForkJoinPool("fork-join-common-pool", DEFAULT_PARALLELISM);
    commonCompletableFutureAsyncPool = newVirtualThreadPerTaskExecutor("completable-future-common-worker");

    System.setProperty("jdk.reflect.useDirectMethodHandle", "false");
    try {
      var forkJoinPoolCommon = ForkJoinPool.class.getDeclaredField("common");
      reflectionSet(forkJoinPoolCommon, common);
      var completableFutureAsyncPool = CompletableFuture.class.getDeclaredField("ASYNC_POOL");
      reflectionSet(completableFutureAsyncPool, commonCompletableFutureAsyncPool);
      log.info("modify fork-join-common-pool parallelism: {} success!", DEFAULT_PARALLELISM);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      log.warn("Can not find declared field: common", e);
    }
  }

  private static <T> void reflectionSet(Field field, T t) throws NoSuchFieldException, IllegalAccessException {
    field.setAccessible(true);
    boolean isFinal = Modifier.isFinal(field.getModifiers());
    Field modifiers = getModifiersField();
    if (isFinal) {
      modifiers.setAccessible(true);
      modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
    }
    field.set(null, t);
    if (isFinal) {
      modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
    }
  }

  private static Field getModifiersField() throws NoSuchFieldException {
    try {
      return Field.class.getDeclaredField("modifiers");
    } catch (NoSuchFieldException e) {
      try {
        Method getDeclaredFields0 = Class.class.getDeclaredMethod("getDeclaredFields0", boolean.class);
        getDeclaredFields0.setAccessible(true);
        Field[] fields = (Field[]) getDeclaredFields0.invoke(Field.class, false);
        for (Field field : fields) {
          if ("modifiers".equals(field.getName())) {
            return field;
          }
        }
      } catch (ReflectiveOperationException ex) {
        e.addSuppressed(ex);
      }
      throw e;
    }
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

  public static ExecutorService newVirtualThreadPerTaskExecutor(String name) {
    if (name == null || name.isBlank()) {
      name = String.format("virtual-thread-worker-%d-", new Random().nextInt(Integer.MAX_VALUE));
    }
    if (!name.endsWith("-")) {
      name = name + "-";
    }
    ThreadFactory factory = Thread.ofVirtual().name(name, 0).factory();
    return Executors.newThreadPerTaskExecutor(factory);
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
