package frc.robot.helpers.controllers;

import edu.wpi.first.wpilibj.Joystick;

/**
 * DPad
 * Control to get the DPad Value while it is Pressed
 * 
 * While Pressed
 * 
 * @author Noah Halstead <nhalstead00@gmail.com>
 */
public class DPadButton {

    private DPad dInstance;
    private Direction dir;

    public enum Direction {
        Up,
        Down,
        Left,
        Right;
    }

    public DPadButton(Joystick joy, Direction dir){
        this.dInstance = new DPad(joy);
        this.dir = dir;
    }

    /**
     * Returns if the Button is Matches the Direction on input
     * 
     * @return If the Button is Pushed
     */
    public boolean get(){

        if(this.dir == Direction.Up){
            return this.dInstance.up();
        }
        else if(this.dir == Direction.Down){
            return this.dInstance.down();
        }
        else if(this.dir == Direction.Left){
            return this.dInstance.left();
        }
        else if(this.dir == Direction.Right){
            return this.dInstance.right();
        }

        System.out.println("Made an oof! DPadButton.java");
        return false;
    }

}