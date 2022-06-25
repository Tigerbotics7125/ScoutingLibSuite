package io.github.tigerbotics7125.tbaapi;

import com.google.gson.reflect.TypeToken;
import io.github.tigerbotics7125.tbaapi.schema.api.APIStatus;

public enum TBACalls {
  Status("status", TypeToken.get(APIStatus.class));

  public final ApiCall<?> kCall;

  TBACalls(String endpoint, TypeToken<?> typeToken) {
    kCall = new ApiCall<>(endpoint, typeToken);
  }
}
