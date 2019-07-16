package frc.robot.helpers;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * EdgeButton
 * This class allows for Edge Case Triggers Only for Joysticks.
 * So this will trigger on cases like Rising Event or Falling Events.
 * 
 * @author Noah Halstead <nhalstead00@gmail.com>
 */
public class ButtonObject {

    private Joystick controller;
    private Integer buttonId;
    private boolean buttonStatus;
    private boolean alreadyReturnedFalling = false;
    private boolean alreadyReturnedRising = false;

    public ButtonObject(Joystick controller, int buttonId){
        this.controller = controller;
        this.buttonId = buttonId;
        this.get(); // Load first status.
    }

    /**
     * Registered Button ID
     * 
     * @return Button ID Passed in on Init
     */
    public int getId(){
        return this.buttonId;
    }

    public boolean get() {
        boolean btnValue = this.controller.getRawButton(this.buttonId);
        this.buttonStatus = btnValue;
        return btnValue;
    }

    public boolean getRise(){

        return false;
    }

    public boolean getWhile(){

        return false;
    }

    public boolean getFall(){
        
        return false;
    }

}