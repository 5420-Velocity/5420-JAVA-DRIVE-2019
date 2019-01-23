package frc.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;

public class SolenoidAuto extends Command {

	boolean isDone = false;
	DoubleSolenoid sol;
	DoubleSolenoid.Value newState;
	
	/**
	 * Action INIT
	 * 
	 * @param solIn      Double Solenoid Device
	 * @param newState   DoubleSolenoid.Value State to Set.
	 */
	public SolenoidAuto (DoubleSolenoid solIn, DoubleSolenoid.Value newState){
		this.sol = solIn;
		this.newState = newState;
	}	
	
	@Override
	public void initialize(){
		
	}
	
	@Override
	protected void execute() {
		this.sol.set(newState);
		this.isDone = true;
	}
	
	@Override
	protected boolean isFinished() {
		return this.isDone;
	}

}