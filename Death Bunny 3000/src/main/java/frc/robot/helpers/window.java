package frc.robot.helpers;

import edu.wpi.first.wpilibj.DriverStation;


/**
 * window
 * Replicates some of the same functions that JavaScript
 *  has in its `console` object.
 * 
 * @author Noah Halstead <nhalstead00@gmail.com>
 */
public class window {

    private static DriverStation.Alliance color;

    private static char[] gameData;

    /**
     * 
     * 
     * @return DriverStation.Alliance, Color of the Driver Station
     */
    public static DriverStation.Alliance getColor(){
        if(window.color == null){
            window.color = DriverStation.getInstance().getAlliance();
        }
        return window.color;
    }

    /**
     * 
     * 
     * @return Double, Match Time
     */
    public static double getTime(){
        return DriverStation.getInstance().getMatchTime();
    }
     

    /**
     * 
     * 
     * @return char[], Match Data
     */
    public static char[] getData(){
        if(window.gameData == null){
            window.gameData = DriverStation.getInstance().getGameSpecificMessage().toCharArray();
        }
        return window.gameData;
    }
}