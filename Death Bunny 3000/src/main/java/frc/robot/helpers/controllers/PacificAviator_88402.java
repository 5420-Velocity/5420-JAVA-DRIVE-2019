package frc.robot.helpers.controllers;

/**
 * PacificAviator_88402
 * The Map of the Logitech Extreme 3D Pro USB Controller
 * 
 * @author Noah Halstead <nhalstead00@gmail.com>
 */

public class PacificAviator_88402 {

    /**
     * Breakdown of the Controls in an Enum
     * 
     */
    public enum Button {
        CIRCLE(1),
        TRIANGLE(2),
        CROSS(3),
        SQUARE(4),
        UP(5),
        RIGHT(6),
        DOWN(7),
        LEFT(8),
        STICKL(9),
        STICKR(10),
        SELECT(11),
        START(12);

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
        X(0),
        Y(1),
        SLIDER(2),
        ROTATE(3);

        public final int value;

        Axis(int value) {
            this.value = value;
        }
    }

    public static final int BUTTON_CIRCLE = 1; // Button Value, Circle button
    public static final int BUTTON_TRIANGLE = 2; // Button Value, Triangle button
    public static final int BUTTON_CROSS = 3; // Button Value, Cross button
    public static final int BUTTON_SQUARE = 4; // Button Value, Square  button
    public static final int BUTTON_UP = 5; // Button Value, Up button
    public static final int BUTTON_RIGHT = 6; // Button Value, Right button

    public static final int BUTTON_DOWN = 7; // Button Value, Down button
    public static final int BUTTON_LEFT = 8; // Button Value, Left button
    public static final int BUTTON_STICKL = 9; // Button Value, Left stick button
    public static final int BUTTON_STICKR = 10; // Button Value, Right stick button
    public static final int BUTTON_SELECT = 11; // Button Value, Select button
    public static final int BUTTON_START = 12; // Button Value, Start button

    public static final int AXIS_X = 0; // Axis Value, Left X Axis
    public static final int AXIS_Y = 1; // Axis Value, Left Y Axis
    public static final int AXIS_SLIDER = 2; // Axis Value, r2, l2 throttle
    public static final int AXIS_ROTATE = 3; // Axis Value, Z axis rotation

}