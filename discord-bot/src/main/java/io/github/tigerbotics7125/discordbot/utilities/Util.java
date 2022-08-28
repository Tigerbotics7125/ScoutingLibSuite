package io.github.tigerbotics7125.discordbot.utilities;

import java.io.InputStream;
import java.util.Collection;

public class Util {

  public enum Resource {
    TBALogoPng("TBALogo.png");

    private final String filePath;

    Resource(String filePath) {
      this.filePath = filePath;
    }
  }

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
   * @param resource the resource to retrieve
   * @return An {@link InputStream} to the resource.
   */
  public static InputStream getResource(Resource resource) {
    return Util.class.getClassLoader().getResourceAsStream(resource.filePath);
  }
}
