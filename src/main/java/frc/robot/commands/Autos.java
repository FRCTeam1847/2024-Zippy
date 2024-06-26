// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.LauncherSubsystem;
import frc.robot.subsystems.LightsSubsystem;

public final class Autos {

  private static Command ShootSequence(LauncherSubsystem launcherSubsystem,
      LightsSubsystem lightsSubsystem) {
    return Commands.sequence(new IntakeCommand(launcherSubsystem, lightsSubsystem), new WaitCommand(1),
        new ShootCommand(launcherSubsystem, lightsSubsystem));
  }

  private static Command RotateSquence(DriveTrainSubsystem driveTrainSubsystem) {
    return Commands.sequence(new WaitCommand(0.25), new RotateCommand(driveTrainSubsystem, 140));
  }

  public static Command DriveBackwardsInches(DriveTrainSubsystem subsystem, ClimberSubsystem climberSubsystem,
      double inches) {
    return Commands.sequence(new DriveBackwardsDistance(subsystem, inches), new LiftClimberCommand(climberSubsystem));
  }

  public static Command DriveInchesRotate(DriveTrainSubsystem subsystem, ClimberSubsystem climberSubsystem,
      double inches, double degrees) {
    return Commands.sequence(new DriveBackwardsDistance(subsystem, inches), new RotateCommand(subsystem, degrees),
        new LiftClimberCommand(climberSubsystem));
  }

  public static Command Rotate(DriveTrainSubsystem subsystem, double degrees) {
    return Commands.sequence(new RotateCommand(subsystem, degrees));
  }

  public static Command CenterAuto(DriveTrainSubsystem subsystem, LauncherSubsystem launcherSubsystem,
      LightsSubsystem lightsSubsystem, ClimberSubsystem climberSubsystem) {
    return Commands.sequence(new DriveBackwardsDistance(subsystem, 6),
        ShootSequence(launcherSubsystem, lightsSubsystem),
        new DriveBackwardsDistance(subsystem, 45), RotateSquence(subsystem), new LiftClimberCommand(climberSubsystem));
  }

  public static Command LeftAuto(DriveTrainSubsystem subsystem, LauncherSubsystem launcherSubsystem,
      LightsSubsystem lightsSubsystem, ClimberSubsystem climberSubsystem) {
    return Commands.sequence(new DriveBackwardsDistance(subsystem, 5),ShootSequence(launcherSubsystem, lightsSubsystem), new WaitCommand(0.5),
        new DriveBackwardsDistance(subsystem, 12), new WaitCommand(0.5),
        new RotateCommand(subsystem, -48),
        new WaitCommand(0.5),
        new DriveBackwardsDistance(subsystem, 62), RotateSquence(subsystem), new LiftClimberCommand(climberSubsystem));
  }

  public static Command RightAuto(DriveTrainSubsystem subsystem, LauncherSubsystem launcherSubsystem,
      LightsSubsystem lightsSubsystem, ClimberSubsystem climberSubsystem) {
    return Commands.sequence(new DriveBackwardsDistance(subsystem, 1), new RotateCommand(subsystem, 5),ShootSequence(launcherSubsystem, lightsSubsystem), new WaitCommand(0.5),
        new DriveBackwardsDistance(subsystem, 12), new WaitCommand(0.5),
        new RotateCommand(subsystem, 25),
        new WaitCommand(0.5),
        new DriveBackwardsDistance(subsystem, 58), RotateSquence(subsystem), new LiftClimberCommand(climberSubsystem));
  }

  public static Command ShootAuto(LauncherSubsystem launcherSubsystem,
      LightsSubsystem lightsSubsystem, ClimberSubsystem climberSubsystem) {
    return Commands.sequence(ShootSequence(launcherSubsystem, lightsSubsystem),
        new LiftClimberCommand(climberSubsystem));
  }

  private Autos() {
    throw new UnsupportedOperationException("This is a utility class!");
  }
}
