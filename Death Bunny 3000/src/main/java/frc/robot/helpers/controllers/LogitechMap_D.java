package frc.robot.helpers.controllers;

/**
 * LogitechMap
 * The Map of the Logitech F310 USB Controller in
 *  switch Mode D.
 * 
 * @author Noah Halstead <nhalstead00@gmail.com>
 */

public class LogitechMap_D {

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
        LT(7),
        RT(8),
        BACK(9),
        START(10),
        LEFT(11),
        RIGHT(12);

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
        RIGHT_Y(3);

        public final int value;

        Axis(int value) {
            this.value = value;
        }
    }

    public static final int BUTTON_A = 1; // Button Value, A Button
    public static final int BUTTON_B = 2; // Button Value, B Button
    public static final int BUTTON_X = 3; // Button Value, X Button
    public static final int BUTTON_Y = 4; // Button Value, Y Button

    public static final int BUTTON_LB = 5; // Button Value, LB Button
    public static final int BUTTON_RB = 6; // Button Value, RB Button
    public static final int BUTTON_LT = 7; // Button Value, LT Button
    public static final int BUTTON_RT = 8; // Button Value, RT Button
    public static final int BUTTON_BACK = 9; // Button Value, BACK Button
    public static final int BUTTON_START = 10; // Button Value, Start Button

    public static final int BUTTON_LEFT = 11; // Button Value, Left Axis Button
    public static final int BUTTON_RIGHT = 12; // Button Value, Right Axis Button

    public static final int AXIS_LEFT_X = 0; // Axis Value, Left X Axis
    public static final int AXIS_LEFT_Y = 1; // Axis Value, Left Y Axis
    public static final int AXIS_RIGHT_X = 4; // Axis Value, Right X Axis
    public static final int AXIS_RIGHT_Y = 5; // Axis Value, Right Y Axis

}