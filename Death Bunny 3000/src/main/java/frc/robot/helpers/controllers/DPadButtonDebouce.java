package frc.robot.helpers.controllers;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

/**
 * DPadButtonDebouce
 * Allows you to use the DPadButton with a Debouce add in to
 *  help make the button better for single actions.
 * 
 * On Press
 * 
 * @author Noah Halstead <nhalstead00@gmail.com>
 */
public class DPadButtonDebouce extends DPadButton {

    private double deboucePeriod;
    private double latest;

    public DPadButtonDebouce(Joystick joy, Direction dir){
        this(joy, dir, 0.8);
    }

    public DPadButtonDebouce(Joystick joy, Direction dir, double debouceTime){
        super(joy, dir);
        this.deboucePeriod = debouceTime;
    }

    /**
     * Returns if the Button is Matches the Direction on input
     * 
     * @return If the Button is Pushed with Debounce added into the mix.
     */
    public boolean get(){

        double now = Timer.getFPGATimestamp();

        // Only Update and Return a Press Event, Ignoring a Debouce, 
        //  when the button is pressed.
        if(super.get() == true){
            if((now-latest) > this.deboucePeriod){
                latest = now;
                return true;
            }
        }
        return false;
    }

}