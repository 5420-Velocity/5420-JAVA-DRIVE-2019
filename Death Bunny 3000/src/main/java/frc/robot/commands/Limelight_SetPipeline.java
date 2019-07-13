package frc.robot.commands;

import frc.robot.helpers.*;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class Limelight_SetPipeline extends InstantCommand {

    private Limelight limelight;
    private int pipeline;

    /**
     * Set the Pipeline, Run Command Once and Quit, Fast and Easy.
     * 
     * @param limelight
     */
    public Limelight_SetPipeline(Limelight limelight){
        this(limelight, 0);
    }

    /**
     * Set the Pipeline, Run Command Once and Quit, Fast and Easy.
     * 
     * @param pipeline
     */
    public Limelight_SetPipeline(Limelight limelight, int pipeline){
        this.limelight = limelight;
        this.pipeline = pipeline;
    }

    @Override
    public void initialize() {
        limelight.setPipeline(pipeline);
    }

}