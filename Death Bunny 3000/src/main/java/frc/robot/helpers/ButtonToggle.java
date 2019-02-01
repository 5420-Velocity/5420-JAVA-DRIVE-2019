package frc.robot.helpers;

import edu.wpi.first.wpilibj.Joystick;

/**
 * ButtonToggle
 * This class will use ButtonDebouncer to Operate but will
 *  be able to Toggle State on Input not only when the
 *  input is detected.
 *  
 * @author Noah Halstead <nhalstead00@gmail.com>
 */

public class ButtonToggle extends ButtonDebouncer {

    private boolean lastState = false;

    /**
     * Map Button on Joystick with Default Controls
     * 
     * @param joystick
     * @param buttonNum
     */
    public ButtonToggle(Joystick joystick, int buttonNum){
        super(joystick, buttonNum, 0.5);

    }
    
    /**
     * Map Button on Joystick with a Period of a Debouce Period
     * 
     * @param joystick
     * @param buttonNum
     * @param period
     */
    public ButtonToggle(Joystick joystick, int buttonNum, double period){
        super(joystick, buttonNum, period);

    }

    /**
     * Return the State Value
     * State Changes when the User has Pushed the Button,
     *  Toggled the Button.
     * 
     * @return
     */
    @Override
    public boolean get(){
        if( super.get() == true){
            // Flip and Save State
            lastState = !lastState;
        }

        return lastState;
    }

}