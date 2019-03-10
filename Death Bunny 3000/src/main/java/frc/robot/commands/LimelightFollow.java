package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.helpers.Limelight;

public class LimelightFollow extends Command {

    private double turnSensitivty = 50;
    private int cruiseVel = 400;
    private int pipeline = 0;
    private double angleError;

    public LimelightFollow(double timeout) {
        this(timeout, 0);
    }

    public LimelightFollow(double timeout, int pipeline){
        super(timeout);
        this.pipeline = pipeline;
    }

    protected void initialize() {

        Limelight.getInstance().setLed(Limelight.ledMode.kOn);
        Limelight.getInstance().setMode(Limelight.camMode.kVision);
        Limelight.getInstance().setPipeline(this.pipeline);

    }

    protected void execute() {
        angleError = Limelight.getInstance().getX() - 8;
        double turn = angleError * turnSensitivty;
        Robot.m_drive.arcadeDrive(cruiseVel + turn, cruiseVel - turn);
    }

    protected boolean isFinished() {
        return Robot.ballLoaded.get() || isTimedOut();
    }

}