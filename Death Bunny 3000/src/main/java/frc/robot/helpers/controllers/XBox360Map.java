package frc.robot.helpers.controllers;

/**
 * XBox360Map
 * The Map of the XBox 360 USB Controller
 * 
 * @author Noah Halstead <nhalstead00@gmail.com>
 */

public class XBox360Map {

    /**
     * Breakdown of the Controls in an Enum
     * 
     */
    public enum Button {
        A(1),
        B(2),
        X(3),
        Y(4),
        LB(5),
        RB(6),
        BACK(7),
        START(8),
        LEFT(9),
        RIGHT(10);

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
        LT(4),
        RT(5);

        public final int value;

        Axis(int value) {
            this.value = value;
        }
    }

    public static int BUTTON_A = 1; // Button Value, A Button
    public static int BUTTON_B = 2; // Button Value, B Button
    public static int BUTTON_X = 3; // Button Value, X Button
    public static int BUTTON_Y = 4; // Button Value, Y Button

    public static int BUTTON_LB = 5; // Button Value, LB Button
    public static int BUTTON_RB = 6; // Button Value, RB Button
    public static int BUTTON_BACK = 7; // Button Value, BACK Button
    public static int BUTTON_START = 8; // Button Value, Start Button

    public static int BUTTON_LEFT = 9; // Button Value, Left Axis Button
    public static int BUTTON_RIGHT = 10; // Button Value, Right Axis Button

    public static int AXIS_LEFT_X = 0; // Axis Value, Left X Axis
    public static int AXIS_LEFT_Y = 1; // Axis Value, Left Y Axis
    public static int AXIS_RIGHT_X = 4; // Axis Value, Right X Axis
    public static int AXIS_RIGHT_Y = 5; // Axis Value, Right Y Axis

    public static int AXIS_LT = 2; // Axis Value, LT Axis, Only 0 to +1
    public static int AXIS_RT = 3; // Axis Value, RT Axis, Only 0 to +1

}