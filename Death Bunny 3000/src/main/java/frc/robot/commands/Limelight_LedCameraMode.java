package frc.robot.commands;

import frc.robot.helpers.*;

import edu.wpi.first.wpilibj.command.InstantCommand ;

public class Limelight_LedCameraMode extends InstantCommand {

    /**
     * Set the Camera Mode, Run Commnad Once and Quit, Fast and Easy.
     * 
     * @param camMode
     */
    public Limelight_LedCameraMode(Limelight.ledMode mode){
        
        Limelight.getInstance().setLed(mode);
    }

}