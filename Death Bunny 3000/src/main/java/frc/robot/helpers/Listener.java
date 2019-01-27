package frc.robot.helpers;


/**
 * Listener
 * Action Called when a new Log Event is Added.
 * 
 * @author Noah Halstead <nhalstead00@gmail.com>
 */
public class Listener {

    public Listener(){
        // Override Me, I do Nothing

    }

    /**
     * Take in the Error and Call the Functions based on the Mode.
     * 
     * @param type
     * @param Message
     */
    public void handle(console.logMode type, String Message){
        this.onAll(type, Message);

        if(type == console.logMode.kDebug){
            this.onDebug(type, Message);
        }
        else if(type == console.logMode.kInfo){
            this.onInfo(type, Message);
        }
        else if(type == console.logMode.kWarn){
            this.onWarn(type, Message);
        }
        else if(type == console.logMode.kError){
            this.onError(type, Message);
        }
        else if(type == console.logMode.kFatal){
            this.onFatal(type, Message);
        }

    } 

    public void onAll(console.logMode type, String Message){

    }

    public void onDebug(console.logMode type, String Message){
        
    }

    public void onInfo(console.logMode type, String Message){
        
    }

    public void onWarn(console.logMode type, String Message){
        
    }

    public void onError(console.logMode type, String Message){
        
    }

    public void onFatal(console.logMode type, String Message){
        
    }

}