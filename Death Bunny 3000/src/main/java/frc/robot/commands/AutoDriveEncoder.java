package frc.robot.commands;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.helpers.console;
import frc.robot.helpers.console.logMode;

/**
 * AutoDrive
 * This class will Drive the Robot
 * 
 * @author Noah Halstead <nhalstead00@gmail.com>
 */
public class AutoDriveEncoder extends Command {
    protected double power;
    protected int distance;
    protected long endTime;
    protected boolean isFinished;
    protected Encoder enc;
    protected Date EStopTime;
    protected int time;

    public DifferentialDrive drive;

    /**
     * AutoDriveEncoder
     * Drive the Robot using the Encoder with the Given Differential Drive
     * 
     * @param drive Drive Control
     * @param enc Encoder to Follow
     * @param power Power Control Value
     * @param ticks Total Distance to go on the Encoder
     */
    public AutoDriveEncoder(DifferentialDrive  drive, Encoder enc, double power, int ticks) {
        this(drive, enc, power, ticks, 4);
    }

    /**
     * AutoDriveEncoder
     * Drive the Robot using the Encoder with the Given Differential Drive
     * 
     * @param drive Drive Control
     * @param enc Encoder to Follow
     * @param power Power Control Value
     * @param ticks Total Distance to go on the Encoder
     * @param time Total time to spend on the job, this is a safety part to
     *    protect the robot from driving without an encoder
     */
    public AutoDriveEncoder(DifferentialDrive  drive, Encoder enc, double power, int ticks, int time) {
        this.drive = drive;
        this.enc = enc;
        this.power = power;
        this.distance = Math.abs(ticks);
        this.time = time;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
        console.out(logMode.kDebug, "["+this.getClass().getSimpleName()+"] Running Encoder Distance " + this.distance);
        
        this.drive.stopMotor(); // Stop Motors, Stops any Rouge Commands Before Execution
        this.enc.reset();

        // Setup the Stop Motor by Time when the encoder does not update.
        Calendar calculateDate = GregorianCalendar.getInstance();
        calculateDate.add(GregorianCalendar.MILLISECOND, (int) this.time); // Time to Check the Encoder Distance is not Zero
        EStopTime = calculateDate.getTime();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        // Check to see if its past the EStopTime
        if( new Date().after(EStopTime) ) {
            // If the Encoder is not Past 10 ticks.
            if(this.enc.get() < 10){
                console.out(logMode.kDebug, "["+this.getClass().getSimpleName()+"] Command Timed Out! Encoder did not move past 10 ticks.");
                this.isFinished = true;
                return;
            }
        }

		/*if( Math.abs(this.enc.get()) > this.distance ) {
			console.out(logMode.kDebug, "["+this.getClass().getSimpleName()+"] Completed Task");
			this.isFinished = true;
        }
        else {
            this.drive.arcadeDrive(power, 0);
        }*/

        double t = this.distance - this.enc.get();
    		
        if(Math.abs(t) > this.distance) {
            if(Math.signum(t) == -1) {
                this.drive.arcadeDrive(power, 0);
            }
            else if(Math.signum(t) == 1) {
                this.drive.arcadeDrive(power, 0);
            }
        }
        else {
            console.out(logMode.kDebug, "["+this.getClass().getSimpleName()+"] Completed Task");
            this.isFinished = true;
        }

        this.drive.feedWatchdog(); // Feed the Dog
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        //return System.currentTimeInMillis() >= endTime;
        return isFinished;
    }

    // Called once after isFinished returns true OR interrupted() is called.
    protected void end() {
        this.drive.arcadeDrive(0, 0);
        this.drive.stopMotor();
        console.out(logMode.kDebug, "["+this.getClass().getSimpleName()+"] Finished Command");
    }
}
