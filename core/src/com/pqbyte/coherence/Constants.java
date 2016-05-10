package com.pqbyte.coherence;

public final class Constants {
  private static final boolean DEBUG = true;

  public static boolean isDebug() {
    return DEBUG;
  }

  public static final int WORLD_WIDTH = 160;
  public static final int WORLD_HEIGHT = 90;

  /* Mask flags for Box2D filters. */
  public static final short PHYSICS_ENTITY = 0x1;
  public static final short WORLD_ENTITY = 0x2;
}
