package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Robot;
import frc.robot.helpers.Limelight;
import frc.robot.helpers.console;

public class LimelightFollow extends Command {

    private double turnSensitivty = 50;
    private int cruiseVel = 400;
    private int pipeline;
    private DifferentialDrive drive;
    private double angleError;
    private Limelight limelight;

    /**
     * 
     * 
     * @param drive Drive to Control
     * @param limelight Limelight Object to read data from.
     * @param pipeline Limelight Contorl to Find
     */
    public LimelightFollow(DifferentialDrive drive, Limelight limelight, int pipeline) {
        this(drive, limelight, 5, pipeline);
    }

    /**
     * 
     * 
     * @param drive Drive to Control
     * @param limelight Limelight Object to read data from.
     * @param pipeline Limelight Contorl to Find
     * @param timeout The total time to allocate before it fails the execution.
     */
    public LimelightFollow(DifferentialDrive drive, Limelight limelight, int pipeline, int timeout){
        super(timeout);
        this.pipeline = pipeline;
        this.drive = drive;
    }

    protected void initialize() {
        console.out(console.logMode.kDebug, this.getName() + ": Class has init the Execution.");
        limelight.setLed(Limelight.ledMode.kOn);
        limelight.setMode(Limelight.camMode.kVision);
        limelight.setPipeline(this.pipeline);

    }

    protected void execute() {
        angleError = limelight.getX() - 8;
        double turn = angleError * turnSensitivty;
        this.drive.arcadeDrive(cruiseVel + turn, cruiseVel - turn);
    }

    protected boolean isFinished() {
        return Robot.ballLoaded.get() || isTimedOut();
    }

    protected void end(){
        this.drive.stopMotor();
        limelight.setLed(Limelight.ledMode.kOff);
        console.out(console.logMode.kDebug, this.getName() + ": Class has finished Execution.");
    }

}