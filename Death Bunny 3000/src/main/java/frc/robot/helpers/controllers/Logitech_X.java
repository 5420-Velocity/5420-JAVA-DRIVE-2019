package frc.robot.helpers.controllers;

/**
 * Logitech_X
 * The Map of the Logitech Driving Force EX USB Controller
 * 
 * @author Noah Halstead <nhalstead00@gmail.com>
 */

public class Logitech_X extends Controller {

    public Logitech_X(int port){
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
        pushJoystick("RT", 4);
        pushJoystick("LT", 5);
    }

}