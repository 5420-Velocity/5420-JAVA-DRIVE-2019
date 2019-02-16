package frc.robot.commands;

import frc.robot.helpers.Limelight;
import edu.wpi.first.wpilibj.command.Command;

public class Limelight_Blink extends Command {

    public Limelight_Blink(int time) {
        super(time);
    }

    protected void execute() {
        Limelight.getInstance().setLed(Limelight.ledMode.kOn);
    }

    protected boolean isFinished() {
        return isTimedOut();
    }

    protected void end() {
        Limelight.getInstance().setLed(Limelight.ledMode.kOff);
    }

}