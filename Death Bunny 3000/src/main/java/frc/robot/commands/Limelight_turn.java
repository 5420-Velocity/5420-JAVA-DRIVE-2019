package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.helpers.Limelight;
/**
 * Limelight_turn
 * 
 * This class will turn the robot to face a target using the limelight
 * 
 * @link http://docs.limelightvision.io/en/latest/cs_drive_to_goal_2019.html
 */

public class Limelight_turn extends Command {
    final double STEER_K = 0.03; // how hard to turn toward the target

    private boolean isFinished = false;
    private DifferentialDrive driveSystem;

    private Limelight limelight;

    private double turnSpeed;

    public Limelight_turn(DifferentialDrive drive, Limelight limelight){
        this(drive, limelight, 0.2);
    }

    public Limelight_turn(DifferentialDrive drive, Limelight limelight, double turnSpeed){

        this.driveSystem = drive;
        this.turnSpeed = turnSpeed;
        this.limelight = limelight;
    }

    protected void initialize() {

    }

    protected void execute(){

         // Start with proportional steering
         double steer_cmd = this.limelight.getX() * STEER_K;
         turnSpeed = steer_cmd;
        
        //only drive when there is a valid target
        if(this.limelight.hasTarget() == true) {
            this.driveSystem.arcadeDrive(0, turnSpeed);
        }
        else{
            this.driveSystem.arcadeDrive(0, 0);
            this.isFinished = true;
        }
    }

    public boolean isFinished(){
        return isFinished;
    }

    public void end(){

    }

}