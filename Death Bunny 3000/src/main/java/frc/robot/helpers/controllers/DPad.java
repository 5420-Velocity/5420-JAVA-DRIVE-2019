package frc.robot.helpers.controllers;

import edu.wpi.first.wpilibj.Joystick;

/**
 * DPad
 * 
 * @author Noah Halstead <nhalstead00@gmail.com>
 * @author Team 1736 Robot Casserole
 * @link https://github.com/RobotCasserole1736/CasseroleLib/blob/master/java/src/org/usfirst/frc/team1736/lib/HAL/Xbox360Controller.java
 */
public class DPad {

    // Controller D-Pad POV Hat
    final static int XBOX_DPAD_POV = 0;

    private int povIndex;
    private Joystick joy;

    public DPad(Joystick joy){
        this.joy = joy;
        this.povIndex = XBOX_DPAD_POV;
    }

    public DPad(Joystick joy, int povIndex){
        this.joy = joy;
        this.povIndex = povIndex;
    }

    /**
     * Get POV Index
     * 
     * @return POV Index Id
     */
    public int getId(){
        return povIndex;
    }

    /**
     * Get POV Value
     * 
     * @return POV Values with the give POV Index
     */
    public int get(){
        return joy.getPOV(povIndex);
    }

    /**
     * Is the DPad button Up
     * 
     * @return True if the DPad is pushed up, False if it is not pressed
     */
    public boolean up() {
        if ((this.get() >= 315 || this.get() <= 45) && this.get() != -1){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Is the DPad button Down
     * 
     * @return True if the DPad is pushed down, False if it is not pressed
     */
    public boolean down() {
        if (this.get() >= 135 && this.get() <= 225){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Is the DPad button on the Right
     * 
     * @return True if the DPad is pushed right, False if it is not pressed
     */
    public boolean right() {
        if (this.get() >= 45 && this.get() <= 135){
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * Is the DPad button on the Left
     * 
     * @return True if the DPad is pushed left, False if it is not pressed
     */
    public boolean left() {
        if (this.get() >= 225 && this.get() <= 315){
            return true;
        }
        else {
            return false;
        }
    }

}