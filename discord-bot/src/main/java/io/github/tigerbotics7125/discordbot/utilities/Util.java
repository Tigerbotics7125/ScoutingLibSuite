package io.github.tigerbotics7125.discordbot.utilities;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Collection;

public class Util {

  @SuppressWarnings("unused") // supress i being unused
  public static int getIterableSize(Iterable<?> data) {
    if (data instanceof Collection) {
      return ((Collection<?>) data).size();
    }
    int counter = 0;
    for (Object i : data) {
      counter++;
    }
    return counter;
  }

  /**
   * @param resourcePath
   * @return the desired file, or null if file does not exists, or URISytaxException is thrown.
   */
  public static File getResource(String resourcePath) {
    try {
      return new File(Util.class.getClassLoader().getResource(resourcePath).toURI());
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    return null;
  }
}
