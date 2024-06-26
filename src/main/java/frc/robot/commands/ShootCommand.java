// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LauncherSubsystem;
import frc.robot.subsystems.LightsSubsystem;

public class ShootCommand extends Command {
  private LauncherSubsystem launcherSubsystem;
  private LightsSubsystem lightsSubsystem;

  int OnIndex;
  double maxSpeed = 1;
  double feedSpeed = 0.5;
  double waitTime = 0.5;
  double timeoutTime = 1;

  // Only need this if we have to use time stuff
  private Timer localTimer = new Timer();

  /** Creates a new ShootCommand. */
  public ShootCommand(LauncherSubsystem _launcherSubsystem, LightsSubsystem _lightsSubsystem) {
    launcherSubsystem = _launcherSubsystem;
    lightsSubsystem = _lightsSubsystem;
    addRequirements(_launcherSubsystem, _lightsSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // if (launcherSubsystem.getBottomSwitchValue()) {
      launcherSubsystem.setLaunchWheel(maxSpeed);
      localTimer.reset();
      localTimer.start();
      launcherSubsystem.enableLaunchMode();
      OnIndex = 0;
      lightsSubsystem.LightsOff();
    // } else {
    //   System.out.println("Missing Note");
    // }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if ( localTimer.get() >= waitTime) {  
      if (OnIndex < 19) {
        // Left side
        lightsSubsystem.m_ledBuffer.setLED(OnIndex, lightsSubsystem.offColor);
        lightsSubsystem.m_ledBuffer.setLED(OnIndex + 1, lightsSubsystem.greenColor);

        // // Right side
        lightsSubsystem.m_ledBuffer.setLED(lightsSubsystem.RightLights - OnIndex, lightsSubsystem.offColor);
        lightsSubsystem.m_ledBuffer.setLED(lightsSubsystem.RightLights - (OnIndex + 1), lightsSubsystem.greenColor);
        OnIndex++;
      } else {
        // Left side
        lightsSubsystem.m_ledBuffer.setLED(OnIndex, lightsSubsystem.offColor);
        lightsSubsystem.m_ledBuffer.setLED(0, lightsSubsystem.offColor);
        // Right side
        lightsSubsystem.m_ledBuffer.setLED(lightsSubsystem.RightLights - OnIndex, lightsSubsystem.offColor);
        lightsSubsystem.m_ledBuffer.setLED(lightsSubsystem.RightLights, lightsSubsystem.greenColor);

        OnIndex = 0;
      }
      launcherSubsystem.setFeedWheel(feedSpeed);
    } else {
      System.out.println(String.format("Waiting for speed! Time: %,.2f", localTimer.get()));
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    launcherSubsystem.StopMotors();
    launcherSubsystem.disableLaunchMode();
    for (int i = 0; i < 19; i++) {
      lightsSubsystem.m_ledBuffer.setLED(i, lightsSubsystem.redColor);
      lightsSubsystem.m_ledBuffer.setLED(lightsSubsystem.RightLights - i, lightsSubsystem.redColor);
    }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return  localTimer.get() > timeoutTime;
  }
}
