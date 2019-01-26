package frc.robot.commands;

import frc.robot.helpers.*;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class Limelight_SetPipeline extends InstantCommand {

    /**
     * Set the Pipeline, Run Commnad Once and Quit, Fast and Easy.
     * 
     * @param pipeline
     */
    public Limelight_SetPipeline(int pipeline){
        Limelight.getInstance().setPipeline(pipeline);
    }

}