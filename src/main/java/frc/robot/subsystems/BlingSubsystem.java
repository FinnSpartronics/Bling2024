// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.BlingModes;

import static frc.robot.Constants.BlingConstants.*;

import java.awt.Color;

/**
* Subsystem for Bling
 */
public class BlingSubsystem extends SubsystemBase {
  private final AddressableLED led;
  private final AddressableLEDBuffer ledBuffer;

  private Constants.BlingModes mMode;
  private Color mPrimary;
  private Color mSecondary;
  private int mFrame;

  private boolean mSetSecondary = false;

  /**
   * Constructor for BlingSubsystem with default settings as set in Constants
   */
  public BlingSubsystem() {
    mMode = kDefaultBlingMode;
    mPrimary = kDefaultBlingColor;
    mSecondary = kDefaultBlingColorSecondary;

    mFrame = 0;

    led = new AddressableLED(kLedPort);
    led.setLength(kLedLength);

    ledBuffer = new AddressableLEDBuffer(kLedLength);

    led.start();
    shuffleboardFunc();
  }

  public void shuffleboardFunc() {
    var tab = Shuffleboard.getTab("bling");
    tab.add("off", this.setModeCommand(BlingModes.OFF));
    tab.add("solid", this.setModeCommand(BlingModes.SOLID));
    tab.add("solid #2", this.setModeCommand(BlingModes.SOLID_SECONDARY));
    tab.add("gradient", this.setModeCommand(BlingModes.GRADIENT));
    tab.add("gradient reversed", this.setModeCommand(BlingModes.GRADIENT_REVERSED));
    tab.add("pulse", this.setModeCommand(BlingModes.PULSE));
    tab.add("pulse switch", this.setModeCommand(BlingModes.PULSE_SWITCH));

    tab.add("warn", this.setModeCommand(BlingModes.WARNING));
    tab.add("ERROR", this.setModeCommand(BlingModes.ERROR));

    tab.add("set secondary", this.setSecondaryColorCommand());

    tab.add("red", this.setColorCommand(Color.red));
    tab.add("orange", this.setColorCommand(Color.orange));
    tab.add("yellow", this.setColorCommand(Color.yellow));
    tab.add("green", this.setColorCommand(Color.green));
    tab.add("blue", this.setColorCommand(Color.blue));
    tab.add("cyan", this.setColorCommand(Color.cyan));
    tab.add("magenta", this.setColorCommand(Color.magenta));
    tab.add("white", this.setColorCommand(Color.white));
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

  public void setColor(Color newColor) {
    if (mSetSecondary) this.mSecondary = newColor;
    else this.mPrimary = newColor;
    mSetSecondary = false;
  }
  public Command setColorCommand(Color newColor) {
    return runOnce(() -> {
      setColor(newColor);
    });
  }
  public Command setSecondaryColorCommand() {
    return runOnce(() -> {
      mSetSecondary = true;
    });
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
    System.out.println("Bling - " + mMode + ", " + mPrimary + ", " + mSecondary);
    switch (mMode) {
      case OFF -> setAllLeds(0, 0, 0);
      case SOLID -> setAllLeds(mPrimary.getRed(), mPrimary.getGreen(), mPrimary.getBlue());
      case SOLID_SECONDARY -> setAllLeds(mSecondary.getRed(), mSecondary.getGreen(), mSecondary.getBlue());
      case GRADIENT -> {
        for (int i = 0; i < kLedLength; i++) {
          setPixel(i, mix(mPrimary, mSecondary, (float) (i) / kLedLength));
        }
        led.setData(ledBuffer);
      }
      case GRADIENT_REVERSED -> {
        for (int i = 0; i < kLedLength; i++) {
          setPixel(i, mix(mSecondary, mPrimary, (float) (i) / kLedLength));
        }
      }
      case PULSE ->
        setAllLeds(mix(mPrimary, Color.black, Math.abs((float) mFrame % kPulseLength / kPulseLength - .5f) * 2f));
      case PULSE_SWITCH ->
        setAllLeds(mix(mPrimary, mSecondary, Math.abs((float) mFrame % kPulseLength / kPulseLength - .5f) * 2f));
      case AROUND -> {
        for (int i = 0; i < kLedLength; i++) {
          if (3 >= Math.abs(i - (mFrame*kAroundSpeedMultiplier % kLedLength))) 
          setPixel(i, mix(mPrimary, Color.black, Math.abs(i - (mFrame*kAroundSpeedMultiplier % kLedLength))));
          else setPixel(i, Color.black);
        }
      }
      case ERROR ->
        setAllLeds(mix(Color.red, Color.pink, Math.abs((float) mFrame % kAlertLength / kAlertLength - .5f) * 2f));
      case WARNING ->
        setAllLeds(mix(Color.yellow, Color.orange, Math.abs((float) mFrame % kAlertLength / kAlertLength - .5f) * 2f));
    }

    led.setData(ledBuffer);
  }

  /**from wwwjava2s.com
  * Mixes two colours.
  *
  * @param a       Base colour.
  * @param b       Colour to mix in.
  * @param percent Percentage of the old colour to keep around.
  * @return the mixed Color
  */
   public static Color mix(Color a, Color b, double percent) {
    return new Color((int) (a.getRed() * percent + b.getRed() * (1.0 - percent)),
      (int) (a.getGreen() * percent + b.getGreen() * (1.0 - percent)),
      (int) (a.getBlue() * percent + b.getBlue() * (1.0 - percent)));
  }

  /**
   * Sets the color of all the LEDS in the String.
   * @param r The red value of the color
   * @param g The green value of the color
   * @param b The blue value of the color
   */
  private void setAllLeds(int r, int g, int b) {
    for (int i = 0; i < kLedLength; i++) {
      setPixel(i, r, g, b);
    }
  }

  /**
   * Sets the color of all the LEDS in the String.
   * @param c Color to set
   */
  private void setAllLeds(Color c) {
    setAllLeds(c.getRed(), c.getGreen(), c.getBlue());
  }

  /**
   * Sets the color of a single pixel
   * @param index Index of the pixel
   * @param c Color to set
   */
  private void setPixel(int index, Color c) {
    ledBuffer.setRGB(index, (int) (c.getRed()*kBrightness), (int) (c.getGreen()*kBrightness), (int) (c.getBlue()*kBrightness));
  }

  /**
   * Sets the color of a single pixel
   * @param index Index of the pixel
   * @param r Red value of the pixel
   * @param g Green value of the pixel
   * @param b Blue value of the pixel
   */
  private void setPixel(int index, int r, int g, int b) {
    ledBuffer.setRGB(index, (int) (r*kBrightness), (int) (g*kBrightness), (int) (b*kBrightness));
  }

  @Override
  public void simulationPeriodic() {
    mFrame++;
    update();
  }
}
