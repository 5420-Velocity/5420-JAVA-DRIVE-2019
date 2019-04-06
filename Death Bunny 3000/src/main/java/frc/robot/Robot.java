/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.Solenoid;
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
import frc.robot.helpers.console.logMode;
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

  public static Ultrasonic frontSide;
  public static DigitalInput hatchSwitchAutoClose, ballLoaded, upperLimit, lowerLimit, ballUpperLimit, ballLowerLimit;
  public static DoubleSolenoid hatchSol; // Put Solenoid to the Open State
  public static Solenoid robotLiftF, robotLiftR;
  public static Encoder leftEncoder, rightEncoder;
  public static AnalogPotentiometer liftEncoder;

  public static VictorSP motorLift, motorTilt, ballIntake2, motorLock, ballIntake;

  public static DoubleSolenoid winchBreak;

  public static Compressor compressor;
  public static CommandGroup autoCommand;

  public static Limelight limelightMain;

  public static char[] gameData;

  // Used to Store the Table Entries for the Network Table.
  //  Stored as an Associative Array to the NetowrkTable Entry
  // Map<String, NetworkTableEntry> tableIndex = new HashMap<String, NetworkTableEntry>();

  public static NetworkTableEntryStore tableIndex;

  public static int DRIVER = 0;
  public static int OPERATOR = 1;
  public static int CTRL_LOG_INTERVAL = 40*4;

  public static boolean liftOnce = false;

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

    // Right SIDE Control
    Robot.right1 = new WPI_TalonSRX(56);

    Robot.right2 = new WPI_TalonSRX(57);
    Robot.right2.follow(Robot.right1);

    // Build a full Differental Drive
    Robot.m_drive = new DifferentialDrive(Robot.left1, Robot.right1);

    Robot.pigeon = new PigeonGyro(left1);

    frontSide = new Ultrasonic(18, 19);
    leftEncoder = new Encoder(4, 5);
    rightEncoder = new Encoder(6, 7);

    hatchSol = new DoubleSolenoid(0, 1);
    winchBreak = new DoubleSolenoid(2, 3);
    transSol = new DoubleSolenoid(4, 5);
    robotLiftF = new Solenoid(6);
    robotLiftR = new Solenoid(7);

    upperLimit = new DigitalInput(0);
    lowerLimit = new DigitalInput(1);
    hatchSwitchAutoClose = new DigitalInput(8);
    ballLoaded = new DigitalInput(9);
    ballUpperLimit = new DigitalInput(10);
    ballLowerLimit = new DigitalInput(11);
    liftEncoder = new AnalogPotentiometer(0, 360, 30);

    
    motorLift = new VictorSP(1);
    motorTilt = new VictorSP(4);
    ballIntake2 = new VictorSP(3);
    motorLock = new VictorSP(2);
    ballIntake = new VictorSP(5);

    compressor = new Compressor(0);

    SmartDashboard.setDefaultNumber("Test", 0);
    SmartDashboard.setDefaultNumber("AutoDelay", 0);
    SmartDashboard.setDefaultBoolean("Sol", false);

    m_drive.setExpiration(0.2); // Default is 0.1
    //m_drive.setSafetyEnabled(true); // Disable Watchdog auto stop

    // Save.getInstance().push("Test", false);

    limelightMain = new Limelight("limelight-one");
    limelightMain.setDistanceControl(OI.LimelightKD, OI.LimelightKA);

    console.allowLog = logMode.kOff;

    // Setup Auto CTRL
    OI.Apply();
  }

  @Override
  public void robotPeriodic() {

    // Save.getInstance().push("t", System.currentTimeMillis());
    // Save.getInstance().push("batt", RobotController.getBatteryVoltage());

    Robot.logInterval++;
    if(logInterval % CTRL_LOG_INTERVAL == 0){
      Logger.pushCtrlValues("Driver", OI.driver);
      Logger.pushCtrlValues("Operator", OI.operator);

      Robot.logInterval = 0;
    }

    // Push Sensor Values
    OI.leftEncoder.setNumber(Robot.leftEncoder.get());
    OI.rightEncoder.setNumber(Robot.rightEncoder.get());
    OI.liftEncoder.setNumber(Robot.liftEncoder.get());
    OI.ballSwitch.setBoolean(Robot.ballLoaded.get());
    OI.hatchSwitch.setBoolean(Robot.hatchSwitchAutoClose.get());
    OI.gyro.setNumber(Robot.pigeon.getAngle());
    OI.distanceFront.setNumber(Robot.frontSide.getRangeInches());
    OI.limitLower.setBoolean(Robot.lowerLimit.get());
    OI.limitUpper.setBoolean(Robot.upperLimit.get());
    OI.ballUppwerLimit.setBoolean(ballUpperLimit.get());
    OI.ballLowerLimit.setBoolean(ballLowerLimit.get());

    OI.LimelightDistance.setNumber(Robot.limelightMain.getDistance());
    OI.limelightV.setBoolean(Robot.limelightMain.hasTarget());
    OI.limelightA.setNumber(Robot.limelightMain.getA());
    OI.limelightS.setNumber(Robot.limelightMain.getS());

    //OI.boschEncoder.setNumber(Robot.bTest.encoderGet());

    if(OI.reset.getBoolean(false)){
      Robot.pigeon.reset();
      OI.reset.setBoolean(false);
    }

    if(OI.resetEncoder.getBoolean(false)){
      Robot.leftEncoder.reset();
      Robot.rightEncoder.reset();
      OI.resetEncoder.setBoolean(false);
    }

    if(OI.debugLogEnabled.getBoolean(false) == false){
      console.allowLog = logMode.kOff;
    }
    else {
      console.allowLog = logMode.kAll;
    }

    // Save.getInstance().sync();
  }

  @Override
  public void disabledInit(){

  }

  @Override
  public void disabledPeriodic(){

    if(OI.limelightLEDOn.getBoolean(false) == false){
      Robot.limelightMain.setLed(Limelight.ledMode.kOff);
    }
    else {
      Robot.limelightMain.setLed(Limelight.ledMode.kOn);
    }

  }

  @Override
  public void testInit() {
    // Enable Compressor Control
    compressor.setClosedLoopControl(true);

    console.out(logMode.kDebug, "Set LED Mode On");

    Robot.limelightMain.setLed(Limelight.ledMode.kOn);

    // Drive the Robot Forward for a Set Point of 10,000 Ticks using the Right Encoder
    console.out(logMode.kDebug, "Testing Init Start");

    // Drive the Robot using an Encoder to a set point.
    Scheduler.getInstance().add(new AutoDriveEncoder(Robot.m_drive, Robot.leftEncoder, 0.5, 0, 10000));

    // Using the Given Drive, Turn to the Object until the value is within a margin.
    //Scheduler.getInstance().add(new Limelight_turn(Robot.m_drive, Robot.limelightMain));

    // Drive and follow the object detected in the LL program.
    //Scheduler.getInstance().add(new LimelightFollow(Robot.m_drive, Robot.limelightMain, 0));

    console.out(logMode.kDebug, "Testing Init Finish");
  }

  @Override
  public void testPeriodic() {

    Scheduler.getInstance().run();

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
        Robot.autoCommand.addSequential( new AutoDrive(Robot.m_drive, 0.8, 0, 2000) );

      }

      // Level 2
      else if(OI.level.get() == 2){
        console.log("LEVEL 2");

        //drive
        Robot.autoCommand.addSequential( new AutoDrive(Robot.m_drive, 0.8, 0, 3000));

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
        Robot.autoCommand.addSequential( new AutoDrive(Robot.m_drive, 0.5, 0, 800));

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
    //Robot.autoCommand.addSequential(new AutoDrive(m_drive, 0.5, 0, 10));

    // Add to Stack
    Scheduler.getInstance().add(Robot.autoCommand);
    
  }

  @Override
  public void autonomousPeriodic() {

    Scheduler.getInstance().run();

    if(Robot.autoCommand.isCompleted()){
      this.teleopPeriodic(); // Run Robot Telelop Code
    }

  }

  @Override
  public void teleopInit() {
    // Turn Limelight Off
    Robot.limelightMain.setLed(Limelight.ledMode.kOn);

    // Stop Auto Commands

    if(Robot.autoCommand != null){
      Robot.autoCommand.cancel();
    }

  }

  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();

    if(OI.driver.getRawButton(LogitechMap_X.BUTTON_A)){
      // Add Code to run the robot.
      //Scheduler.getInstance().add();

    }
    else {
      // Add code to Cancel the Program
    }

    /////////////////
    /// SIDE CTRL ///
    /////////////////
    // Update the Side Value, Swapping Sides
    if(OI.directionSwitch.get()){
      RobotOrientation.getInstance().flipSide();
    }
    
    /////////////////
    ///   LIFT    ///
    /////////////////
    if(OI.operator.getRawButton(LogitechMap_X.BUTTON_LB) && OI.operator.getRawButton(LogitechMap_X.BUTTON_RB)){
      // Go Up bypass the Upper Limit
      console.out(logMode.kDebug, "BYPASS UPPER LIMIT, UP");
      Robot.motorLift.set(0.4); // Up
      Robot.winchBreak.set(Value.kReverse); // Break Off
    }
    else if(OI.operator.getRawButton(LogitechMap_X.BUTTON_LB)){
      if(!Robot.upperLimit.get() == false){
        // Upperlimit is setup to give a signal as true as open.
        Robot.motorLift.set(0.9); // Up
        Robot.winchBreak.set(Value.kReverse); // Break Off
      }
      else {
        console.out(logMode.kDebug, "Upper Limit");
        Robot.motorLift.set(0); // Off
        Robot.winchBreak.set(Value.kForward); // Break On  
      }
    }
    else if(OI.operator.getRawButton(LogitechMap_X.BUTTON_RB)){
      if(Robot.lowerLimit.get() == false){
        Robot.motorLift.set(-0.5); // Down
        Robot.winchBreak.set(Value.kReverse); // Break Off
      }
      else {
        console.out(logMode.kDebug, "Lower Limit");
        Robot.motorLift.set(0); // Off
        Robot.winchBreak.set(Value.kForward); // Break On  
      }
    }
    else {
      Robot.motorLift.set(0); // Off
      Robot.winchBreak.set(Value.kForward); // Break On
    }
    
    //////////////////
    //  BALL CTRL   //
    //////////////////
    // Ball Intake Control using Buttons
    if(OI.driver.getRawButton(LogitechMap_X.BUTTON_LB)){
      // Ball In
      if(Robot.ballLoaded.get() == false){

        ballIntake.set(-0.5);
        ballIntake2.set(0.5);
      }
      else {
        console.log("Ball Loaded, Auto Stop");
        // Ball Off
        ballIntake.set(0);
        ballIntake2.set(0);
        if(liftOnce == false){
          // Disabled since the button in the lift was too sensitive.
          //Scheduler.getInstance().add( new MotorDrive(Robot.motorTilt, 0.8, 2000, "controlArm") );
          liftOnce = true;
        }
      }
    }
    else if(OI.driver.getRawButton(LogitechMap_X.BUTTON_RB)){
      // Ball Out
      ballIntake.set(0.8);
      ballIntake2.set(-0.8);
      liftOnce = false;
    }
    else {
      // Ball Off
      ballIntake.set(0);
      ballIntake2.set(0);
    }

    /////////////////
    ///   HATCH   ///
    /////////////////
    if(OI.hatchButton.get()){
      hatchSol.set(Value.kReverse); // In
    }
    else if(OI.hatchButtonOut.get()){
      hatchSol.set(Value.kForward); // Out
    }
    else {
      if(Robot.hatchSwitchAutoClose.get()){
        // Defalt State Control, If the User is not Pushing the Button
        hatchSol.set(Value.kReverse); // In
      }
      else {
        if(window.getTime() != -1 && window.getTime() < 10){
          // Hatch Close in the Last Part of the Match Time.
          hatchSol.set(Value.kReverse); // In
        }
        else {
          // Defalt State Control, If the User is not Pushing the Button
          hatchSol.set(Value.kForward); // Out
        }
      }
    }


    //////////////////
    //  SHIFT CTRL  //
    //////////////////
    if(OI.transButtonHigh.get()){
      transSol.set(Value.kForward); // High Gear
      OI.driveShift.setString("HIGH");
    }
    else if(OI.transButtonLow.get()){
      transSol.set(Value.kReverse); // LowGear
      OI.driveShift.setString("LOW");
    }
    
    
    //////////////////
    //  DRIVE CTRL  //
    //////////////////
    double DRIVE_Y = (OI.driver.getRawAxis(LogitechMap_X.AXIS_LEFT_Y));
    double DRIVE_X = (-OI.driver.getRawAxis(LogitechMap_X.AXIS_RIGHT_X));
    DRIVE_Y = RobotOrientation.getInstance().fix(DRIVE_Y, Side.kSideB);

    if(OI.driveSlowForward.get()){
      // Button Mode Forward
      console.out(logMode.kDebug, "Slow Forward");
      DRIVE_Y = 0.3;
      DRIVE_X = 0;
    }
    else if(OI.driveSlowReverse.get()){
      // Button Mode Reverse
      console.out(logMode.kDebug, "Slow Reverse");
      DRIVE_Y = -0.3;
      DRIVE_X = 0;
    }
    else if(OI.driveSlowLeft.get()){
      // Button Mode Reverse
      console.out(logMode.kDebug, "Slow Left");
      DRIVE_Y = 0;
      DRIVE_X = 0.3;
    }
    else if(OI.driveSlowRight.get()){
      // Button Mode Reverse
      console.out(logMode.kDebug, "Slow Right");
      DRIVE_Y = 0;
      DRIVE_X = -0.3;
      ;
    }
    else {
      // Joystick Mode
      DRIVE_Y = DRIVE_Y*0.75;
      DRIVE_X = DRIVE_X*0.95;
    }

    /**
     * Auto Turn Control using Limelight
     *
     * p = 
     *    0.5 = P * tX
     * 0.5 is the target speed @ tX distance.
     */
    if(OI.autoTurnCtrl.get() == true){

      DRIVE_X = -0.048 * Robot.limelightMain.getX();

      // Covers the Sensor if its not connected.
      double range = Robot.limelightMain.getDistance();
      if(range > 200){
        range = 1;
      }
      DRIVE_Y = 0.18 * range;

      console.out(logMode.kDebug, ">> " + DRIVE_X + "  " + DRIVE_Y);
      OI.LLCtrl.setBoolean(true);
    }
    else {
      console.out(logMode.kDebug, ":: " + DRIVE_X);
      OI.LLCtrl.setBoolean(false);
    }
    Robot.m_drive.arcadeDrive( DRIVE_Y, DRIVE_X );


    ////////////////////////
    //////// SIDE A ////////
    ////////////////////////
    if(RobotOrientation.getInstance().getSide() == Side.kSideA) {

      OI.cameraView.setNumber(1);
      OI.cameraViewText.setString("HATCH");
    }
    ////////////////////////
    //////// SIDE B ////////
    ////////////////////////
    else {

      OI.cameraView.setNumber(0);
      OI.cameraViewText.setString("BALL");
    }
   
    ///////////////////////////////////////
    ////  Front and Rear Lift Control  ////
    ///////////////////////////////////////
    if(OI.driver.getRawButton(LogitechMap_X.BUTTON_START)){
      Robot.robotLiftR.set(true);
      console.out(logMode.kDebug, "UP! UP! AND AWAY!");
    }
    else {
      Robot.robotLiftR.set(false);
    }

    if(OI.driver.getRawButton(LogitechMap_X.BUTTON_BACK)){
      Robot.robotLiftF.set(true);
      console.out(logMode.kDebug, "UP! UP! AND AWAY! FRONT");
    }
    else {
      Robot.robotLiftF.set(false);
    }

    //Robot.motorTilt.set(OI.operator.getRawAxis(LogitechMap_X.AXIS_LEFT_Y));
    //Robot.motorLock.set(OI.operator.getRawAxis(LogitechMap_X.AXIS_LEFT_Y));

    double controlArm = -OI.operator.getRawAxis(LogitechMap_X.AXIS_LEFT_Y);
    if(controlArm > 0){
      if(ballUpperLimit.get() != true){
        // Allow if button is true, Wired for Cut Wire Saftey
        controlArm = 0;
      }
      else{
        // Limit the Up to %50 max power
        controlArm = controlArm*0.85;
      }
    }
    else if (controlArm > 0){
      Robot.motorTilt.set(0);
    }
    else {
      // Limit the Down to 85% max power
      controlArm = controlArm*0.85;
    }

    if(Locker.isLocked("controlArm") == false){
      Robot.motorTilt.set(controlArm);
    }
    else {
      if(controlArm != 0){
        console.log("User Control Ignored, Locked");
      }
    }


    //////////////////////
    //// Lift Control ////
    //////////////////////
    if(OI.liftTop.get()){
      console.out(logMode.kDebug, "Top");
    }
    else if(OI.liftMid.get() || OI.liftMidAlt.get()){
      console.out(logMode.kDebug, "Mid");
    }
    else if(OI.liftBottom.get()){
      console.out(logMode.kDebug, "Bottom");
    }

  }
  

  /**
   * Is Value between the given input.
   * 
   * @param Input
   * @param trueZone
   * @return
   */
  public boolean between(double Input, double trueZone){
    return (Math.abs(Input) < trueZone);
  }

}
