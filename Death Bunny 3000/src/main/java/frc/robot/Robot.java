/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Joystick.ButtonType;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.can.*;

import java.util.Map;
import java.util.HashMap;
import frc.robot.helpers.*;
import frc.robot.commands.*;

/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 */
public class Robot extends TimedRobot {
  
  private static int logInterval = 0;
  
  public static Joystick driver;
  //private XboxController driver;
  public static JoystickButton inputGrabberToggle;
  public static Joystick operator;

  public static DifferentialDrive m_drive;
  public static WPI_TalonSRX left1, left2, left3; // Left Side Motors
  public static WPI_TalonSRX right1, right2, right3; // Right Side Motors
  public static WPI_TalonSRX test;
  public static SpeedControllerGroup left;
  public static SpeedControllerGroup right;
  public static DoubleSolenoid transSol; // Put Solenoid to the Close State

  public static NetworkTableInstance tableInstance;
  public static NetworkTable table;
  public static NetworkTableEntry autoSelect;
  public static Ultrasonic leftSide; // Left Side Sonar Sensor
  public static Ultrasonic rightSide; // Right Side Sonar Sensor
  public static Ultrasonic frontSide; // Front Direction Sensor
  public static DigitalInput hatchSwitchAutoClose; // This switch is to auto close the
  public static DoubleSolenoid hatchSol; // Put Solenoid to the Open State
  
  public static VictorSP motorLift, motorTilt, climbMotor, walkingMotor, ballIntake;

  public static Compressor compressor;
  public static CommandGroup autoCommand;

  public static char[] gameData;

  // Used to Store the Table Entries for the Network Table.
  //  Stored as an Associative Array to the NetowrkTable Entry
  // Map<String, NetworkTableEntry> tableIndex = new HashMap<String, NetworkTableEntry>();

  public static NetworkTableEntryStore tableIndex;

  public static int DRIVER = 1;
  public static int DRIVER_TRIGGER = 1; // Button Value, A Button
  public static int OPERATOR = 2;
  public static int INTERVAL_LOG = 30;

  @Override
  public void robotInit() {
    Save.getInstance().writeComment("Robot Log Started.")
                      .write("Started Program");

    Robot.tableInstance = NetworkTableInstance.getDefault(); // Get the Driver Station Network Table Instance.
    Robot.table = tableInstance.getTable("SensorData"); // Add the a table just for Sensor Data.

    Robot.driver = new Joystick(Robot.DRIVER);
    //Robot.driver = new XboxController(Robot.DRIVER);
    Robot.inputGrabberToggle = new JoystickButton(Robot.driver, DRIVER_TRIGGER);

    Robot.driver.setRumble(RumbleType.kLeftRumble, 0);

    Robot.operator = new Joystick(Robot.OPERATOR);

    Robot.left1 = new WPI_TalonSRX(50);
    Robot.left2 = new WPI_TalonSRX(51);
    Robot.left3 = new WPI_TalonSRX(52);

    Robot.right1 = new WPI_TalonSRX(53);
    Robot.right2 = new WPI_TalonSRX(54);
    Robot.right3 = new WPI_TalonSRX(55);

    Robot.left = new SpeedControllerGroup(left1, left2, left3);
    Robot.right = new SpeedControllerGroup(right1, right2, right3);
    Robot.m_drive = new DifferentialDrive(Robot.left, Robot.right);

    Robot.test = new WPI_TalonSRX(57);

    //m_myRobot = new DifferentialDrive(new PWMVictorSPX(0), new PWMVictorSPX(1));
    //m_leftStick = new Joystick(0);
    //m_rightStick = new Joystick(1);
    
    leftSide = new Ultrasonic(1, 2);
    rightSide = new Ultrasonic(3, 4);
    frontSide = new Ultrasonic(5, 6);

    hatchSol = new DoubleSolenoid(0, 1);
    transSol = new DoubleSolenoid(7, 8);

    motorLift = new VictorSP(1);
    motorTilt = new VictorSP(2);
    climbMotor = new VictorSP(3);
    walkingMotor = new VictorSP(4);
    ballIntake = new VictorSP(5);

    compressor = new Compressor(0);

    SmartDashboard.setDefaultNumber("Test", 0);
    SmartDashboard.setDefaultNumber("AutoDelay", 0);
    SmartDashboard.setDefaultBoolean("Sol", false);
  }

  @Override
  public void testInit() {
    // Enable Compressor Control
    compressor.setClosedLoopControl(true);
  }

  @Override
  public void testPeriodic() {

    Robot.test.set( SmartDashboard.getNumber("Test", 0) );

    if(SmartDashboard.getBoolean("Sol", false) == false){
      hatchSol.set(DoubleSolenoid.Value.kForward);
    }
    else {
      hatchSol.set(DoubleSolenoid.Value.kReverse);
    }

  }  

  @Override
  public void robotPeriodic() {
    // Reset the Rumble to 0, No Rumble
    driver.setRumble(RumbleType.kLeftRumble, 0);
    driver.setRumble(RumbleType.kRightRumble, 0);
    operator.setRumble(RumbleType.kLeftRumble, 0);
    operator.setRumble(RumbleType.kRightRumble, 0);

    if(logInterval % INTERVAL_LOG == 0){
      Logger.pushCtrlValues("Driver", Robot.driver);
      Logger.pushCtrlValues("Operator", Robot.operator);

      Robot.logInterval = 0;
    }

    Robot.logInterval++;
  }

  @Override
  public void autonomousInit() {

    // Auto Timer Delay
		if( SmartDashboard.getNumber("AutoDelay", 0) != 0 ){
      // Get the Timer Delay from the Dashboard and Delay the Auto.
			Timer.delay( SmartDashboard.getNumber("AutoDelay", 0) );
		}
    Robot.autoCommand = new CommandGroup();

    Robot.autoCommand.addSequential(new AutoDrive(m_drive, 0.5, 10));

    Robot.autoCommand.start();
  }

  

  @Override
  public void autonomousPeriodic() {
    Robot.gameData = window.getData();
    
    if(hatchSwitchAutoClose.get() == true){
      new ToggleHatchGrabState().start();
    }

    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    inputGrabberToggle.whenPressed(new ToggleHatchGrabState());

  }

  @Override
  public void teleopPeriodic() {
    //m_myRobot.tankDrive(m_leftStick.getY(), m_rightStick.getY());
    
    Robot.m_drive.arcadeDrive(driver.getY(), driver.getX());

  }

}
