package io.github.tigerbotics7125.tbaapi;

import com.google.gson.reflect.TypeToken;
import io.github.tigerbotics7125.tbaapi.schema.api.APIStatus;

public enum TBACalls {
  Status("status", TypeToken.get(APIStatus.class));

  public final ApiCall<?> apiCall;

  TBACalls(String endpoint, TypeToken<?> typeToken) {
    apiCall = new ApiCall<>(endpoint, typeToken);
  }
}
