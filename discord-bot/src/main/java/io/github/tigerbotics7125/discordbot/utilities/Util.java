package io.github.tigerbotics7125.discordbot.utilities;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Objects;

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
   * @return the desired file, or null if file does not exist, or URISyntaxException is thrown.
   */
  public static File getResource(Resource resource) {
    try {
      return new File(
          Objects.requireNonNull(Util.class.getClassLoader().getResource(resource.filePath))
              .toURI());
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    return null;
  }
}
