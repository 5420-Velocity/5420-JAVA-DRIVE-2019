package frc.robot.commands;

import frc.robot.helpers.*;

import edu.wpi.first.wpilibj.command.InstantCommand ;

public class Limelight_SetCameraMode extends InstantCommand {

    private Limelight limelight;
    private Limelight.camMode cMode;

    /**
     * Set the Pipeline, Run Command Once and Quit, Fast and Easy.
     * 
     * @param limelight
     */
    public Limelight_SetCameraMode(Limelight limelight){
        this(limelight, Limelight.camMode.kVision);
    }

    /**
     * Set the Pipeline, Run Command Once and Quit, Fast and Easy.
     * 
     * @param limelight
     * @param cMode
     */
    public Limelight_SetCameraMode(Limelight limelight, Limelight.camMode cMode){
        this.limelight = limelight;
        this.cMode = cMode;
    }

    @Override
    public void initialize() {
        limelight.setMode(cMode);
    }

}