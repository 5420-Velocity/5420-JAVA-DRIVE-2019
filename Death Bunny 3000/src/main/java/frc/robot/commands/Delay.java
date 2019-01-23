package frc.robot.commands;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import edu.wpi.first.wpilibj.command.Command;

public class Delay extends Command {

	private Date waitUntil;
	private boolean isFinished = false;
	private int milliSeconds;
	
	/**
	 * The total time to wait in milliSeconds.
	 * @param milliSeconds
	 */
	public Delay(int milliSeconds) {
		this.milliSeconds = milliSeconds;
	}
	
	/**
	 * The total time to wait in Seconds, Converted to milliSeconds.
	 * @param Seconds
	 */
	public Delay(double seconds) {
		this.milliSeconds = (int) (seconds*1000);
	}
	
	@Override
	protected void initialize() {
		Calendar calculateDate = GregorianCalendar.getInstance();
		calculateDate.add(GregorianCalendar.MILLISECOND, milliSeconds);
		waitUntil = calculateDate.getTime();
	}
	
	@Override
	protected void execute() {
		if(new Date().after(waitUntil)) {
			this.isFinished = true;
		}
	}
	
	@Override
	protected boolean isFinished() {
		return isFinished;
	}

	// Called once after isFinished returns true OR interrupted() is called.
    protected void end() {
        
    }

}
