package frc.robot.helpers;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.io.File;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableType;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.DriverStation.MatchType;

import java.util.Date;
import java.util.HashMap;

/**
 * This is almost like what Team 272 have for their Logging system
 *  but solves some issues I see with their system.
 * 
 * 1. You have to know your structure ahead of time before writing
 *   + This is a dynamic structure, add data as needed. 
 * 2. All values must be written for each line or a blank value
 *   + This new Class will only write Changes to Values, Like git
 * 3. Writes to Volatile Memory then Syncs to a USB Device on a user Trigger.
 *   + Saves to USB Device and Provides the Choice of USB Devices
 * 
 * @author Noah Halstead <nhalstead00@gmail.com>
 */
public class Save {

    /**
     * File IO Stream in Java, Allows you to read and write
     *  to the file. Its set to Write Only, With non Blocking.
     * 
     * @var FileWriter
     */
    private FileWriter fileStream;

    /**
     * Keep track of the Time since Init
     * 
     * @var Timer
     */
    private Timer timer;

    /**
     * Filename
     * 
     * @var String
     */
    private String filename = "na";

    /**
     * Filename
     * 
     * @var Mode
     */
    private Mode fileLocation;

    /**
     * What was pushed to the File
     * 
     * @var HashMap
     */
    private Map<String, String> lastPushValues;

    /**
     * Buffer
     * 
     * @var HashMap
     */
    private Map<String, String> pendingPushValues;

    /**
     * Allows you to create one instance of the call and call it 
     *  later without needing to recall a new instance or save it.
     * 
     * @var Save
     */
    private static Save constantInstance;

    /**
     * Add Timestamp to Filename
     * 
     * @var boolean
     */
    private static Boolean tsFile = true;

    /**
     * Save Location Mode
     * 
     * @var Mode
     */
    public static Mode defaultMode = Mode.kInternalTemporary;
    
    /**
     * Ignore Data Added when Disabled
     * 
     * @var boolean
     */
    public Boolean ignoreInDisabled = true;
    
    /**
     * Is new data ready to be Written?
     * 
     * @var boolean
     */
    private Boolean pendingWrites = false;
    
    /**
     * Return the Static Instance of the Save Object.
     * Save System resources and memory by using just one object.
     * 
     * @return Save
     */
    public static Save getInstance(){
        if(Save.constantInstance == null){
            // Make a new Instance
            Save.constantInstance = new Save("telemetry", Save.defaultMode);
        }
        return Save.constantInstance;
    }

    /**
     * Return the Static Instance of the Save Object.
     * Save System resources and memory by using just one object.
     * 
     * @param in
     */
    public static void setInstance(Save in){
        Save.constantInstance = in;
    }

    public enum Mode {
        kInternalTemporary("/var/volatile/tmp"),
        kInternalPerminate("/home/lvuser"),
        kUSBFirst("/u"),
        kUSBLast("/v");

        public final String value;

        Mode(String value){
            this.value = value;
        }
    }

    public Save(String name){
        this(name, Save.defaultMode);
    }

