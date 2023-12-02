package de.sql.chat.util;

import static java.util.concurrent.CompletableFuture.delayedExecutor;
import static java.util.concurrent.CompletableFuture.runAsync;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class TestUtils {

  /**
   * Sleep for a short duration.
   *
   * @param millis The duration to sleep in milliseconds.
   */
  public static void sleepForShortDuration(long millis) {
    runAsync(() -> {}, delayedExecutor(millis, MILLISECONDS)).join();
  }
}