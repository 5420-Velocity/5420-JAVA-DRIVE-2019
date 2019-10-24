package frc.robot.subsystem;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.helpers.Limelight;

public class LimelightConfigure extends InstantCommand {

    private Limelight.ledMode ledMode;
    private int pipeline;

    /**
     * Configure the Limelight
     * 
     * @param led_mode Set the Led Mode
     * @param pipeline Set the Pipeline Mode
     */
    public LimelightConfigure(Limelight.ledMode led_mode, int pipeline) {

        this.ledMode = led_mode;
        this.pipeline = pipeline;
    }

    protected void execute() {

        Limelight.getInstance().setLed(ledMode);
        Limelight.getInstance().setPipeline(pipeline);
    }

}