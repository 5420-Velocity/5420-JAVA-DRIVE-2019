package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 * ToggleHatchGrabState
 * This class will flip the value of the Hatch when the Push the Button.
 * This is not to be used in the CommandGroups. Only Used for the Button
 *  event.
 * 
 * @author Noah Halstead <nhalstead00@gmail.com>
 */
public class ToggleHatchGrabState extends Command {
    protected double power;
    protected double time;
    protected long endTime;

    public ToggleHatchGrabState() {
        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        // long startTime = System.currentTimeMillis();
        // endTime = startTime + this.time;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        //return System.currentTimeInMillis() >= endTime;
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        // Robot.driveTrain.setPower(0, 0);

    }

    protected void interrupted() {
        end();
    }
}
