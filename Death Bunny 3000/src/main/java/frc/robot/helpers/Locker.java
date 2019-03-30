package frc.robot.helpers;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.PWMSpeedController;

/**
 * Listener
 * Action Called when a new Log Event is Added.
 * 
 * All `name` values are converted to UpperCase to allow
 *  for any case matching.
 * 
 * @author Noah Halstead <nhalstead00@gmail.com>
 */
public class Locker {

    private static Map<String, Boolean> lockStatus = new HashMap<String, Boolean>();

    public static boolean isLocked(String name){
        if(Locker.lockStatus.get(name.toUpperCase()) == null){
            return false;
        }
        return Locker.lockStatus.get(name.toUpperCase());
    }

    /**
     * Lock
     * 
     * Set a Key to be True, Create lock
     * 
     * @param name String Name
     */
    public static void lock(String name){
        Locker.lockStatus.put(name.toUpperCase(), true);
    }

    /**
     * Lock via the Class Interface
     * 
     * @param t Class Instance
     */
    public static void lock(Class<?> t){
        Locker.lockStatus.put("CLASS" +t.getCanonicalName(), true);
    }

    /**
     * Lock
     * 
     * Set a Key to be True, Create lock from PWMSpeedController
     * 
     * @param SpeedCtrl Convert Speed Contorller to String Name
     */
    public static void lock(PWMSpeedController SpeedCtrl){
        Locker.lockStatus.put("PWM" + SpeedCtrl.getName() + SpeedCtrl.getChannel(), true);
    }

    /**
     * Unlock
     * 
     * Set a Key to be True, Create lock
     * 
     * @param name String Name
     */
    public static void unlock(String name){
        Locker.lockStatus.put(name.toUpperCase(), false);
    }

    /**
     * Unlock via the Class Interface
     * 
     * @param t Class Instance
     */
    public static void unlock(Class<?> t){
        Locker.lockStatus.put("CLASS" +t.getCanonicalName(), false);
    }

    /**
     * Unlock
     * 
     * Set a Key to be True, Create lock from PWMSpeedController
     * 
     * @param SpeedCtrl Convert Speed Contorller to String Name
     */
    public static void unlock(PWMSpeedController SpeedCtrl){
        Locker.lockStatus.put("PWM" + SpeedCtrl.getName() + SpeedCtrl.getChannel(), false);
    }

}