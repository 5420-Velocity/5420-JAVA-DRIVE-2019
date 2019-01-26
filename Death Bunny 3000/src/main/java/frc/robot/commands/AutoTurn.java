package frc.robot.commands;

import edu.wpi.first.wpilibj.GyroBase;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class AutoTurn extends Command {

	private boolean isDone = false;
	private DifferentialDrive drive;
    private GyroBase Gyro;
	private double Turn = 0;
	private double degTarget;
	
	/**
	 * Set the Speed and Turn DEG for a Target
	 * This will turn in place to the set DEG.
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
	}	
	
	@Override
	public void initialize(){
        this.drive.stopMotor(); // Stop Motors, Stops any Rouge Commands Before Execution
	}
	
	@Override
	protected void execute() {
		
		// Do the Drive Operation.
		if(Math.abs(Gyro.getAngle()) <= Math.abs(this.degTarget) ){ 
			this.drive.tankDrive(-this.Turn , this.Turn);
		}
		else {
			this.drive.arcadeDrive(0, 0);
			this.isDone = true;
		}
		
	}
	
	@Override
	protected boolean isFinished() {
		return this.isDone;
	}

	// Called once after isFinished returns true OR interrupted() is called.
    protected void end() {
        this.drive.arcadeDrive(0, 0);
    }

}
