package frc.robot.commands;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import frc.robot.helpers.VirtualGyro;
import frc.robot.helpers.console;
import frc.robot.helpers.console.logMode;

/**
 * AutoDriveGyro
 * This class will Drive the Robot using the Gyro
 * So only turning is controlled.
 * 
 * @author Noah Halstead <nhalstead00@gmail.com>
 */
public class AutoDriveGyro extends Command {
    protected Date EStopTime;
    protected long endTime;
    protected boolean isFinished;
    protected double power;
    protected double targetDeg;
    protected VirtualGyro gyro;
    protected int timeout;

    public DifferentialDrive drive;

    /**
     * AutoDriveGyro
     * Drive the Robot using the Gyro with the Given Differential Drive
     * 
     * @param drive     Drive Control
     * @param gyro      Gyro Instance
     * @param targetDeg Target Deg
     * @param power     Power Control Value (Used for the Turn Contorl)
     */
    public AutoDriveGyro(DifferentialDrive  drive, Gyro gyro, double targetDeg, double power) {
        this(drive, gyro, power, targetDeg, 4);
    }

    /**
     * AutoDriveGyro
     * Drive the Robot using the Gyro with the Given Differential Drive
     * 
     * @param drive     Drive Control
     * @param gyro      Gyro Instance
     * @param targetDeg Target Deg
     * @param power     Power Control Value (Used for the Turn Contorl)
     * @param timeout   Total time to spend on the job, this is a safety part to
     *    protect the robot from driving without an encoder
     */
    public AutoDriveGyro(DifferentialDrive  drive, Gyro gyro, double targetDeg, double power, int timeout) {
        this.drive = drive;
        this.gyro = new VirtualGyro(gyro);
        this.power = power;
        this.targetDeg = targetDeg;
        this.timeout = timeout;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
        console.out(logMode.kDebug, "["+this.getClass().getSimpleName()+"] Running Gyro " + this.gyro.getAngle());
        
        this.drive.stopMotor(); // Stop Motors, Stops any Rouge Commands Before Execution
        this.gyro.reset(); // Reset Gyro to Zero.

        // Setup the Stop Motor by Time when the encoder does not update.
        Calendar calculateDate = GregorianCalendar.getInstance();
        calculateDate.add(GregorianCalendar.MILLISECOND, (int) this.timeout); // Time to Check the Encoder Distance is not Zero
        EStopTime = calculateDate.getTime();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        // Check to see if its past the EStopTime
        if(new Date().after(EStopTime)) {
            // Detect if it has turned past 1/4 of the target.
            if(this.gyro.getAngle() < (this.targetDeg / 4)) {
                console.out(logMode.kDebug, "["+this.getClass().getSimpleName()+"] Command Timed Out!");
                this.isFinished = true;
                return;
            }
        }

        double current = this.gyro.getAngle();
        if(Math.abs(current) > this.targetDeg) {
            if(Math.signum(current) == -1) {
                this.drive.arcadeDrive(power, 0);
            }
            else if(Math.signum(current) == 1) {
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
