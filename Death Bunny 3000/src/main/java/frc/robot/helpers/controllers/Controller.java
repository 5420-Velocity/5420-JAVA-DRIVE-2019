package frc.robot.helpers.controllers;

import java.util.HashMap;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.helpers.ButtonObject;
import frc.robot.helpers.JoystickObject;

/**
 * Controller
 * 
 * @author Noah Halstead <nhalstead00@gmail.com>
 */
public class Controller {

    private int port;
    private Joystick joy;
    private HashMap<String, ButtonObject> Buttons = new HashMap<String, ButtonObject>();
    private HashMap<String, JoystickObject> Joysticks = new HashMap<String, JoystickObject>();
    private HashMap<String, DPad> DPads = new HashMap<String, DPad>();

    public Controller(int port) {
        this.port = port;
        this.joy = new Joystick(port);
        this.populate();
    }

    /**
     * Populate
     * 
     * This function is used to generate Joystick and Button Inputs
     */
    public void populate(){

    }

    /**
     * Registered Controller ID
     * 
     * @return Int
     */
    public int getId(){
        return this.port;
    }

    /**
     * DPad Direction
     * 
     * @param name Name of the Button to store it as
     * @param povIndex ID of the DPad Input
     */
    protected void pushDPad(String name, int povIndex){
        DPads.put(name, new DPad(this.joy, povIndex));
    }

    /**
     * Push Button
     * 
     * @param name Name of the Button to store it as
     * @param buttonId ID of the Button Id
     */
    protected void pushButton(String name, int buttonId){
        Buttons.put(name, new ButtonObject(this.joy, buttonId));
    }

    /**
     * Push Joystick
     * 
     * @param name Name of the Joystick
     * @param axisX ID of the X Axis
     */
    protected void pushJoystick(String name, int axisX){
        Joysticks.put(name, new JoystickObject(this.joy, axisX));
    }

    /**
     * Push Joystick
     * 
     * @param name Name of the Joystick
     * @param axisX ID of the X Axis
     * @param axisY ID of the Y Axis
     */
    protected void pushJoystick(String name, int axisX, int axisY){
        Joysticks.put(name, new JoystickObject(this.joy, axisX, axisY));
    }

    /**
     * Push Joystick
     * 
     * @param name Name of the Joystick
     * @param axisX ID of the X Axis
     * @param axisY ID of the Y Axis
     * @param axisZ ID of the Z Axis
     */
    protected void pushJoystick(String name, int axisX, int axisY, int axisZ){
        Joysticks.put(name, new JoystickObject(this.joy, axisX, axisY, axisZ));
    }

    /**
     * Get Joystick
     * 
     * @param name Name of the Joystick Assigned
     * @return Joystick Object
     */
    public JoystickObject getJoystick(String name) {
        JoystickObject joy = Joysticks.get(name);
        if(joy == null){
            throw new IllegalArgumentException("Can't find Joystick name: " + name);
        }
        return joy;
    }

    /**
     * Get Button
     * 
     * @param name Name of the Button Assigned
     * @return Button Object
     */
    public ButtonObject getButton(String name) {
        ButtonObject btn = Buttons.get(name);
        if(btn == null){
            throw new IllegalArgumentException("Can't find Button name: " + name);
        }
        return btn;
    }

    /**
     * Get Button Value
     * 
     * @param name Name of the Button Assigned
     * @return Boolean Value
     */
    public boolean getButtonValue(String name) {
        return getButton(name).get();
    }

    /**
     * Get Button
     * 
     * @param name Name of the Button Assigned
     * @return Button Object
     */
    public DPad getDPad(String name) {
        DPad pad = DPads.get(name);
        if(pad == null){
            throw new IllegalArgumentException("Can't find DPad name: " + name);
        }
        return pad;
    }

}