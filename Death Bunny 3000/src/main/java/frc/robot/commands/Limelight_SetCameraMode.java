package frc.robot.commands;

import frc.robot.helpers.*;

import edu.wpi.first.wpilibj.command.InstantCommand ;

public class Limelight_SetCameraMode extends InstantCommand {

    /**
     * Set the Camera Mode, Run Commnad Once and Quit, Fast and Easy.
     * 
     * @param camMode
     */
    public Limelight_SetCameraMode(Limelight.camMode mode){
        
        Limelight.getInstance().setMode(mode);
    }

}