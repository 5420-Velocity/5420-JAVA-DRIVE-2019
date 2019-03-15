package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.helpers.Limelight;

public class Limelight_turn extends Command {

    private boolean isFinished = false;
    private DifferentialDrive driveSystem;

    private double kp = -0.1f;
    private Limelight limelight;

    private double leftSpeed;
    private double rightSpeed;

    private double minCommand = 0.05;

    public Limelight_turn(DifferentialDrive drive, Limelight limelight){
        this(drive, limelight, 0.2);
    }

    public Limelight_turn(DifferentialDrive drive, Limelight limelight, double speed){

        this.driveSystem = drive;
        this.leftSpeed = speed;
        this.rightSpeed = -speed;
        this.limelight = limelight;
    }

    protected void initialize() {

    }

    protected void execute(){

        double headingError = this.limelight.getX();
        double steeringAdjust = 0;

        if (Math.signum(headingError) == 1.0){
            steeringAdjust = kp * headingError - minCommand;
        }

        else if (Math.signum(headingError) == 1.0){
            steeringAdjust = kp * headingError + minCommand;
        }

        leftSpeed += steeringAdjust;
        rightSpeed -= steeringAdjust;

        this.driveSystem.tankDrive(leftSpeed, rightSpeed);

    }

    public boolean isFinished(){
        return isFinished;
    }

    public void end(){

    }

}