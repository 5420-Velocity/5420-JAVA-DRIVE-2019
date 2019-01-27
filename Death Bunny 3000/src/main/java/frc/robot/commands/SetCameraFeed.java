package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.command.InstantCommand;

public class SetCameraFeed extends InstantCommand {

    /**
     * Set the Value in the Dashboard for the Camera Number
     * 
     * @param entry
     * @param cameraPortNumber
     */
    public SetCameraFeed(NetworkTableEntry entry, int cameraPortNumber){
        entry.setNumber(cameraPortNumber);
    }

}