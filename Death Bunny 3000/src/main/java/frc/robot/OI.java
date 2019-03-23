package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.helpers.ButtonDebouncer;
import frc.robot.helpers.ButtonToggle;
import frc.robot.helpers.console;
import frc.robot.helpers.controllers.DPadButton;
import frc.robot.helpers.controllers.DPadButtonDebouce;
import frc.robot.helpers.controllers.LogitechMap_X;
import frc.robot.helpers.DropSelection;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    public enum Target {
        None,
        Side,
        Face
    }

    public enum SideTarget {
        Far,
        Mid,
        Close
    }

    public enum FaceTarget {
        Left,
        Right
    }

    // getDistance Param Values
    public static final double LimelightKD = 4;
    public static final double LimelightKA = 2.5409;

    public static Joystick driver;
    public static JoystickButton inputGrabberToggle;
    public static Joystick operator;
    
    public static ButtonDebouncer directionSwitch;
    public static ButtonDebouncer transButtonHigh;
    public static ButtonDebouncer transButtonLow;

    public static DPadButtonDebouce liftTop;
    public static DPadButtonDebouce liftMid;
    public static DPadButtonDebouce liftMidAlt;
    public static DPadButtonDebouce liftBottom;

    public static DPadButton driveSlowForward;
    public static DPadButton driveSlowReverse;
    public static DPadButton driveSlowLeft;
    public static DPadButton driveSlowRight;

    public static JoystickButton hatchButton;
    public static JoystickButton hatchButtonOut;
    
    public static NetworkTableInstance tableInstance;
    public static NetworkTable table;

    public static NetworkTableEntry cameraView;
    public static NetworkTableEntry cameraViewText;
    public static NetworkTableEntry leftEncoder;
    public static NetworkTableEntry rightEncoder;
    public static NetworkTableEntry liftEncoder;
    public static NetworkTableEntry gyro;
    public static NetworkTableEntry LimelightDistance;
    public static NetworkTableEntry hatchSwitch;
    public static NetworkTableEntry ballSwitch;
    public static NetworkTableEntry reset;
    public static NetworkTableEntry resetEncoder;
    public static NetworkTableEntry distanceFront;
    public static NetworkTableEntry limitUpper;
    public static NetworkTableEntry limitLower;
    public static NetworkTableEntry boschSpeed;
    public static NetworkTableEntry boschEncoder;
    public static NetworkTableEntry autoDelay;
    public static NetworkTableEntry limelightA;
    public static NetworkTableEntry limelightS;
    public static NetworkTableEntry limelightV;
    public static NetworkTableEntry driveShift;
    public static NetworkTableEntry ballUppwerLimit;
    public static NetworkTableEntry ballLowerLimit;
    public static NetworkTableEntry limelightLEDOn;

    public static DropSelection<Integer> position;
    public static DropSelection<Integer> level;
    public static DropSelection<Target> target;
    public static DropSelection<SideTarget> targetSide;
    public static DropSelection<FaceTarget> targetFace;

    
    public OI() {

        position = new DropSelection<Integer>("pos");
        level = new DropSelection<Integer>("level");
        target = new DropSelection<Target>("target");
        targetSide = new DropSelection<SideTarget>("targetSide");
        targetFace = new DropSelection<FaceTarget>("targetFace");


        // Put Some buttons on the SmartDashboard and get the Table Entries
        tableInstance = NetworkTableInstance.getDefault(); // Get the Driver Station Network Table Instance.
        table = tableInstance.getTable("SmartDashboard"); // Add the a table just for Sensor Data.
        cameraView = table.getEntry("camView");
        cameraViewText = table.getEntry("camViewText");
        leftEncoder = table.getEntry("leftEncoder");
        rightEncoder = table.getEntry("rightEncoder");
        liftEncoder = table.getEntry("liftEncoder");
        gyro = table.getEntry("gyro");
        autoDelay = table.getEntry("autoDelay");
        LimelightDistance = table.getEntry("limelightDistance");
        hatchSwitch = table.getEntry("hatchSwitch");
        ballSwitch = table.getEntry("ballSwitch");
        reset = table.getEntry("gyroReset");
        resetEncoder = table.getEntry("encoderReset");
        distanceFront = table.getEntry("frontDistance");
        limitUpper = table.getEntry("upperLimit");
        limitLower = table.getEntry("lowerLimit");
        boschSpeed = table.getEntry("boschSpeed");
        boschEncoder = table.getEntry("boschEncoder");
        limelightA = table.getEntry("llA");
        limelightS = table.getEntry("llS");
        limelightV = table.getEntry("llV");
        driveShift = table.getEntry("driveShift");
        ballUppwerLimit = table.getEntry("ballUpperLimit");
        ballLowerLimit = table.getEntry("ballLowerLimit");
        limelightLEDOn = table.getEntry("limelightLEDOn");

        LimelightDistance.setDefaultNumber(0);
        cameraView.setDefaultNumber(0);
        reset.setDefaultBoolean(false);
        resetEncoder.setDefaultBoolean(false);
        distanceFront.setDefaultNumber(0);
        limitUpper.setDefaultBoolean(false);
        limitLower.setDefaultBoolean(false);

        driver = new Joystick(Robot.DRIVER);
        operator = new Joystick(Robot.OPERATOR);

        //// Create Buttons ////

        driver.setRumble(RumbleType.kLeftRumble, 0);
        directionSwitch = new ButtonDebouncer(driver, LogitechMap_X.BUTTON_B, 0.8);
        transButtonHigh = new ButtonDebouncer(driver, LogitechMap_X.BUTTON_Y, 0.8); // High Range
        transButtonLow = new ButtonDebouncer(driver, LogitechMap_X.BUTTON_X, 0.8);  // Low Range

        inputGrabberToggle = new JoystickButton(driver, LogitechMap_X.BUTTON_A);
        //hatchButton = new Button(operator, LogitechMap_X.BUTTON_A);
        hatchButton = new JoystickButton(operator, LogitechMap_X.BUTTON_A);
        hatchButtonOut = new JoystickButton(operator, LogitechMap_X.BUTTON_Y);

        liftTop = new DPadButtonDebouce(operator, DPadButton.Direction.Up);
        liftBottom = new DPadButtonDebouce(operator, DPadButton.Direction.Down);
        liftMid = new DPadButtonDebouce(operator, DPadButton.Direction.Right);
        liftMidAlt = new DPadButtonDebouce(operator, DPadButton.Direction.Left);

        driveSlowForward = new DPadButton(driver, DPadButton.Direction.Up);
        driveSlowReverse = new DPadButton(driver, DPadButton.Direction.Down);
        driveSlowLeft = new DPadButton(driver, DPadButton.Direction.Left);
        driveSlowRight = new DPadButton(driver, DPadButton.Direction.Right);

        //// Configure Controls ////

        boschSpeed.setDefaultNumber(0.0);
        boschEncoder.setDefaultNumber(0.0);

        limelightLEDOn.setDefaultBoolean(false);

        limelightA.setDefaultNumber(0);
        limelightS.setDefaultNumber(0);
        limelightV.setDefaultBoolean(false);

        ballUppwerLimit.setDefaultBoolean(false);
        ballLowerLimit.setDefaultBoolean(false);

        driveShift.setDefaultString("LAST-STATE");

        limelightLEDOn.setDefaultBoolean(false);

        console.log("OI Setup.");
    }

    /**
     * Setup 
     * 
     */
    public static void Apply(){

        autoDelay.setDefaultNumber(0);

        // Define the Position Contorls
        position.add("Left", true, 1);
        position.add("Center", 2);
        position.add("Right", 3);

        // Define the Levels Contorls
        level.add("Level 1 (Low)", true, 1);
        level.add("Level 2 (Med)", 2);
        level.add("Level 3 (High)", 3);

        // Add options to target the Hab eithrer by the Face or Side
        target.add("No Auto", OI.Target.None);
        target.add("Face of Hab", OI.Target.Face);
        target.add("Side of Hab", true, OI.Target.Side);

        // targetSide Options
        targetSide.add("Far", OI.SideTarget.Far);
        targetSide.add("Middle", OI.SideTarget.Mid);
        targetSide.add("Close", true, OI.SideTarget.Close);

        // targetFace Options
        targetFace.add("Left", true, OI.FaceTarget.Left);
        targetFace.add("Right", OI.FaceTarget.Right);

        
        // Send data to driverstation
        position.send();
        level.send();
        target.send();
        targetSide.send();
        targetFace.send();
    }

}