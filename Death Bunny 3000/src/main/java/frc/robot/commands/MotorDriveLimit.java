package frc.robot.commands;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PWMSpeedController;
import frc.robot.Robot;

/**
 * MotorDrive
 * This class will Drive the Motors on the Robot, and can use a limit sensor
 * 
 * @author Asher Carney
 */
public class MotorDriveLimit extends MotorDrive {

    public DigitalInput upperLimit;
        
    public MotorDriveLimit(PWMSpeedController drive, double power, double timeInMillis, DigitalInput upperLimit, String Lockname){
        super(Robot.motorTilt, power, timeInMillis, Lockname);
        this.upperLimit = upperLimit;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        // if the lift is going up and it hit the limit, stop
        if(power > 0 && upperLimit.get()){
            power = 0;
            this.isFinished = true;
        }
        
        // Call the parent execute function
        super.execute();
    }
}