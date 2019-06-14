package frc.robot.commands;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class AutoClimb extends Command{

    private DifferentialDrive Drive;
    private Solenoid Front, Back;
    private double Speed;
    private Encoder Left;
    private int driveToEdge = 200;

    public AutoClimb(DifferentialDrive Drive, Solenoid Front, Solenoid Back, double Speed, Encoder Left){
        this.Drive = Drive;
        this.Front = Front;
        this.Back = Back;
        this.Speed = Speed;
        this.Left = Left;
    }
    protected void initialize() {

    }

    protected void execute(){
    
        

    }

    protected boolean isFinished(){
        return false;
    }
}