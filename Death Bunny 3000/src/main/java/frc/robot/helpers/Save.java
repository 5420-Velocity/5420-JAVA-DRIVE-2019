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
public class Save extends SimpleJson {

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
     * Used to Allow or Deny Changes
     * 
     * @var Map
     */
    protected Map<String, Boolean> readonly;

    /**
     * Used to Allow or Deny Duplicate Inserts of Data
     * 
     * @var Map
     */
    protected Map<String, Boolean> allowDuplicates;

    public enum Mode {
        kInternalTemporary("/var/volatile/tmp"),
        kInternalPermanent("/home/lvuser"),
        kUSBFirst("/u"),
        kUSBLast("/v");

        public final String value;

        Mode(String value){
            this.value = value;
        }
    }

    public enum Flags {
        Readonly,
        ForceUppercase,
        ForceLowercase,
        AllowDuplicates
    }
    
    /**
     * Init With Constructor with the Default Mode
     * 
     * @param name
     */
    public Save(String name){
        this(name, Save.defaultMode);
    }

    /**
     * Constructor Function
     * Build a new Instance of Save to Write Logs to a File.
     * 
     * @param name
     * @param fileLocation
     */
    public Save(String name, Mode fileLocation){

        this.readonly = new HashMap<String, Boolean>();
        this.allowDuplicates = new HashMap<String, Boolean>();

        // Check to see if the File Exists.
        // 
        this.filename = name;
        this.fileLocation = fileLocation;
        
        System.out.println("Input Save Location: " + this.fileLocation.value);

        File directory = new File(this.fileLocation.value);
        if(!directory.exists()){
            System.err.println("Original Selected File Location `" + this.fileLocation.value + "` can not be accessed.");
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

        this.clear();

        System.out.println("Final Save to file: " + this.getFilename());
    }
    
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

    /**
     * Set a Flag
     * 
     * @param key String
     * @param f Flag Set
     */
    public void setFlag(String key, Flags f){

        if(f == Flags.Readonly){
            this.readonly.put(key, true);
        }
    }

    /**
     * Set a Flag with a Boolean
     * 
     * @param key String
     * @param f Flag Set
     * @param value Option
     */
    public void setFlag(String key, Flags f, Boolean value){

        if(f == Flags.Readonly){
            this.readonly.put(key, value);
        }
    }

    /**
     * Clear a Selected Flag
     * 
     * @param key String
     */
    public void clearFlag(String key, Flags f){
        
        if(f == Flags.Readonly){
            this.readonly.put(key, false);
        }
    }

    /**
     * Removes all Flags for a given Key
     * 
     * @param key String
     */
    public void clearFlags(String key){

        for (Flags f : Flags.values()) { 
            this.clearFlag(key, f); 
        }
    }
    
    /**
     * Write Value based on Network Table Entry
     * 
     * @param value
     * @param type
     */
    public void push(NetworkTableEntry value, NetworkTableType type){

        // Used as Temp Storage to be Converted to String
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
                System.out.println("Datatype Not Supported for Datatype to String");
                break;
            default:
                System.out.println("[Save] Unknown Datatype.");
        }

        // Convert to String
        String valueR = String.format("%s", valueT);
        this.push(value.getName(), valueR);
    }

    /**
     * Write Value based on the Current Speed Controller
     * 
     * @param key Key
     * @param value Value
     */
    public void push(String key, SpeedController value){
        
        this.push(key, value.get());
    }

    /**
     * Checks flags and Applies Modifications If Needed
     *  Check to see if the value is already pushed, Prevent Dups.
     * 
     * @param key String
     * @param value String
     * @param t Json Type
     */
    @Override
    protected void push(String key, String value, SimpleJson.Type t){

        if(this.readonly.get(key) != null && this.readonly.get(key) == true){
            // Readonly is Set and is True, Skip Write.
            return;
        }

        if(this.lastPushValues.get(key) != null && value == this.lastPushValues.get(key)){
            // Write Duplicate being Made.
            // Check to see if this is allowed to be a duplicate entry.    
            if(this.allowDuplicates.get(key) == null || this.allowDuplicates.get(key) == false){
                // Do not allow Duplicate Values in the Given Key.
                // If the allowDuplicates is not defined or is False.
                return;
            }
        }

        this.pendingWrites = true;
        super.push(key, value, t);
    }

    /**
     * Write Values from Buffer to the selected Output Device
     * 
     */
    public void sync(){

        if(this.pendingWrites == false){
            // Return, No Writes pending
            return;
        }

        // Add time and Mode to the Mix.
        this.push("time", this.getTime());
        this.push("mode", Save.getMode());
        this.push("state", Save.getEnv());
        this.push("mtime", Save.getMatchTime());

        this.lastPushValues = this.write;

        // Generate JSON From Input, Push to the File
        this.writeRaw(super.toString());

        this.pendingWrites = false;
        super.clear(); // Clear the JSON Objects
    }

    /**
     * Returns the Full Filename with the Selected File
     *  path as well as the file proper filename.
     * 
     * @return Full Filename
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
     * Clear Buffer Write
     * Cleared when writing to the File.
     * 
     */
    public void clearBuffer(){
        
        super.clear();
        this.pendingWrites = false;
    }
    
    /**
     * Clear Push Cache
     * This what prevents duplicate values being written to the file.
     * 
     */
    public void clearPush(){
        
        this.lastPushValues.clear();
    }
    
    /**
     * Reruns the Total Time Since Class Instance Init.
     * 
     * @return double
     */
    public double getTime(){
        
        return this.timer.get();
    }
    
    /**
     * Write string data to File.
     * This function will write directly to the file,
     *  no write buffer
     * 
     * 
     * @param input Log Data
     * @return Save Return the Self Instance to Call Functions back to back.
     */
    public Save writeRaw(String... input){
        
        StringBuilder buffer = new StringBuilder();
        for (String single : input) {
            buffer.append(single);
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
        this.sync(); // Write File Data
        this.clearBuffer(); // Clear Write Buffer
        this.clearPush(); // Clear Duplicate Write Records (This index prevents duplicate writes)

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
    public static double getMatchTime(){
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
