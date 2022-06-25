package io.github.tigerbotics7125.tbaapi;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

  /**
   * @param url The url, escaping params as such
   *     <p><code>"www.website.com/api/{param1}/{param2}"</code>
   *     <p>
   * @param params The params to fill.
   * @return the url filled with params.
   */
  public static String fillParams(String url, String... params) {
    Pattern pattern = Pattern.compile("\\{.*?\\}");
    Matcher matcher = pattern.matcher(url);

    if (matcher.results().count() != params.length) {
      throw new IllegalArgumentException(
          "The number of params does not match the number of expected params in the url.");
    }

    // This is awful so here is a break down.
    /*
     * 1. We make a static array to contain the incrementor as is effectively static, required by the lambda.
     * 2. matcher.replaceAll(Function) will call the function over every match, so we can simply call .group() to get the match.
     * 3. we take the match, and replace itself with the desired param.
     */

    int[] i = {0};
    url =
        matcher.replaceAll(
            x -> {
              return x.group().replace(x.group(), params[i[0]++]);
            });

    return url;
  }
}
