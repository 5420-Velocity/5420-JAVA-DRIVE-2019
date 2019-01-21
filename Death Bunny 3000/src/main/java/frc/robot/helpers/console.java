package frc.robot.helpers;

import edu.wpi.first.wpilibj.DriverStation;

/**
 * console
 * Replicates some of the same functions that JavaScript
 *  has in its `console` object.
 * 
 * @author Noah Halstead <nhalstead00@gmail.com>
 */
public class console {
    
    /**
     * This is to Imitate the JavaScript Version of `console.log`
     * 
     * @param input Data to send to the DriverStation
     * @return console 
     */
    public static void log(String... input){
        String buffer = "";
        for (String single : input) {
            buffer += single;
        }
        System.out.println(buffer);
    }

    /**
     * Write Error to Driver Station Log
     * 
     * @param input Data to send to the DriverStation
     */
    public static void error(String... input){
        String buffer = "";
        for (String single : input) {
            buffer += single;
        }
        DriverStation.reportError(buffer, false);
    }

    /**
     * Write Warning to Driver Station Log
     * 
     * @param input Data to send to the DriverStation
     */
    public static void warn(String... input){
        String buffer = "";
        for (String single : input) {
            buffer += single;
        }
        DriverStation.reportWarning(buffer, false);
    }

}