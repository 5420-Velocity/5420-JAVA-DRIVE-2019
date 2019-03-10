package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.helpers.Limelight;

public class TurnToTarget extends InstantCommand {

    private double angleError;
    private double angleDividend = 0.1;
    private int pipeline = 0;
    private DifferentialDrive m_drive;
    private double turnSpeed;

    public TurnToTarget(DifferentialDrive drive, double turnSpeed) {
        this.m_drive = drive;
        this.turnSpeed = turnSpeed;
    }

    protected void initialize() {

        Limelight.getInstance().setLed(Limelight.ledMode.kOn);
        Limelight.getInstance().setMode(Limelight.camMode.kVision);
        Limelight.getInstance().setPipeline(this.pipeline);
    }

    protected void execute() {

        angleError = Limelight.getInstance().getX() - 8;
        SmartDashboard.putNumber("ANGLE ERROR", angleError);
        double turn = angleError * angleDividend;

        this.m_drive.arcadeDrive(0, turn);

    }

    protected void end(){

        Limelight.getInstance().setLed(Limelight.ledMode.kOff);

    }

}