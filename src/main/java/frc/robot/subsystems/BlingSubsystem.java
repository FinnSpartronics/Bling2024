// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.BlingConstants.*;

/**
* Subsystem for Bling
 */
public class BlingSubsystem extends SubsystemBase {
  /**
   * Bling Modes
   */
  public enum BlingModes {
    OFF,
    WARNING,
    ERROR,

    ALLIANCE,
    ALLIANCE_PULSE,

    RED,
    ORANGE,
    YELLOW,
    LIME,
    GREEN,
    CYAN,
    BLUE,
    BLUE_OTHER,
    SKY,
    PURPLE,
    PINK,
    PINK_HOT,
    WHITE,
    BROWN,
    GRAY,

    // GRADIENT,
    // PULSE,
    // SNAKE,
  }

  private final AddressableLED led;
  private final AddressableLEDBuffer ledBuffer;

  private BlingModes mMode;
  private int mFrame;

  /**
   * @return the current bling mode.
   */
  public BlingModes getMode() {
    return mMode;
  }

  public void shuffleboardFunc(){
    var tab = Shuffleboard.getTab("blonk");
    tab.add("off", this.setModeCommand(BlingModes.OFF));
    tab.add("err pulse", this.setModeCommand(BlingModes.ERROR));
    tab.add("PURPLE", this.setModeCommand(BlingModes.PURPLE));

  }

  /**
   * Sets the bling mode
   * @param newMode The new Bling Mode for the lighting
   */
  public void setMode(BlingModes newMode) {
    this.mMode = newMode;
  }

  public Command setModeCommand(BlingModes newMode) {
    return runOnce(() -> {
      setMode(newMode);
    });
  }

  /**
   * Constructor for BlingSubsystem with default settings as set in Constants
   */
  public BlingSubsystem() {
    mMode = kDefaultBlingMode;
    mFrame = 0;
    led = new AddressableLED(kLedPort);

    ledBuffer = new AddressableLEDBuffer(kLedLength);
    for (var i = 0; i < kLedLength; i++) {
      int r = (int) (255f-(255f/kLedLength)*i);
      int g = (int) (255f/kLedLength)*i;
      ledBuffer.setRGB(i,  r,  g, 50);
    }
    led.setLength(kLedLength);

    led.start();
    shuffleboardFunc();
  }

  /**
   * Constructor for BlingSubsystem with default port and non-default starting mode
   * @param startMode Starting mode for the bling
   */
  public BlingSubsystem(BlingModes startMode) {
    mMode = startMode;
    mFrame = 0;
    led = new AddressableLED(kLedPort);

    ledBuffer = new AddressableLEDBuffer(kLedLength);
    for (var i = 0; i < kLedLength; i++) {
      int r = (int) (255f-(255f/kLedLength)*i);
      int g = (int) (255f/kLedLength)*i;
      ledBuffer.setRGB(i,  r,  g, 50);
    }
    led.setLength(kLedLength);

    led.start();
  }

  /**
   * Constructor for BlingSubsystem with default port and non-default starting mode
   * @param startMode Starting mode for the bling
   * @param port Port for bling light strip
   */
  public BlingSubsystem(BlingModes startMode, int port) {
    mMode = startMode;
    mFrame = 0;
    led = new AddressableLED(port);

    ledBuffer = new AddressableLEDBuffer(kLedLength);
    for (var i = 0; i < kLedLength; i++) {
      int r = (int) (255f-(255f/kLedLength)*i);
      int g = (int) (255f/kLedLength)*i;
      ledBuffer.setRGB(i,  r,  g, 50);
    }
    led.setLength(kLedLength);

    led.start();
  }

  /**
   * Constructor for BlingSubsystem with default port and non-default starting mode
   * @param port Port for bling light strip
   */
  public BlingSubsystem(int port) {
    mMode = kDefaultBlingMode;
    mFrame = 0;
    led = new AddressableLED(port);

    ledBuffer = new AddressableLEDBuffer(kLedLength);
    for (var i = 0; i < kLedLength; i++) {
      int r = (int) (255f-(255f/kLedLength)*i);
      int g = (int) (255f/kLedLength)*i;
      ledBuffer.setRGB(i,  r,  g, 50);
    }
    led.setLength(kLedLength);

    led.start();
  }