    /**
     * Constructor Function
     * Build a new Instance of Save to Write Logs to a File.
     * 
     * @param name
     * @param type
     */
    public Save(String name, Mode fileLocation){
        // Check to see if the File Exists.
        // 
        this.filename = name;
        this.fileLocation = fileLocation;
        
        System.out.println("Input Save Location: " + this.fileLocation.value);

        File directory = new File(this.fileLocation.value);
        if(!directory.exists()){
            System.err.println("Orignal Selected File Location `" + this.fileLocation.value + "` can not be accessed.");
            this.fileLocation = Mode.kInternalTemporary;
        }

        try {
            this.fileStream = new FileWriter( this.getFilename() );
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(Save.constantInstance == null){
            // Save this Instance to the Static Part if it's not set.
            Save.constantInstance = this;
        }

        this.timer = new Timer();
        this.timer.start();

        this.lastPushValues = new HashMap<String, String>();
        this.pendingPushValues =  new HashMap<String, String>();

        System.out.println("Final Save to file: " + this.getFilename());
    }

    /**
     * Push Exception to String, First StackTrace and Suppressed Message
     * 
     * @param e Exception
     * @return
     */
    public Save push(Exception e){
        this.writeComment(e.getSuppressed()[0].toString());
        this.writeComment(e.getStackTrace()[0].toString());

        return this;
    }
    
    /**
     * Write Value based on Network Table Entry
     * 
     * @param NetworkTableEntry
     * @param NetworkTableType
     * @return Save Return the Self Instance to Call Functions back to back.
     */
    public Save push(NetworkTableEntry value, NetworkTableType type){

        // Used as Temp Stoage to be Converted to String
        Object valueT = null;

        // Switch Case Switching based on the Type of Entry.
        switch( NetworkTableType.getFromInt(type.getValue()) ){

            case kBoolean:
                valueT =  value.getBoolean(false);
            case kDouble:
                valueT = value.getDouble(0.0);
            case kString:
                valueT = value.getString("");
            case kRaw:
                // valueT = value.getRaw(null);
            case kBooleanArray:
            case kDoubleArray:
            case kStringArray:
            case kRpc:
            case kUnassigned:
        }

        // Convert to String
        String valueR = String.format("%s", valueT);

        return this.push(value.getName(), valueR);
    }

    /**
     * Write Value based on the Current Speed Controller
     * 
     * @param String Key
     * @param SpeedController Value
     * @return Save Return the Self Instance to Call Functions back to back.
     */
    public Save push(String key, SpeedController value){
        
        return this.push(key, value.get());
    }
    
    /**
     * Write Value based on Key Input
     * Internally converts long to String.
     * 
     * @param String Key
     * @param long Value
     * @return Save Return the Self Instance to Call Functions back to back.
     */
    public Save push(String key, long value){

        return this.push(key, Long.toString(value) );
    }

    /**
     * Write Value based on Key Input
     * Internally converts Float to String.
     * 
     * @param String Key
     * @param Float Value
     * @return Save Return the Self Instance to Call Functions back to back.
     */
    public Save push(String key, Float value){
        return this.push(key, String.valueOf(value.toString()) );
    }

    /**
     * Write Value based on Key Input
     * 
     * @param String Key
     * @param Double Value
     * @return Save Return the Self Instance to Call Functions back to back.
     */
    public Save push(String key, Double value){

        String valueR = String.format("%.2f", value);

        return this.push(key, valueR);
    }

    /**
     * Write Value based on Key Input
     * 
     * @param String Key
     * @param int Value
     * @return Save Return the Self Instance to Call Functions back to back.
     */
    public Save push(String key, int value){
        
        String valueR = String.format("%s", value);

        return this.push(key, valueR);
    }

    /**
     * Write Value based on Key Input
     * 
     * @param String Key
     * @param boolean Value
     * @return Save Return the Self Instance to Call Functions back to back.
     */
    public Save push(String key, Boolean value){
        
        String valueR = value.toString();

        return this.push(key, valueR);
    }

    /**
     * Write Value based on Key Input
     * Looks at this.ignoreInDisabled to see if it needs to ignore writes.
     * 
     * @param String Key
     * @param String Value
     * @return Save Return the Self Instance to Call Functions back to back.
     */
    public Save push(String key, String value){

        if(this.ignoreInDisabled == true && Save.getMode() == "disable"){
            // Don't write to Telem File, Save Data and Time.
            return this;
        }

        // Set the value if its different from tha last value.
        if(!this.lastPushValues.containsKey(key) || this.lastPushValues.get(key) != value){
            this.pendingWrites = true;
            this.pendingPushValues.put(key, value);
        }

        return this;
    }

    /**
     * Write Values from Buffer to the selected Output Device
     * 
     * @return Save Return the Self Instance to Call Functions back to back.
     */
    public void sync(){

        if(this.pendingWrites == false){
            // Return, No Writes
            return;
        }

        // Add time and Mode to the Mix.
        this.push("time", this.getTime());
        this.push("mode", Save.getMode());
        this.push("state", Save.getEnv());
        this.push("mtime", Save.getMatchtime());

        this.lastPushValues = this.pendingPushValues;

        // Generate JSON From Object
        //this.writeRaw(JSONObject.wrap(this.pendingPushValues).toString());

        this.pendingWrites = false;
        this.pendingPushValues.clear();

    }

    /**
     * Returns the Full Filename with the Selected File
     *  path as well as the file proper filename.
     * 
     * @return
     */
    public String getFilename(){
        if(this.filename.length() == 0 )
    		this.filename = "telemetry";
    		
    	String fullName = this.fileLocation.value + "/" + this.filename;
    	
    	if(Save.tsFile == true)
            fullName += "_" +  new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());

    	fullName += ".jsonl";

    	return fullName;
    }
    
