package frc.robot.helpers.controllers;

/**
 * Logitech_X
 * The Map of the Logitech Driving Force EX USB Controller
 * 
 * @author Noah Halstead <nhalstead00@gmail.com>
 */

public class Logitech_D extends Controller {

    public Logitech_D(int port){
        super(port);
    }

    public void populate(){
        pushButton("A", 1);
        pushButton("B", 2);
        pushButton("X", 3);
        pushButton("Y", 4);
        pushButton("LB", 5);
        pushButton("RB", 6);
        pushButton("RT", 7);
        pushButton("LT", 8);
        pushButton("BACK", 9);
        pushButton("START", 10);
        pushButton("LEFT", 11);
        pushButton("RIGHT", 12);

        pushJoystick("LEFT", 0, 1);
        pushJoystick("RIGHT", 2, 3);
    }

}