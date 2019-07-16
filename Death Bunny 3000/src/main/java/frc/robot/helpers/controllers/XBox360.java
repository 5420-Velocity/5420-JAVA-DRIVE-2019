package frc.robot.helpers.controllers;

/**
 * XBox360
 * The Map of the Logitech Driving Force EX USB Controller
 * 
 * @author Noah Halstead <nhalstead00@gmail.com>
 */

public class XBox360 extends Controller {

    public XBox360(int port){
        super(port);
    }

    public void populate(){
        pushButton("A", 1);
        pushButton("B", 2);
        pushButton("X", 3);
        pushButton("Y", 4);
        pushButton("LB", 5);
        pushButton("RB", 6);
        pushButton("BACK", 7);
        pushButton("START", 8);
        pushButton("LEFT", 9);
        pushButton("RIGHT", 10);

        pushJoystick("LEFT", 0, 1);
        pushJoystick("RIGHT", 2, 3);
        pushJoystick("LT", 4);
        pushJoystick("RT", 5);
    }

}