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
public class JoystickObject {

    private Joystick controller;
    private Integer axisX;
    private Integer axisY;
    private Integer axisZ;

    /**
     * Init Joystick Object
     * 
     * @param controller 
     * @param axisX 
     */
    public JoystickObject(Joystick controller, int axisX){
        this(controller, axisX, -1, -1);
    }

    /**
     * Init Joystick Object
     * 
     * @param controller 
     * @param axisX 
     * @param axisY 
     */
    public JoystickObject(Joystick controller, int axisX, int axisY){
        this(controller, axisX, axisY, -1);
    }

    /**
     * Init Joystick Object
     * 
     * @param controller 
     * @param axisX 
     * @param axisY 
     * @param axisZ 
     */
    public JoystickObject(Joystick controller, int axisX, int axisY, int axisZ){
        this.controller = controller;
        this.axisX = axisX;
        this.axisY = axisY;
        this.axisZ = axisZ;
    }

    /**
     * Registered Joystick ID
     * 
     * @return Int List [axisX, axisY, axisZ]
     */
    public int[] getId(){
        return new int[]{this.axisX, this.axisY, this.axisZ};
    }

    public double get() {
        return this.getX();
    }

    public double getX() {
        if(this.axisX == -1){
            return 0;
        }
        return this.controller.getRawAxis(this.axisX);
    }

    public double getY() {
        if(this.axisX == -1){
            return 0;
        }
        return this.controller.getRawAxis(this.axisY);
    }

    public double getZ() {
        if(this.axisX == -1){
            return 0;
        }
        return this.controller.getRawAxis(this.axisZ);
    }

}