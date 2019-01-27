package frc.robot.helpers;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.DriverStation;

/**
 * console
 * Replicates some of the same functions that JavaScript
 *  has in its `console` object.
 * This also adds Log Control for allowing specific types
 *  to be logged while ignoring other types of logs.
 * 
 * @author Noah Halstead <nhalstead00@gmail.com>
 */
public class console {

    public static logMode allowLog = logMode.kAll;

    // Prevent too many Event Listeners, Save some time,
    //  any more than the value below, discard the new entry.
    public static int maxListeners = 5;

    // New Log Listeners
    // When new Logs are added, It will call the following functions.
    private static List<Listener> listeners = new ArrayList<Listener>();

    /**
     * Represents the LED Modes for the Limelight
     */
    public enum logMode {
        kDebug(5),
        kInfo(4),
        kWarn(3),
        kError(2),
        kFatal(1),
        kAll(0),
        kOff(-1);

        public final int value;

        logMode(int value) {
            this.value = value;
        }
    }

    /**
     * Log a Message with a Type of Message, This will
     *  log to the console based on the allowed log mode.
     * 
     * @param type
     * @param Message
     */
    public static void out(logMode type, String Message){
        if(type == logMode.kOff || type == logMode.kAll){
            // Reassign Type since the Type can not be All or Off.
            type = logMode.kDebug;
        }

        if(console.allowLog == logMode.kOff){
            // Log Nothing, Return, Stop Processing
            return;
        }

        if(allowLog == logMode.kAll || allowLog == logMode.kDebug){
            // Log Everything
            console.processLog(type, Message);
        }
        else if(allowLog == logMode.kFatal){
            if(type == logMode.kError || type == logMode.kWarn || type == logMode.kInfo || type == logMode.kDebug){
                // Ignore Error, Warn, Info, Debug Logs
                return;
            }
            console.processLog(type, Message);
        }
        else if(allowLog == logMode.kError){
            if(type == logMode.kWarn || type == logMode.kInfo || type == logMode.kDebug){
                // Ignore Warn, Info, Debug Logs
                return;
            }

            // Verify Log Level is Fatal or Less
            console.processLog(type, Message);
        }
        else if(allowLog == logMode.kWarn){
            if(type == logMode.kInfo || type == logMode.kDebug){
                // Ignore Info, Debug Logs
                return;
            }

            // Verify Log Level is Fatal or Less
            console.processLog(type, Message);
        }
        else if(allowLog == logMode.kInfo){
            if(type == logMode.kDebug){
                // Ignore Debug Logs
                return;
            }

            // Verify Log Level is Fatal or Less
            console.processLog(type, Message);
        }
    }

    /**
     * Log Based error based on LogMode input.
     * 
     * @param type
     * @param Message
     */
    public static void processLog(logMode type, String Message){

        if(type == logMode.kDebug){
            console.log(Message);
        }
        else if(type == logMode.kInfo){
            console.log(Message);
        }
        else if(type == logMode.kWarn){
            console.warn(Message);
        }
        else if(type == logMode.kError){
            console.error(Message);
        }
        else if(type == logMode.kFatal){
            console.error(Message);
        }

        // Call all Listeners
        for (Listener listener : listeners){
            listener.handle(type, Message);
        }

    }

    /**
     * Add Event to new Logs
     * 
     * @param action
     * @return If the Listener was added.
     */
    public boolean addListener(Listener action) {
        if(listeners.size() >= console.maxListeners){
            return false;
        }

        listeners.add(action);
        return true;
    }

    /**
     * Delete all Listener Actions from the List.
     * 
     */
    public void removeListeners() {
        if(!listeners.isEmpty()){
            listeners = new ArrayList<Listener>();
        }
    }

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