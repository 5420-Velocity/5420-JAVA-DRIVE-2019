package frc.robot.helpers;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

/**
 * ButtonDebouncer
 * This will make it so the button is only registered as pressed
 *  once, making it much easier to control your inputs. Debouncers
 *  are also useful when you want a surefire way of only sending 
 *  one pulse (instead having to press and release really quickly).
 * 
 * This was updated from the following link (section 4.2) to work 
 *  with an Up-to-date Class.
 * This class also does not Lock any Buttons, It uses getRawButton!
 * 
 * @author Tim Winters
 * @author Noah Haltead <nhalstead00@gmail.com>
 * @link https://media.readthedocs.org/pdf/frc-pdr/latest/frc-pdr.pdf
 */

public class ButtonDebouncer {

    private Joystick joystick;
    private int buttonNum;
    private double latest;
    private double debouncePeriod;

    /**
     * Map Button on Joystick with Default Controls
     * 
     * @param joystick
     * @param buttonNum
     */
    public ButtonDebouncer(Joystick joystick, int buttonNum){
        // Call the Other method for the Same Actions but with a default of 0.5
        this(joystick, buttonNum, 0.5);
    }
    
    /**
     * Map Button on Joystick with a Period of a Debouce Period
     * 
     * @param joystick
     * @param buttonNum
     * @param period
     */
    public ButtonDebouncer(Joystick joystick, int buttonNum, double period){
        this.joystick = joystick;
        this.buttonNum = buttonNum;
        this.latest = 0;
        this.debouncePeriod = period;
    }

    /**
     * Define a Debouce Period
     * 
     * @param period
     */
    public void setDebouncePeriod(double period){
        this.debouncePeriod = period;
    }

    /**
     * The Value of the Button Selected, If its past
     *  the debouce period.
     * 
     * @return
     */
    public boolean get(){
        double now = Timer.getFPGATimestamp();

        // Only Update and Return a Press Event, Ignoring a Debouce, 
        //  when the button is pressed.
        if(joystick.getRawButton(buttonNum) == true){
            if((now-latest) > debouncePeriod){
                latest = now;
                return true;
            }
        }
        return false;
    }

}
    