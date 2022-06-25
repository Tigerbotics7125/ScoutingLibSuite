package io.github.tigerbotics7125.databaselib;

public class DatabaseLib {

  public static String getBuildVersion() {
    return DatabaseLib.class.getPackage().getImplementationVersion();
  }
}
