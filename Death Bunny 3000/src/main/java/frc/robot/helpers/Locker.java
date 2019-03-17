package frc.robot.helpers;

import java.util.HashMap;
import java.util.Map;

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

    public static void lock(String name){
        Locker.lockStatus.put(name.toUpperCase(), true);
    }

    public static void unlock(String name){
        Locker.lockStatus.put(name.toUpperCase(), false);
    }

}