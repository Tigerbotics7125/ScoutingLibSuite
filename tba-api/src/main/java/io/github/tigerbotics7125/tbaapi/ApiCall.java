package io.github.tigerbotics7125.tbaapi;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Request;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class ApiCall<T> {
  private final String kEndpoint;
  private final TypeToken<?> kTypeToken;

  private T cache;
  private String eTag;

  public ApiCall(String endpoint, TypeToken<?> returnType) {
    kEndpoint = endpoint;
    kTypeToken = returnType;
  }

  public Optional<T> call(String... params) throws IOException {
    var reqBuilder =
        new Request.Builder()
            .url(Utility.fillParams(TBAReadApi3.kApiUrl + kEndpoint, params))
            .addHeader("X-TBA-Auth-Key", TBAReadApi3.authKey);
    if (eTag != null) {
      reqBuilder.addHeader("If-None-Match", eTag);
    }

    // get the final request
    Request req = reqBuilder.build();

    // execute the request and get its response
    var res = TBAReadApi3.kHttpClient.newCall(req).execute();

    if (res.code() == 404) {
      res.close();
      return Optional.empty();
    }

    // store the eTag, which is used to determine if the data has changed
    eTag = res.header("ETag");

    if (res.code() == 200) {
      // if the request was successful, parse the response and return it
      cache = new Gson().fromJson(Objects.requireNonNull(res.body()).string(), kTypeToken.getType());
    }

    res.close();
    return Optional.ofNullable(cache);
  }

  public TypeToken<?> getTypeToken() {
    return kTypeToken;
  }
}
