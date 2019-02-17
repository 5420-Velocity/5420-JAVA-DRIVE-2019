package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.helpers.Limelight;

public class Limelight_turn extends Command {

    private boolean isFinished = false;
    private DifferentialDrive driveSystem;

    private double kp = -0.1f;
    private double tx = Limelight.getInstance().getX();

    private double leftSpeed;
    private double rightSpeed;

    private double minCommand = 0.05;

    public Limelight_turn(DifferentialDrive drive){
        this(drive, 0);

    }

    public Limelight_turn(DifferentialDrive drive, double speed){

        this.driveSystem = drive;
        this.leftSpeed = speed;
        this.rightSpeed = -speed;
    }

    protected void initialize() {

    }

    protected void execute(){

        double headingError = tx;
        double steeringAdjust = 0;

        if (tx > 1.0){
            steeringAdjust = kp * headingError - minCommand;
        }

        else if (tx < 1.0){
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