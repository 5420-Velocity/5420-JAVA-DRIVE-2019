package frc.robot.commands;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.helpers.console;
import frc.robot.helpers.console.logMode;

// TODO: Add Safety Code based on time to Stop Robot if no Encoder is Present
/**
 * AutoDrive
 * This class will Drive the Robot
 * 
 * @author Noah Halstead <nhalstead00@gmail.com>
 */
public class AutoDriveEncoder extends Command {
    protected double power;
    protected double turn;
    protected int distance;
    protected long endTime;
    protected boolean isFinished;
    protected Encoder enc;

    public DifferentialDrive drive;

    public AutoDriveEncoder(DifferentialDrive  drive, Encoder enc, double power, double turn, int ticks) {
        this.drive = drive;
        this.enc = enc;
        this.power = power;
        this.turn = turn;
        this.distance = ticks;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        console.out(logMode.kDebug, "[AutoDriveEncoder] Running Encoder Distance" + this.distance);

        this.drive.stopMotor(); // Stop Motors, Stops any Rouge Commands Before Execution
        this.enc.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        // Safety Code, Its made to catch the Human Error of not plugging in the Encoder.
		//  Encoders will send a 0 value if you don't have an encoder plugged-in to the port.
		// Do the Safe Check to see if the Encoders are doing their thing or not after x seconds
		if( this.enc.getRaw() > this.distance ) {
			// If the Encoder is not Past 10 ticks.
			console.out(logMode.kDebug, "[AutoDriveEncoder] Finished");
			this.isFinished = true;
        }
        else {
            this.drive.arcadeDrive(power, turn);
        }
        this.drive.feedWatchdog();
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
    }
}
