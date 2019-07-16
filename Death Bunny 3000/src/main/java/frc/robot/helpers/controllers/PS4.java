package frc.robot.helpers.controllers;

/**
 * Logitech_X
 * The Map of the Logitech Driving Force EX USB Controller
 * 
 * @author Noah Halstead <nhalstead00@gmail.com>
 */

public class PS4 extends Controller {

    public PS4(int port){
        super(port);
    }

    public void populate(){
        pushButton("SQUARE", 1);
        pushButton("CROSS", 2);
        pushButton("CIRCLE", 3);
        pushButton("TRIANGLE", 4);
        pushButton("LB", 5);
        pushButton("RB", 6);
        pushButton("LT", 7);
        pushButton("RT", 8);
        pushButton("SHARE", 9);
        pushButton("OPTIONS", 10);
        pushButton("LEFT", 11);
        pushButton("RIGHT", 12);
        pushButton("PS", 13);
        pushButton("TRACKPAD", 14);

        pushJoystick("LEFT", 0, 1);
        pushJoystick("RIGHT", 2, 3);
        pushJoystick("LT", 4);
        pushJoystick("RT", 5);
    }

}