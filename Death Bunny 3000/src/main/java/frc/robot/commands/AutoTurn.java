package frc.robot.commands;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;

import edu.wpi.first.wpilibj.GyroBase;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.helpers.console;
import frc.robot.helpers.console.logMode;

public class AutoTurn extends Command {

	private boolean isDone = false;
	private DifferentialDrive drive;
    private GyroBase Gyro;
	private double Turn = 0;
	private double degTarget;
	protected boolean isFinished;
	protected Date EStopTime;
	
	/**
	 * Set the Speed and Turn DEG for a Target
	 *  This will turn in place to the set DEG.
	 *  This is a Turn in place using Tank Drive.
	 * 
	 * @param drive   Differential Drive Instance
	 * @param gyro    Gyro Instance
	 * @param Speed	  X Value to Drive speed
	 * @param turnDEG Turn to the Set Deg Passed in.
	 */
	public AutoTurn(DifferentialDrive drive, GyroBase gyro, double Speed, int turnDEG){
        
        this.drive = drive;
        this.Gyro = gyro;
        
		if( turnDEG > 0 ){
        	// Number is +
			this.Turn = Math.abs(Speed);
        }
        else if( turnDEG < 0 ){
        	// Number is -
        	this.Turn = -( Math.abs(Speed) );
        }
		
		this.degTarget = turnDEG;

		// Setup the Stop Motor by Time when the encoder does not update.
        Calendar calculateDate = GregorianCalendar.getInstance();
		calculateDate.add(GregorianCalendar.MILLISECOND, (int) 5); // Time to Check the Encoder Distance is not Zero
        EStopTime = calculateDate.getTime();
	}	
	
	@Override
	public void initialize(){
		console.out(logMode.kDebug, "["+this.getClass().getSimpleName()+"] Running Gyro Turn to " + this.degTarget);
		this.drive.stopMotor(); // Stop Motors, Stops any Rouge Commands Before Execution
		this.Gyro.calibrate();
	}
	
	@Override
	protected void execute() {
		
		// Check to see if its past the EStopTime
        if( new Date().after(EStopTime) ) {
            // If the Encoder is not Past 5 Degs.
            if(this.Gyro.getAngle() < 5){
                console.out(logMode.kDebug, "["+this.getClass().getSimpleName()+"] Command Timed Out!!!");
                this.isFinished = true;
                return;
            }
        }

		// Do the Drive Operation.
		if(Math.abs(Gyro.getAngle()) >= Math.abs(this.degTarget) ){ 
			console.out(logMode.kDebug, "["+this.getClass().getSimpleName()+"] Completed Task");
			this.drive.arcadeDrive(0, 0);
			this.isFinished = true;
		}
		else {
			this.drive.tankDrive(-this.Turn , this.Turn);
		}
		this.drive.feedWatchdog();
	}
	
	@Override
	protected boolean isFinished() {
		return this.isDone;
	}

	// Called once after isFinished returns true OR interrupted() is called.
    protected void end() {
		this.drive.arcadeDrive(0, 0);
		this.drive.stopMotor();
		console.out(logMode.kDebug, "["+this.getClass().getSimpleName()+"] Finished Command");
    }

}
