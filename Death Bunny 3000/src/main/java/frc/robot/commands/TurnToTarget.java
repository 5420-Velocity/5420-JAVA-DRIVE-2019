package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.helpers.Limelight;

public class TurnToTarget extends InstantCommand {

    private double angleDividend = 0.1;
    private int pipeline = 0;
    private DifferentialDrive m_drive;
    private double turnSpeed;
    private Limelight limelight;

    public TurnToTarget(DifferentialDrive drive, Limelight limelight, double turnSpeed) {
        this.m_drive = drive;
        this.turnSpeed = turnSpeed;
        this.limelight = limelight;
    }

    protected void initialize() {

        limelight.setLed(Limelight.ledMode.kOn);
        limelight.setMode(Limelight.camMode.kVision);
        limelight.setPipeline(this.pipeline);
    }

    protected void execute() {

        double angleError = limelight.getX() - 8;
        SmartDashboard.putNumber("ANGLE ERROR", angleError);
        double turn = angleError * angleDividend;

        this.m_drive.arcadeDrive(0, turn);

    }

    protected void end(){

        limelight.setLed(Limelight.ledMode.kOff);

    }

}