// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.util.Color;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }
  public static class BlingConstants {
    public static final int kLedPort = 0; // Port that light strip is on
    public static final int kLedLength = 150; // Length of light strip
    public static final int kAlertLength = 60; // Pulse Length in Frames
    public static final int kPulseLength = 200; // Pulse Length in Frames
    public static final double kAroundSpeedMultiplier = .2; // Around speed multiplier
    public static final double kAroundStripLength = 15; // Around speed multiplier
    public static final double kBrightness = 1; // Percentage
    public static final BlingModes kDefaultBlingMode = BlingModes.AROUND_SECONDARY_BG;
    public static final Color kDefaultBlingColor = Color.kGold;
    public static final Color kDefaultBlingColorSecondary = Color.kRoyalBlue;
  }
  public enum BlingModes {
    OFF,
    SOLID,
    SOLID_SECONDARY,
    GRADIENT,
    GRADIENT_REVERSED,
    PULSE,
    PULSE_SWITCH,
    AROUND,
    AROUND_SECONDARY_BG,
    WARNING,
    ERROR
  }
}
