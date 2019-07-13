package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;

/**
 * AutoDrive
 * This class will Drive the Robot
 * 
 * When added to the Scheduler this should be a parallel task
 *  so it does not stop processing other actions.
 * 
 * @author Noah Halstead <nhalstead00@gmail.com>
 */
public class ControllerRumble extends Command {
    protected boolean isDone = false;
    protected double vibrationIntensity;
    protected Joystick joy;
    protected int length = 500; // Time in milliseconds
    protected Date endTime;
    protected RumbleType rum;
    
    /**
     * Controller Rumble
     * Force the Controller to Rumble at a Set Intensity for a set time.
     * 
     * @param joy
     * @param vibrationIntensity
     * @param milliseconds
     */
    public ControllerRumble(Joystick joy, double vibrationIntensity, int milliseconds){
        this(joy, RumbleType.kLeftRumble, vibrationIntensity, milliseconds);
    }

    /**
     * Controller Rumble
     * Force the Controller to Rumble at a Set Intensity for a set time
     *  with the Choice of the Left or Right Side of the Controller.
     * 
     * @param joy
     * @param rum
     * @param vibrationIntensity
     * @param milliseconds
     */
    public ControllerRumble(Joystick joy, RumbleType rum, double vibrationIntensity, int milliseconds) {
        this.joy = joy;
        this.vibrationIntensity = vibrationIntensity;
        this.length = milliseconds;
        this.rum = rum;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        // Setup the Stop Motor by Time.
		Calendar calculateDate = GregorianCalendar.getInstance();
		calculateDate.add(GregorianCalendar.MILLISECOND, (int) this.length); // Time to Check the Encoder Distance is not Zero
        this.endTime = calculateDate.getTime();
        
        this.joy.setRumble(this.rum, 0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if( new Date().after(this.endTime) ) {
            // Time has Passed.
			this.isDone = true;
        }
        else {
            // Time has not past the Set Date.
            this.joy.setRumble(this.rum, this.vibrationIntensity);
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        //return System.currentTimeInMillis() >= endTime;
        return this.isDone;
    }

    // Called once after isFinished returns true OR interrupted() is called.
    protected void end() {
        this.joy.setRumble(this.rum, 0);
    }
}
