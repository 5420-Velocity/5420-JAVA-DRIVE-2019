package frc.robot.helpers.controllers;

/**
 * LogitechE3DProMap
 * The Map of the Logitech Extreme 3D Pro USB Controller
 * 
 * @author Noah Halstead <nhalstead00@gmail.com>
 */

public class LogitechE3DProMap {

    /**
     * Breakdown of the Controls in an Enum
     * 
     */
    public enum Button {
        TRIGGER(0),
        THUMB(1),
        T3(3),
        T4(4),
        T5(5),
        T6(6),
        B7(7),
        B8(8),
        B9(9),
        B10(10),
        B11(11),
        B12(12);

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
        Rotate(2),
        Slider(3);

        public final int value;

        Axis(int value) {
            this.value = value;
        }
    }

    public static final int BUTTON_TRIGGER = 1; // Button Value, A Button
    public static final int BUTTON_THUMB = 2; // Button Value, B Button
    public static final int BUTTON_3 = 3; // Button Value, Button 3
    public static final int BUTTON_4 = 4; // Button Value, Button 4
    public static final int BUTTON_5 = 5; // Button Value, Button 5
    public static final int BUTTON_6 = 6; // Button Value, Button 6

    public static final int BUTTON_7 = 7; // Button Value, Button 6
    public static final int BUTTON_8 = 8; // Button Value, Button 6
    public static final int BUTTON_9 = 9; // Button Value, Button 6
    public static final int BUTTON_10 = 10; // Button Value, Button 6
    public static final int BUTTON_11 = 11; // Button Value, Button 6
    public static final int BUTTON_12 = 12; // Button Value, Button 6

    public static final int AXIS_X = 0; // Axis Value, Left X Axis
    public static final int AXIS_Y = 1; // Axis Value, Left Y Axis
    public static final int AXIS_ROTATE = 2; // Axis Value, Right X Axis
    public static final int AXIS_SLIDER = 3; // Axis Value, Right Y Axis

}