  @Override
  public void periodic() {
    mFrame++;
    update();
  }

  /**
   * Updates the LEDS.
   */
  public void update() {
    System.out.println("curr mode" + mMode.name());
    switch (mMode) {
      case OFF: setAllLeds(0,0,0); break;
      case WARNING: alert(255,0,0); break;
      case ERROR: alert(255,225,0); break;

      case ALLIANCE: {
        if (DriverStation.getAlliance().get() == Alliance.Red) setAllLeds(232, 72, 72);
        else if (DriverStation.getAlliance().get() == Alliance.Blue) setAllLeds(46, 102, 232);
        break;
      }
      case ALLIANCE_PULSE: {
        if (DriverStation.getAlliance().get() == Alliance.Red) pulse(232, 72, 72);
        else if (DriverStation.getAlliance().get() == Alliance.Blue) pulse(46, 102, 232);
        break;
      }

      case RED: setAllLeds(255,255,0); break;
      case ORANGE: setAllLeds(255,180,0); break;
      case YELLOW: setAllLeds(255,225,0); break;
      case LIME: setAllLeds(0,255,0); break;
      case GREEN: setAllLeds(82, 199, 107); break;
      case CYAN: setAllLeds(24, 175, 217); break;
      case BLUE: setAllLeds(0, 0, 255); break;
      case BLUE_OTHER: setAllLeds(46, 102, 232); break;
      case SKY: pulse(66, 192, 255); break;
      case PURPLE: setAllLeds(195, 0, 255); break;
      case PINK: setAllLeds(255, 110, 231); break;
      case PINK_HOT: setAllLeds(255, 0, 213); break;
      case WHITE: setAllLeds(255,255,255); break;
      case BROWN: setAllLeds(181, 140, 69); break;
      case GRAY: setAllLeds(125,125,125); break;
    }
  }

  /**
   * Fades in, flashes out.
   * @param r The red value of the color
   * @param g The green value of the color
   * @param b The blue value of the color
   */
  private void alert(int r, int g, int b) {
    float percentage = (mFrame % (float) (kAlertLength)) / (float) kAlertLength;
    setAllLeds((int) (r*percentage*kBrightness), (int) (g*percentage*kBrightness), (int) (b*percentage*kBrightness));
  }

  /**
   * Fades in, flashes out.
   * @param rgb An array of three integers for Red, Green, Blue
   */
  private void alert(int[] rgb) {
    alert(rgb[0],rgb[1],rgb[2]);
  }

  /**
   * Pulses the LEDS on a certain color.
   * @param r The red value of the color
   * @param g The green value of the color
   * @param b The blue value of the color
   */
  private void pulse(int r, int g, int b) {
    float percentage = (float) (Math.sin(mFrame / (float) kPulseLength)+1)/2;
    setAllLeds((int) (r*percentage*kBrightness), (int) (g*percentage*kBrightness), (int) (b*percentage*kBrightness));
  }

  /**
   * Pulses the LEDS on a certain color.
   * @param rgb An array of three integers for Red, Green, Blue
   */
  private void pulse(int[] rgb) {
    pulse(rgb[0],rgb[1],rgb[2]);
  }

  /**
   * Sets the color of all the LEDS in the String.
   * @param r The red value of the color
   * @param g The green value of the color
   * @param b The blue value of the color
   */
  private void setAllLeds(int r, int g, int b) {
    for (int i = 0; i < kLedLength; i++) {
      ledBuffer.setRGB(i, (int) (r*kBrightness), (int) (g*kBrightness), (int) (b*kBrightness));
    }
    led.setData(ledBuffer);
  }

  @Override
  public void simulationPeriodic() {
    mFrame++;
    update();
  }
}
