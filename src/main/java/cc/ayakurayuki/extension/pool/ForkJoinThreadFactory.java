package cc.ayakurayuki.extension.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinPool.ForkJoinWorkerThreadFactory;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Ayakura Yuki
 * @date 2024/01/10-10:49
 */
public class ForkJoinThreadFactory implements ForkJoinWorkerThreadFactory {

  private final String     name;
  private final AtomicLong count = new AtomicLong();

  public ForkJoinThreadFactory(String name) {
    this.name = name;
  }

  @Override
  public ForkJoinWorkerThread newThread(ForkJoinPool pool) {
    return new NamedForkJoinWorkerThread(String.format("%s-%d", name, count.incrementAndGet()), pool);
  }

  public static class NamedForkJoinWorkerThread extends ForkJoinWorkerThread {

    protected NamedForkJoinWorkerThread(String name, ForkJoinPool pool) {
      super(pool);
      super.setName(name);
      super.setContextClassLoader(ClassLoader.getSystemClassLoader());
    }

  }

}
