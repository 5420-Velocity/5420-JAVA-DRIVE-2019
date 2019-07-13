package frc.robot.commands;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import edu.wpi.first.wpilibj.PWMSpeedController;
import edu.wpi.first.wpilibj.command.Command;

public class TimedMotor extends Command {

	boolean isDone = false;
	double speed;
	int targetTime;
	private PWMSpeedController Motor = null;
	private Date EStopEncoderTime;
	
	/**
	 * Drive Motor for 1 Second
	 * 
	 * @param motor       Motor to Control
	 * @param Speed       Speed used to drive the motor
	 */
	public TimedMotor (PWMSpeedController motor, double Speed){
		this(motor, Speed, 1);
	}

	/**
	 * Action INIT
	 * 
	 * @param motor       Motor to Control
	 * @param Speed       Speed used to drive the motor
	 * @param targetTimeIn  The Time to wait before turning off the motors.
	 */
	public TimedMotor (PWMSpeedController motor, double Speed, int targetTimeIn){
		this.targetTime = Math.abs(targetTimeIn); // Get ABS of the Number.
		this.speed = Speed;
		this.Motor = motor;
	}	
	
	@Override
	public void initialize(){
		// Setup the Stop Motor by Time.
		Calendar calculateDate = GregorianCalendar.getInstance();
		calculateDate.add(GregorianCalendar.SECOND, (int) this.targetTime); // Time to Check the Encoder Distance is not Zero
		EStopEncoderTime = calculateDate.getTime();
	}
	
	@Override
	protected void execute() {
		
		if( new Date().after(EStopEncoderTime) ) {
			this.Motor.setSpeed(0);
			this.isDone = true;
		}
		else {
			// Set Motor Speed
			this.Motor.set( this.speed );
		}
		
	}
	
	@Override
	protected boolean isFinished() {
		return this.isDone;
	}

	@Override
	protected void end() {
		this.Motor.stopMotor();
	}

}
