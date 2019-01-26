package frc.robot.user.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * ToggleHatchGrabState
 * This class will flip the value of the Hatch when the Push the Button.
 * This is not to be used in the CommandGroups. Only Used for the Button
 *  event.
 * 
 * @author Noah Halstead <nhalstead00@gmail.com>
 */
public class ToggleHatchGrabState extends Command {
    private boolean isDone;
    private DoubleSolenoid.Value setOnly;

    public ToggleHatchGrabState() {
        
    }

    /**
     * Set only One Kind of state.
     * 
     * @param newState
     */
    public ToggleHatchGrabState(DoubleSolenoid.Value newState) {
        this.setOnly = newState;
    }

    // Called just before this Command runs the first time
    protected void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        if(this.setOnly != null){
            // If setOnly is defined then Only allow One Value to be Set.

            // This only allows the State to be Set to the Given Mode, Prevents it from Flipping, Only allows it be Set.
            if(Robot.hatchSol.get() == DoubleSolenoid.Value.kForward && this.setOnly == DoubleSolenoid.Value.kReverse){
                // Flip the State
                Robot.hatchSol.set(DoubleSolenoid.Value.kReverse);
            }
            else if(Robot.hatchSol.get() == DoubleSolenoid.Value.kReverse && this.setOnly == DoubleSolenoid.Value.kForward){
                // Flip the State
                Robot.hatchSol.set(DoubleSolenoid.Value.kForward);
            }

        }
        else {
            // Run if the setOnly (Set State Lock) was defined.

            if(Robot.hatchSol.get() == DoubleSolenoid.Value.kForward){
                // Flip the State
                Robot.hatchSol.set(DoubleSolenoid.Value.kReverse);
            }
            else if(Robot.hatchSol.get() == DoubleSolenoid.Value.kReverse){
                // Flip the State
                Robot.hatchSol.set(DoubleSolenoid.Value.kForward);
            }

        }
        
        this.isDone = true;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return this.isDone;
    }

    // Called once after isFinished returns true
    protected void end() {

    }
    
}
