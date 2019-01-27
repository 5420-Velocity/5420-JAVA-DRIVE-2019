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
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.can.*;

import frc.robot.helpers.*;
import frc.robot.helpers.controllers.*;
import frc.robot.commands.*;
import frc.robot.user.commands.*;

/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 */
public class Robot extends TimedRobot {
  
  private static int logInterval = 0;
  
  /**
   * All Input and Output Devices below should be in the
   *  Excel Sheet in the link below.
   * 
   * @link https://docs.google.com/spreadsheets/d/1qpUWBg1E4hRL2MkAI9xdoiQkqGg9Q8PkZpfA0f9DALU/edit
   */

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
  public static DigitalInput ballLoaded; // This switch is for when the balll is loaded.
  public static DoubleSolenoid hatchSol; // Put Solenoid to the Open State
  
  public static VictorSP motorLift, motorTilt, climbMotor, walkingMotor, ballIntake;

  public static DoubleSolenoid winchBreak;

  public static Compressor compressor;
  public static CommandGroup autoCommand;

  public static char[] gameData;

  // Used to Store the Table Entries for the Network Table.
  //  Stored as an Associative Array to the NetowrkTable Entry
  // Map<String, NetworkTableEntry> tableIndex = new HashMap<String, NetworkTableEntry>();

  public static NetworkTableEntryStore tableIndex;

  public static int DRIVER = 1;
  public static int OPERATOR = 2;
  public static int CTRL_LOG_INTERVAL = 60;

  @Override
  public void robotInit() {
    Save.getInstance().writeComment("Robot Log Started.")
                      .write("Started Program");

    Robot.tableInstance = NetworkTableInstance.getDefault(); // Get the Driver Station Network Table Instance.
    Robot.table = tableInstance.getTable("SensorData"); // Add the a table just for Sensor Data.

    Robot.driver = new Joystick(Robot.DRIVER);
    //Robot.driver = new XboxController(Robot.DRIVER);
    Robot.inputGrabberToggle = new JoystickButton(Robot.driver, LogitechMap_X.BUTTON_A);

    Robot.driver.setRumble(RumbleType.kLeftRumble, 0);

    Robot.operator = new Joystick(Robot.OPERATOR);

    Robot.left1 = new WPI_TalonSRX(52);
    Robot.left2 = new WPI_TalonSRX(54);
    Robot.left3 = new WPI_TalonSRX(55);

    Robot.right1 = new WPI_TalonSRX(56);
    Robot.right2 = new WPI_TalonSRX(57);
    Robot.right3 = new WPI_TalonSRX(58);

    Robot.left = new SpeedControllerGroup(left1, left2, left3);
    Robot.left.setInverted(true);
    Robot.right = new SpeedControllerGroup(right1, right2, right3);
    Robot.m_drive = new DifferentialDrive(Robot.left, Robot.right);

    Robot.test = new WPI_TalonSRX(57);

    //m_myRobot = new DifferentialDrive(new PWMVictorSPX(0), new PWMVictorSPX(1));
    //m_leftStick = new Joystick(0);
    //m_rightStick = new Joystick(1);
    
    leftSide = new Ultrasonic(0, 1);
    rightSide = new Ultrasonic(2, 3);
    frontSide = new Ultrasonic(4, 5);

    hatchSol = new DoubleSolenoid(0, 1);
    winchBreak = new DoubleSolenoid(2, 3);
    transSol = new DoubleSolenoid(4, 5);

    hatchSwitchAutoClose = new DigitalInput(6);

    ballLoaded = new DigitalInput(7);

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

    if(logInterval % CTRL_LOG_INTERVAL == 0){
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

    // Robot Auto Command Drive Controls
    Robot.autoCommand.addSequential(new AutoDrive(m_drive, 0.5, 10));

    Robot.autoCommand.start();
  }

  @Override
  public void autonomousPeriodic() {
    Robot.gameData = window.getData();
    if(hatchSwitchAutoClose.get() == true){
      new ToggleHatchGrabState(DoubleSolenoid.Value.kForward).start();
    }

    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    // Stop Auto Commands
    if(Robot.autoCommand != null){
      Robot.autoCommand.cancel();
    }

    inputGrabberToggle.whenPressed(new ToggleHatchGrabState());

  }

  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();

    // Ball Intake Control using Buttons
    if(driver.getRawButton(LogitechMap_X.BUTTON_X)){
      // Ball In
      ballIntake.set(0.5);
    }
    else if(driver.getRawButton(LogitechMap_X.BUTTON_Y)){
      // Ball Out
      ballIntake.set(-0.5);
    }
    else {
      // Ball oFF
      ballIntake.set(0);
    }
    
    Robot.m_drive.arcadeDrive( (driver.getRawAxis(LogitechMap_X.AXIS_RIGHT_X)*0.8), (-driver.getRawAxis(LogitechMap_X.AXIS_LEFT_Y)*0.8) );

  }

}
