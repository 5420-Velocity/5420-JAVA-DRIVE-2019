package frc.robot.commands;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PWMSpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import frc.robot.helpers.console;
import frc.robot.helpers.console.logMode;

/**
 * MotorDrive
 * This class will Drive the Motors on the Robot
 * 
 * @author AA
 */
public class MotorDriveEncoder extends MotorDrive {
    public DigitalInput upperLimit;
    public DigitalInput lowerLimit;

    
    public MotorDriveEncoder(PWMSpeedController drive, double power, double timeInMillis, DigitalInput upperLimit, DigitalInput lowerLimit){
        super( drive, power, timeInMillis, "");
        this.upperLimit = upperLimit;
        this.lowerLimit = lowerLimit;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        console.out(logMode.kDebug, "[MotorDrive] Running Timeout" + this.time);

        // Setup the Stop Motor by Time.
		Calendar calculateDate = GregorianCalendar.getInstance();
		calculateDate.add(GregorianCalendar.MILLISECOND, (int) this.time); // Time to Check the Encoder Distance is not Zero
        EStopEncoderTime = calculateDate.getTime();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        // Safety Code, Its made to catch the Human Error of not plugging in the Encoder.
		//  Encoders will send a 0 value if you don't have an encoder plugged-in to the port.
		// Do the Safe Check to see if the Encoders are doing their thing or not after x seconds
		if( new Date().after(EStopEncoderTime)) {
			// If the Encoder is not Past 10 ticks.
			console.out(logMode.kDebug, "[AutoDrive] Finished");
			this.isFinished = true;
        }
        else if(upperLimit.get() == false && lowerLimit.get() == false){
            this.drive.set(power);
        }
        else if(power < 0 && upperLimit.get() == true){
            this.drive.set(power);
        }
        else if(power  > 0 && lowerLimit.get() == true){
            this.drive.set(power);
        }
        else{
            this.isFinished = false;
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
    }
}
