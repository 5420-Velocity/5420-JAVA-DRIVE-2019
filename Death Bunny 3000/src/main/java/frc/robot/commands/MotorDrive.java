package frc.robot.commands;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PWMSpeedController;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.helpers.Locker;
import frc.robot.helpers.console;
import frc.robot.helpers.console.logMode;

/**
 * MotorDrive
 * This class will Drive the Motors on the Robot
 * 
 * @author Noah Halstead <nhalstead00@gmail.com>
 */
public class MotorDrive extends Command {
    protected double power;
    protected double turn;
    protected double time;
    protected long endTime;
    protected boolean isFinished;
    protected Date EStopEncoderTime;
    public PWMSpeedController drive;
    private String lockName = "";

    public MotorDrive(PWMSpeedController drive, double power, double timeInMillis, String lockName) {
        this.power = power;
        this.time = timeInMillis;
        this.drive = drive;
        this.lockName = lockName;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        console.out(logMode.kDebug, "["+this.getClass().getSimpleName()+"] ["+drive.getChannel()+"] Running Timeout" + this.time);

        // Setup the Stop Motor by Time.
		Calendar calculateDate = GregorianCalendar.getInstance();
		calculateDate.add(GregorianCalendar.MILLISECOND, (int) this.time); // Time to Check the Encoder Distance is not Zero
        EStopEncoderTime = calculateDate.getTime();

        Locker.lock(this.lockName);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        // Safety Code, Its made to catch the Human Error of not plugging in the Encoder.
		//  Encoders will send a 0 value if you don't have an encoder plugged-in to the port.
		// Do the Safe Check to see if the Encoders are doing their thing or not after x seconds
		if( new Date().after(EStopEncoderTime) ) {
			// If the Encoder is not Past 10 ticks.
			console.out(logMode.kDebug, "["+this.getClass().getSimpleName()+"] ["+drive.getChannel()+"] Finished");
			this.isFinished = true;
        }
        else {
            this.drive.set(power);
        }
        this.drive.feed();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        //return System.currentTimeInMillis() >= endTime;
        return isFinished;
    }

    // Called once after isFinished returns true OR interrupted() is called.
    protected void end() {
        this.drive.set(0);
        this.drive.stopMotor();
        console.out(logMode.kDebug, "["+this.getClass().getSimpleName()+"] ["+drive.getChannel()+"] Command Ended");
        Locker.unlock(this.lockName);
    }
}
