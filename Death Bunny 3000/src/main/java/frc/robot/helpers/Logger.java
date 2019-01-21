package frc.robot.helpers;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

/**
 * Tools
 * This class is to Log Data to the Console
 * 
 * @author Noah Halstead <nhalstead00@gmail.com>
 */
public class Logger {

    /**
     * 
     * 
     * @param joy  Joystick get the Values from.
     */
    public static void pushCtrlValues(Joystick joy){
        pushCtrlValues("Unknown", joy);
    }

    /**
     * 
     * 
     * @param text String value to Prepend to the Output
     * @param joy  Joystick get the Values from.
     */
    public static void pushCtrlValues(String text, Joystick joy){
        // Log the X Y Values from a Controller for Joy 1 and Joy 2
        //console.log(text, ": ", Double.toString(joy.getX()), " ", Double.toString(joy.getY()), " ", Double.toString(joy.getZ()));
        console.log(text, ": LX", Double.toString(joy.getX(Hand.kLeft)), " LY", Double.toString(joy.getY(Hand.kLeft)), "RX", Double.toString(joy.getX(Hand.kRight)), " RY", Double.toString(joy.getY(Hand.kLeft)) );
    }

    /**
     * 
     * 
     * @param text String value to Prepend to the Output
     * @param joy  XboxController get the Values from.
     */
    public static void pushCtrlValues(String text, XboxController joy){
        // Log the X Y Values from a Controller for Joy 1 and Joy 2
        console.log(text, ": LX", Double.toString(joy.getX(Hand.kLeft)), " LY", Double.toString(joy.getY(Hand.kLeft)), "RX", Double.toString(joy.getX(Hand.kRight)), " RY", Double.toString(joy.getY(Hand.kLeft)) );
    }

    /**
     * 
     * 
     * @param joy  XboxController get the Values from.
     */
    public static void pushCtrlValues(XboxController joy){
        pushCtrlValues("Unknown", joy);
    }

}