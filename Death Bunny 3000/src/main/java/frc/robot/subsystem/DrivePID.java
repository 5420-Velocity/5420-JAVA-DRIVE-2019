package frc.robot.subsystem;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;


public class DrivePID extends Subsystem { // This system extends PIDSubsystem

    DifferentialDrive motor;
	Encoder encoder;
	PIDController pid;

	public DrivePID() {
        super("DrivePID");
		
		this.pid = new PIDController(4, 0, 0,
			new PIDSource() {
				public double pidGet() {
					return encoder.getDistance();
                }
                
                public void setPIDSourceType(PIDSourceType t) {
					
                }
                
                public PIDSourceType getPIDSourceType() {
					return null;
				}
			},
			new PIDOutput() {
				public void pidWrite(double d) {
					motor.arcadeDrive(0, .2);
					SmartDashboard.putString("drive status", "in pidloop for driving");
				}
			}
		);
	}
	
    public void initDefaultCommand() {
    }

    protected double returnPIDInput() {
        //return encoder.getAverageVoltage(); // returns the sensor value that is providing the feedback for the system
        return 0.0;
    }

    protected void usePIDOutput(double output) {
    	//motor.pidWrite(output); // this is where the computed output value fromthe PIDController is applied to the motor
    }
}