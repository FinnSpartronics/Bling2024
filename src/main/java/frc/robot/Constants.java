// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.subsystems.BlingSubsystem.BlingModes;

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
    public static final int kAlertLength = 20; // Pulse Length in Frames
    public static final int kPulseLength = 40; // Pulse Length in Frames
    public static final float kBrightness = 0.6f; // Percentage
    public static final BlingModes kDefaultBlingMode = BlingModes.GREEN;
  }

}
