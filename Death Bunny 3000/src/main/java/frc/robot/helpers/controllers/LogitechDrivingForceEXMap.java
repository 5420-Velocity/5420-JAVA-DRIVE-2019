package frc.robot.helpers.controllers;

/**
 * LogitechDrivingForceEXMap
 * The Map of the Logitech Driving Force EX USB Controller
 * 
 * @author Noah Halstead <nhalstead00@gmail.com>
 */

public class LogitechDrivingForceEXMap {

    /**
     * Breakdown of the Controls in an Enum
     * 
     */
    public enum Button {
        X(0),
        Square(1),
        Circle(2),
        Triangle(3),
        PaddleRight(4),
        PaddleLeft(5),
        R2(6),
        L2(7),
        Select(8),
        Start(9),
        R3(10),
        L3(11);
        

        public final int value;

        Button(int value) {
            this.value = value;
        }
    }

    /**
     * Breakdown of the Controls in an Enum
     * 
     */
    public enum Axis {
        Steering(0), // Left -1, Center 0, Right 1
        GasBreak(1); // On a Range from -1 to 1; This is for Break and Gas; Gas 1, Break -1, Neutral 0

        public final int value;

        Axis(int value) {
            this.value = value;
        }
    }

    public static final int BUTTON_X = 0; // Button Value, A Button
    public static final int BUTTON_SQUARE = 1; // Button Value, B Button
    public static final int BUTTON_CIRCLE = 2; // Button Value, Button 3
    public static final int BUTTON_TRIANGLE = 3; // Button Value, Button 4
    public static final int BUTTON_PADDLERIGHT = 4; // Button Value, Button 5
    public static final int BUTTON_PADDLELEFT = 5; // Button Value, Button 6

    public static final int BUTTON_R2 = 6; // Button Value, Button 6
    public static final int BUTTON_L2 = 7; // Button Value, Button 6
    public static final int BUTTON_Select = 8; // Button Value, Button 6
    public static final int BUTTON_Start = 9; // Button Value, Button 6
    public static final int BUTTON_R3 = 10; // Button Value, Button 6
    public static final int BUTTON_L3 = 11; // Button Value, Button 6

    public static final int AXIS_X = 0; // Axis Value, Left X Axis
    public static final int AXIS_Y = 1; // Axis Value, Left Y Axis

}