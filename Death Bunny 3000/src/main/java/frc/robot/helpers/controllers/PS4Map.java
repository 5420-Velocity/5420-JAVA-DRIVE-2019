package frc.robot.helpers.controllers;

/**
 * PS4Map
 * The Map of the PS4 USB Controller
 * 
 * @author Noah Halstead <nhalstead00@gmail.com>
 */

public class PS4Map {

    /**
     * Breakdown of the Controls in an Enum
     * 
     */
    public enum Button {
        SQUARE(1),
        CROSS(2),
        CIRCLE(3),
        TRIANGLE(4),
        LB(5), // L1
        RB(6), // R1
        LT(7), // L2
        RT(8), // R2
        SHARE(9),
        OPTIONS(10),
        LEFT(11),
        RIGHT(12),
        PS4(13), 
        TRACKPAD (14);

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
        LEFT_X(0),
        LEFT_Y(1),
        RIGHT_X(2),
        RIGHT_Y(3),
	    LEFT_TRIGGER(3),
	    RIGHT_TRIGGER(4);


        public final int value;

        Axis(int value) {
            this.value = value;
        }
    }

    public static final int BUTTON_SQUARE = 1; // Button Value, A Button
    public static final int BUTTON_CROSS = 2; // Button Value, B Button
    public static final int BUTTON_CIRCLE = 3; // Button Value, X Button
    public static final int BUTTON_TRIANGLE = 4; // Button Value, Y Button

    public static final int BUTTON_LB = 5; // Button Value, LB Button
    public static final int BUTTON_RB = 6; // Button Value, RB Button
    public static final int BUTTON_LT = 7; // Button Value, LT Button
    public static final int BUTTON_RT = 8; // Button Value, RT Button

    public static final int BUTTON_SHARE = 9; // Button Value, BACK Button
    public static final int BUTTON_OPTIONS = 10; // Button Value, Start Button
    public static final int BUTTON_LEFT = 11; // Button Value, Left Axis Button
    public static final int BUTTON_RIGHT = 12; // Button Value, Right Axis Button
    public static final int BUTTON_PS4 = 13; // Button Value
    public static final int BUTTON_TRACKPAD = 14; // Button Value

    public static final int AXIS_LEFT_X = 0; // Axis Value, Left X Axis
    public static final int AXIS_LEFT_Y = 1; // Axis Value, Left Y Axis
    public static final int AXIS_RIGHT_X = 2; // Axis Value, Right X Axis
    public static final int AXIS_RIGHT_Y = 3; // Axis Value, Right Y Axis
    public static final int AXIS_LEFT_TRIGGER = 4;
    public static final int AXIS_RIGHT_TRIGGER = 5;


}