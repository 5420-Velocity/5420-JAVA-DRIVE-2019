package frc.robot.helpers.controllers;

import frc.robot.helpers.ButtonObject;
import frc.robot.helpers.JoystickObject;

/**
 * LogitechDrivingForceEXMap
 * The Map of the Logitech Driving Force EX USB Controller
 * 
 * @author Noah Halstead <nhalstead00@gmail.com>
 */

public class LogitechDrivingForceEx extends Controller {

    public LogitechDrivingForceEx (int port){
        super(port);
    }

    public void populate(){
        pushJoystick("Steering", 0); // Left -1, Center 0, Right 1
        pushJoystick("GasBreak", 1); // On a Range from -1 to 1; This is for Break and Gas; Gas 1, Break -1, Neutral 0

        pushButton("X", 0);
        pushButton("Square", 1);
        pushButton("Circle", 2);
        pushButton("Triangle", 3);
        pushButton("PaddleRight", 4);
        pushButton("PaddleLeft", 5);
        pushButton("R2", 6);
        pushButton("L2", 7);
        pushButton("Select", 8);
        pushButton("Start", 9);
        pushButton("R3", 10);
        pushButton("L3", 11);
    }

}