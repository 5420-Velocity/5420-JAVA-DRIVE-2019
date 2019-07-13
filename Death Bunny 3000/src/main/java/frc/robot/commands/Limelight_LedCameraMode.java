package frc.robot.commands;

import frc.robot.helpers.*;

import edu.wpi.first.wpilibj.command.InstantCommand ;

public class Limelight_LedCameraMode extends InstantCommand {

    /**
     * Set the Camera Mode, Run Command Once and Quit, Fast and Easy.
     * 
     * @param mode
     */
    public Limelight_LedCameraMode(Limelight.ledMode mode){
        
        Limelight.getInstance().setLed(mode);
    }

    private Limelight limelight;
    private Limelight.ledMode cMode;

    /**
     * Set the Pipeline, Run Command Once and Quit, Fast and Easy.
     * 
     * @param limelight
     */
    public Limelight_LedCameraMode(Limelight limelight){
        this(limelight, Limelight.ledMode.kOn);
    }

    /**
     * Set the Pipeline, Run Command Once and Quit, Fast and Easy.
     * 
     * @param limelight
     * @param cMode
     */
    public Limelight_LedCameraMode(Limelight limelight, Limelight.ledMode cMode){
        this.limelight = limelight;
        this.cMode = cMode;
    }

    @Override
    public void initialize() {
        limelight.setLed(cMode);
    }

}