/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.can.*;

import frc.robot.helpers.*;
import frc.robot.helpers.RobotOrientation.Side;
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

  public static DifferentialDrive m_drive;
  public static WPI_TalonSRX left1, left2, left3; // Left Side Motors
  public static WPI_TalonSRX right1, right2, right3; // Right Side Motors
  public static WPI_TalonSRX test;
  public static DoubleSolenoid transSol; // Put Solenoid to the Close State
  public static PigeonGyro pigeon;

  public static Ultrasonic leftSide; // Left Side Sonar Sensor
  public static Ultrasonic rightSide; // Right Side Sonar Sensor
  public static DigitalInput hatchSwitchAutoClose; // This switch is to auto close the
  public static DigitalInput ballLoaded; // This switch is for when the balll is loaded.
  public static DoubleSolenoid hatchSol; // Put Solenoid to the Open State
  public static Encoder leftEncoder, rightEncoder, liftEncoder;
  
  public static VictorSP motorLift, motorTilt, climbMotor, walkingMotor, ballIntake;

  public static DoubleSolenoid winchBreak;

  public static Compressor compressor;
  public static CommandGroup autoCommand;

  public static char[] gameData;

  // Used to Store the Table Entries for the Network Table.
  //  Stored as an Associative Array to the NetowrkTable Entry
  // Map<String, NetworkTableEntry> tableIndex = new HashMap<String, NetworkTableEntry>();

  public static NetworkTableEntryStore tableIndex;

  public static int DRIVER = 0;
  public static int OPERATOR = 1;
  public static int CTRL_LOG_INTERVAL = 60;

  @Override
  public void robotInit() {
    // Init User Input Controls
    new OI();

    // Generate new Save with USB as Default, Save when Disabled
    // Save.defaultMode = // Save.Mode.kUSBFirst;
    // Save.getInstance().ignoreInDisabled = false; // Log Everything

    // Save.getInstance().writeComment("Robot Log Started.");

    // LEFT SIDE Control
    Robot.left1 = new WPI_TalonSRX(52);

    Robot.left2 = new WPI_TalonSRX(54);
    Robot.left2.follow(Robot.left1);

    Robot.left3 = new WPI_TalonSRX(55);
    Robot.left3.follow(Robot.left1);

    // Right SIDE Control
    Robot.right1 = new WPI_TalonSRX(56);

    Robot.right2 = new WPI_TalonSRX(57);
    Robot.right2.follow(Robot.right1);

    Robot.right3 = new WPI_TalonSRX(58);
    Robot.right3.follow(Robot.right1);

    // Build a full Differental Drive
    Robot.m_drive = new DifferentialDrive(Robot.left1, Robot.right1);

    Robot.test = new WPI_TalonSRX(57);

    Robot.pigeon = new PigeonGyro(left1);
    
    leftSide = new Ultrasonic(0, 1);
    rightSide = new Ultrasonic(2, 3);
    leftEncoder = new Encoder(4, 5);
    rightEncoder = new Encoder(6, 7);
    liftEncoder = new Encoder(10, 11);

    hatchSol = new DoubleSolenoid(0, 1);
    winchBreak = new DoubleSolenoid(2, 3);
    transSol = new DoubleSolenoid(4, 5);

    hatchSwitchAutoClose = new DigitalInput(8);
    ballLoaded = new DigitalInput(9);

    motorLift = new VictorSP(1);
    motorTilt = new VictorSP(2);
    climbMotor = new VictorSP(3);
    walkingMotor = new VictorSP(4);
    ballIntake = new VictorSP(5);

    compressor = new Compressor(0);

    SmartDashboard.setDefaultNumber("Test", 0);
    SmartDashboard.setDefaultNumber("AutoDelay", 0);
    SmartDashboard.setDefaultBoolean("Sol", false);
    
    Robot.logInterval++;

    // Save.getInstance().push("Test", false);

    Limelight.getInstance().setLed(Limelight.ledMode.kOff);

    // Setup Auto CTRL
    OI.Apply();
  }

  @Override
  public void robotPeriodic() {

    //// Save.getInstance().push("t", System.currentTimeMillis());
    // Save.getInstance().push("batt", RobotController.getBatteryVoltage());
    
    if(logInterval % CTRL_LOG_INTERVAL == 0){
      Logger.pushCtrlValues("Driver", OI.driver);
      Logger.pushCtrlValues("Operator", OI.operator);
      
      Robot.logInterval = 0;
    }

    OI.leftEncoder.setNumber(Robot.leftEncoder.get());
    OI.rightEncoder.setNumber(Robot.rightEncoder.get());
    OI.liftEncoder.setNumber(Robot.liftEncoder.get());

    // Save.getInstance().sync();
  }

  @Override
  public void testInit() {
    // Enable Compressor Control
    compressor.setClosedLoopControl(true);
  }

  @Override
  public void testPeriodic() {

    if(SmartDashboard.getBoolean("Sol", false) == false){
      hatchSol.set(DoubleSolenoid.Value.kForward);
    }
    else {
      hatchSol.set(DoubleSolenoid.Value.kReverse);
    }

  }  

  @Override
  public void autonomousInit() {
    Robot.gameData = window.getData();
    Robot.autoCommand = new CommandGroup();

    /////////////////////
    ////// STAGE 1 //////
    /////////////////////

    // Add Time Delay for a User Input
    Robot.autoCommand.addSequential( new Delay(OI.autoDelay.getNumber(0)) );

    //////////////////////////////////
    // If Position is Left / Right  //
    //////////////////////////////////
    if(OI.position.get() == 1 || OI.position.get() == 3){
      console.log("POS 1 || POS 3");


      // Level 1
      if(OI.level.get() == 1){
        console.log("LEVEL 1");

        // Drive 
        Robot.autoCommand.addSequential( new AutoDrive(Robot.m_drive, 0.5, 0, 2000) );

      }

      // Level 2
      else if(OI.level.get() == 2){
        console.log("LEVEL 2");

        //drive
        Robot.autoCommand.addSequential( new AutoDrive(Robot.m_drive, 0.5, 0, 4000));

      }

    }

    ///////////////////////////
    // If Position is Center //
    ///////////////////////////

    else if(OI.position.get() == 2){

      console.log("POS 2");

      // Level 1
      if(OI.level.get() == 1){  
        console.log("LEVEL 1");

        // Drive 
        Robot.autoCommand.addSequential( new AutoDrive(Robot.m_drive, 0.5, 0, 2000) );

      }

    }

    /////////////////////
    ////// STAGE 2 //////
    /////////////////////

    if(OI.target.get() == OI.Target.None){

    }
    else if(OI.target.get() == OI.Target.Face){
      console.log("TARGET: FACE");

      Robot.autoCommand.addSequential(new AutoDrive(Robot.m_drive, 0.5, 0, 4000));
      Robot.autoCommand.addSequential(new AutoTurn(Robot.m_drive, Robot.pigeon, 0.5, 90));
      Robot.autoCommand.addSequential(new AutoDrive(Robot.m_drive, 0.5, 0, 2500));
      Robot.autoCommand.addSequential(new AutoTurn(Robot.m_drive, Robot.pigeon, 0.5, -90));

    }
    else if(OI.target.get() == OI.Target.Side){
      console.log("TARGET: SIDE");

      Robot.autoCommand.addSequential(new AutoDrive(Robot.m_drive, 0.5, 0, 1000));
    }

    /////////////////////
    ////// STAGE 3 //////
    /////////////////////

    if(OI.target.get() == OI.Target.Face){

      console.log("TARGET: FACE (3)");

      Robot.autoCommand.addSequential( new AutoDrive(Robot.m_drive, 0.5, 0, 500));
      Robot.autoCommand.addSequential( new SolenoidAuto(Robot.hatchSol, Value.kReverse));
    }
    else if(OI.target.get() == OI.Target.Side){
      console.log("TARGET: SIDE (3)");

      Robot.autoCommand.addSequential( new SolenoidAuto(Robot.hatchSol, Value.kForward));
    }

    // Robot Auto Command Drive Controls
    Robot.autoCommand.addSequential(new AutoDrive(m_drive, 0.5, 0, 10));

    Robot.autoCommand.start();
  }

  @Override
  public void autonomousPeriodic() {

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

    OI.inputGrabberToggle.whenPressed(new ToggleHatchGrabState());

  }

  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();

    //////////////////
    //  BALL CTRL   //
    //////////////////

    // Ball Intake Control using Buttons
    if(OI.driver.getRawButton(LogitechMap_X.BUTTON_LB)){
      // Ball In
      ballIntake.set(0.5);
    }
    else if(OI.driver.getRawButton(LogitechMap_X.BUTTON_RB)){
      // Ball Out
      ballIntake.set(-0.5);
    }
    else {
      // Ball Off
      ballIntake.set(0);
    }


    //////////////////
    //  DRIVE CTRL  //
    //////////////////

    // Update the Side Value, Swapping Sides
    if(OI.directionSwitch.get()){
      RobotOrientation.getInstance().flipSide();
    }

    // Drive Shifting, High and Low Range
    if(OI.transButtonHigh.get()){
      transSol.set(Value.kForward);
    }
    else if(OI.transButtonLow.get()){
      transSol.set(Value.kReverse);
    }

    double DRIVE_Y = (OI.driver.getRawAxis(LogitechMap_X.AXIS_LEFT_Y)*0.8);
    double DRIVE_X = (-OI.driver.getRawAxis(LogitechMap_X.AXIS_RIGHT_X)*0.8);

    DRIVE_Y = RobotOrientation.getInstance().fix(DRIVE_Y, Side.kSideA);
    DRIVE_X = RobotOrientation.getInstance().fix(DRIVE_X, Side.kSideA);

    // Save.getInstance().push("drive_x", DRIVE_X);
    // Save.getInstance().push("drive_y", DRIVE_Y);
    
    Robot.m_drive.arcadeDrive( DRIVE_Y, DRIVE_X );

    if(RobotOrientation.getInstance().getSide() == Side.kSideA) {

      double speed = OI.driver.getRawButton(LogitechMap_X.BUTTON_START)?0.5:0;
      speed = OI.driver.getRawButton(LogitechMap_X.BUTTON_BACK)?-0.5:0;
      Robot.ballIntake.set( speed );

    }
    else {

      // Add a ButtonDeboucer Instace for the Button and Read and not toggle
      // fast. This is setup to Open Hold the Value when Held.
      boolean plate = OI.driver.getRawButton(LogitechMap_X.BUTTON_START);

      // Drive Shifting, High and Low Range
      if(plate == true){
        hatchSol.set(Value.kForward);
      }
      else {
        hatchSol.set(Value.kReverse);
      }

    }

  }

}
