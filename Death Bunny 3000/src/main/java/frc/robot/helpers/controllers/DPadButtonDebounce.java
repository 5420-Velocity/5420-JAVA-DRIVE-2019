package frc.robot.helpers.controllers;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

/**
 * DPadButtonDebounce
 * Allows you to use the DPadButton with a Debounce add in to
 *  help make the button better for single actions.
 * 
 * On Press
 * 
 * @author Noah Halstead <nhalstead00@gmail.com>
 */
public class DPadButtonDebounce extends DPadButton {

    private double debouncePeriod;
    private double latest;

    public DPadButtonDebounce(Joystick joy, Direction dir){
        this(joy, dir, 0.8);
    }

    public DPadButtonDebounce(Joystick joy, Direction dir, double debounceTime){
        super(joy, dir);
        this.debouncePeriod = debounceTime;
    }

    /**
     * Returns if the Button is Matches the Direction on input
     * 
     * @return If the Button is Pushed with Debounce added into the mix.
     */
    public boolean get(){

        double now = Timer.getFPGATimestamp();

        // Only Update and Return a Press Event, Ignoring a Debounce,
        //  when the button is pressed.
        if(super.get() == true){
            if((now-latest) > this.debouncePeriod){
                latest = now;
                return true;
            }
        }
        return false;
    }

}