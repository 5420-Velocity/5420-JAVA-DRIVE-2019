package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.helpers.ButtonDebouncer;
import frc.robot.helpers.Save;
import frc.robot.helpers.console;
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

    public static Joystick driver;
    public static JoystickButton inputGrabberToggle;
    public static Joystick operator;
    
    public static ButtonDebouncer directionSwitch;
    public static ButtonDebouncer transButtonHigh;
    public static ButtonDebouncer transButtonLow;
    
    public static NetworkTableInstance tableInstance;
    public static NetworkTable table;
    public static NetworkTableEntry cameraView;
    public static NetworkTableEntry leftEncoder;
    public static NetworkTableEntry rightEncoder;
    public static NetworkTableEntry gyro;

    public static NetworkTableEntry autoDelay;
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


        // Put Some buttons on the SmartDashboard
        tableInstance = NetworkTableInstance.getDefault(); // Get the Driver Station Network Table Instance.
        table = tableInstance.getTable("SmartDashboard"); // Add the a table just for Sensor Data.
        cameraView = table.getEntry("camView");
        leftEncoder = table.getEntry("leftEncoder");
        rightEncoder = table.getEntry("rightEncoder");
        gyro = table.getEntry("gyro");
        autoDelay = table.getEntry("autoDelay");

        cameraView.setDefaultNumber(0);

        // Create some buttons
        driver = new Joystick(Robot.DRIVER);
        driver.setRumble(RumbleType.kLeftRumble, 0);
        directionSwitch = new ButtonDebouncer(driver, LogitechMap_X.BUTTON_B, 0.8);
        transButtonHigh = new ButtonDebouncer(driver, LogitechMap_X.BUTTON_Y, 0.8); // Low Range
        transButtonLow = new ButtonDebouncer(driver, LogitechMap_X.BUTTON_X, 0.8);  // High Range
        inputGrabberToggle = new JoystickButton(driver, LogitechMap_X.BUTTON_A);

        operator = new Joystick(Robot.OPERATOR);

        console.log("OI Setup.");
    }

    /**
     * Setup 
     * 
     */
    public static void Apply(){

        autoDelay.setNumber(0);

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