    /**
     * Clear Pending Writes
     * Good to do when Changing Mode States
     * 
     * @return Save Return the Self Instance to Call Functions back to back.
     */
    public Save clearBuffer(){
        
        this.pendingPushValues.clear();
        this.pendingWrites = false;
        return this;
    }
    
    /**
     * Clear Push Cache
     * This what prevents duplicate values being written to the file.
     * 
     * @return Save Return the Self Instance to Call Functions back to back.
     */
    public Save clearPush(){
        
        this.lastPushValues.clear();
        this.pendingWrites = false;
        return this;
    }
    
    /**
     * Retuns the Total Time Since Class Instance Init.
     * 
     * @return double
     */
    public double getTime(){
        
        return this.timer.get();
    }
    
    /**
     * Write string data to File.
     * Write Directly to the file, Assume it is noting in order.
     * 
     * @param String Log Data
     * @return Save Return the Self Instance to Call Functions back to back.
     */
    public Save writeComment(String input){
        this.writeRaw("# " + input);
        return this;
    }
    
    /**
     * Write string data to File.
     * This function will wrtie directly to the file,
     *  no write buffer
     * 
     * 
     * @param String Log Data
     * @return Save Return the Self Instance to Call Functions back to back.
     */
    public Save writeRaw(String... input){
        
        String buffer = "";
        for (Object single : input) {
            buffer += single.toString();
        }
        
        try {
            this.fileStream.write( buffer + "\n" );
        }
        catch(Exception e){
            System.out.println("Conversion or Save Error, " + e.getCause().getMessage());
        }
        
        return this;
    }
    
    /**
     * Close the File and save the Last Logs
     * 
     */
    public void close(){
        this.sync();
        try {
            this.fileStream.close();
        }
        catch(Exception e){
            System.out.println("Conversion or Save Error, " + e.getCause().getMessage());
        }
        
    }

    /**
     * Return Match Time
     * 
     * @return MatchTime
     */
    public static double getMatchtime(){
        return Timer.getMatchTime();
    }

    /**
     * Get Current Game Mode
     * 
     * Returns
     *    auto
     *    teleop
     *    test
     *    disable
     * 
     * @return String
     */
    public static String getMode(){
        if (DriverStation.getInstance().isAutonomous() == true) 		   return "auto";
        else if (DriverStation.getInstance().isOperatorControl() == true)  return "teleop";
        else if (DriverStation.getInstance().isTest() == true)             return "test";
        else if (DriverStation.getInstance().isDisabled() == true) 		   return "disable";
        else return "";
    }

    /**
     * Returns Env Conditions
     * 
     * (T|F)     First Char is if the Driver Station is Connected.
     * (F|I)     Field, If the Device in Independent or FMS Connected.
     * (B|R|I)   Alliance Color.
     * (E|D)     If the Robot is Enabled to be Driven by User or Code.
     * (N|P|Q|E) Match Types: None, Practice, Qualification, Elimination
     * 
     * @return String
     */
    public static String getEnv(){

        DriverStation d = DriverStation.getInstance();

        Boolean driverStation = d.isDSAttached();
        Boolean field = d.isFMSAttached();
        Alliance alliance = d.getAlliance();
        Boolean enabled = d.isEnabled();
        MatchType type = d.getMatchType();

        String color = "I";
        if(alliance == Alliance.Blue){
            color = "B";
        }
        else if(alliance == Alliance.Red){
            color = "R";
        }

        String mtype = "N";
        if(type == MatchType.None){
            mtype = "N";
        }
        else if(type == MatchType.Practice){
            mtype = "P";
        }
        else if(type == MatchType.Qualification){
            mtype = "Q";
        }
        else if(type == MatchType.Elimination){
            mtype = "E";
        }

        return ((driverStation)?"T":"F") + ((field)?"F":"I") + color + ((enabled)?"E":"D") + mtype;
    }

    /**
     * Return the Battery Voltage.
     * 
     * @return Return the Battery Voltage with 2 Decimal Points.
     */
    public static String batt(){
        return String.format("%.2f", RobotController.getBatteryVoltage() );
    }
 
}
