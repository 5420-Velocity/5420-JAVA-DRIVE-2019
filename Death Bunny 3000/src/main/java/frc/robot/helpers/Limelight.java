package frc.robot.helpers;

import java.net.URL;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * Limelight
 * Interface to work with the Limelight, Use Limelight.getInstance() to
 *  get the default Limelight network table.
 * 
 * This Class has LEDs setup for Version 2019.6.1
 * 
 * @author Noah Halstead <nhalstead00@gmail.com>
 * @link http://docs.limelightvision.io/en/latest/getting_started.html#networking-setup
 */
public class Limelight {

    private NetworkTable table;
    private NetworkTableEntry tx;
    private NetworkTableEntry ty;
    private NetworkTableEntry ta;
    private NetworkTableEntry ts;
    private NetworkTableEntry tv;
    private NetworkTableEntry ledModeNT;
    private NetworkTableEntry camModeNT;
    private NetworkTableEntry pipeline;
    private NetworkTableEntry stream;

    private NetworkTableEntry Interface;
    private NetworkTableEntry VideoStream;
    private NetworkTableEntry PipelineName;

    private double KnownDistance;
    private double KnownArea;

    /**
     * Represents the LED Modes for the Limelight
     */
    public enum ledMode {
        kAuto(0),
        kOff(1),
        kBlink(2),
        kOn(3);

        public final int value;

        ledMode(int value) {
            this.value = value;
        }
    }

    /**
     * Represents the Camera Mode for the Limelight
     */
    public enum camMode {
        kVision(0),
        kCamera(1);

        public final int value;

        camMode(int value) {
            this.value = value;
        }
    }

    /**
     * Represents the Camera VIdo Output Mode for the Limelight
     * @since v2018.2
     */
    public enum videoMode {
        Standard(0),     // Side-by-side streams if a webcam is attached to Limelight
        PIPMain(1),      // The secondary camera stream is placed in the lower-right corner of the primary camera stream.
        PIPSecondary(2); // PiP Secondary - The primary camera stream is placed in the lower-right corner of the secondary camera stream.

        public final int value;

        videoMode(int value) {
            this.value = value;
        }
    }

    /**
     * Allows you to create one instance of the call and call it 
     *  later without needing to recall a new instance or save it.
     * 
     * @var Limelight
     */
    private static Limelight constantInstance;

    /**
     * Return the Static Instance of the Limelight Object.
     * Limelight System resources and memory by using just one object.
     * 
     * @return Limelight
     */
    public static Limelight getInstance(){
        if(Limelight.constantInstance == null){
            // Make a new Instance
            Limelight.constantInstance = new Limelight();
        }
        return Limelight.constantInstance;
    }

    /**
     * Start the Limelight with the Default Network Table
     * 
     */
    public Limelight(){
        this("limelight");
        
    }

    /**
     * Createa a new Limelight Instance with a Custome Table
     * 
     * @param NetworkTable
     */
    public Limelight(String tableName){
        NetworkTableInstance ds  = NetworkTableInstance.getDefault(); // Get the Driver Station Network Table Instance.

        this.table = NetworkTableInstance.getDefault().getTable(tableName);
        this.tx = this.table.getEntry("tx");
        this.ty = this.table.getEntry("ty");
        this.ta = this.table.getEntry("ta");
        this.ts = this.table.getEntry("ts");
        this.tv = this.table.getEntry("tv");
        this.stream = this.table.getEntry("stream");
        this.ledModeNT = this.table.getEntry("ledMode");
        this.camModeNT = this.table.getEntry("camMode");
        this.pipeline = this.table.getEntry("pipeline");

        this.Interface = ds.getEntry(tableName + "_Interface");
        this.PipelineName = ds.getEntry(tableName + "_PipelineName");
        this.VideoStream  = ds.getEntry(tableName + "_Stream");

        if(Limelight.constantInstance == null){
            // Save this Instance to the Static Part if it's not set.
            Limelight.constantInstance = this;
        }
    }

    /**
     * Get the IP Address of the Limelight
     * 
     * @return IP Address
     */
    public String getIP(){
        String host = "";
        try {
            URL url = new URL(this.Interface.getString(""));
            host = url.getHost();
        }
        catch(Exception e){
            
        }
        return host;
    }

    /**
     * Offset Angle Horizontal
     * 
     * @return
     */
    public double getX(){
        return this.tx.getDouble(0.0);
    }

    /**
     * Offset Angle Vertical
     *  Vertical Offset From Crosshair To Target (-20.5 degrees to 20.5 degrees)
     * 
     * @return
     */
    public double getY(){
        return this.ty.getDouble(0.0);
    }

    /**
     * Area Filled in the Setup Pipeline
     * 
     * @return
     */
    public double getA(){
        return this.ta.getDouble(0.0);
    }

    /**
     * Skew Value
     * 
     * @return
     */
    public double getS(){
        return this.ts.getDouble(0.0);
    }

    /**
     * Set the LED Mode to a Set ENUM Value.
     * 
     * @param ledMode
     */
    public void setLed(Limelight.ledMode mode){
        this.ledModeNT.setNumber(mode.value);
    }

    /**
     * Get the LED Mode to a Set ENUM Value.
     * 
     * Returns if the LED is on, This is General,
     *   On or Off, Not On Left or On Right
     * 
     * @return ledMode
     */
    public Limelight.ledMode getLed(){
        Number t = this.ledModeNT.getNumber(0);
        if(t.intValue() == 1){
            return Limelight.ledMode.kOff;
        }
        else if(t.intValue() == 2){
            return Limelight.ledMode.kBlink;
        }
        else if(t.intValue() == 3){
            return Limelight.ledMode.kOn;
        }
        else {
            return Limelight.ledMode.kAuto;
        }
    }

    /**
     * Get if the LED is on.
     * 
     * @return Is LED On
     */
    public boolean isLedOn(){
        Number t = this.ledModeNT.getNumber(0);
        if(t.intValue() == 1 || t.intValue() == 2 || t.intValue() == 3){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Sets limelight’s LED mode usinng ENUM
     * 
     * @param mode
     */
    public void setMode(Limelight.camMode mode){
        this.setMode(mode.value);
    }

    /**
     * Sets limelight’s LED mode using an Int
     * 
     * @param modeCode
     */
    public void setMode(int modeCode){
        this.camModeNT.setNumber(modeCode);
    }

    /**
     * Returns if it has the target
     * 
     * @return Has Target
     */
    public boolean hasTarget(){
        return this.tv.getDouble(0) == 1;
    }

    /**
     * Set the limelight’s current pipeline 0-9
     * 
     * @param int
     */
    public void setPipeline(int line){
        this.pipeline.setNumber(line);
    }

    /**
     * Get the limelight’s current pipeline 0-9
     * 
     * @return Pipeline Index
     */
    public int getPipeline(){
        return this.pipeline.getNumber(0.0).intValue();
    }

    /**
     * Get the Pipeline Name in selection.
     * 
     * @return Pipeline Name
     */
    public String getPipelineName(){
        return this.PipelineName.getString("");
    }

    /**
     * Set the limelight’s Video Output Mode on port 5802
     * 
     * @param videoMode
     */
    public void setVideo(Limelight.videoMode vmode){
        this.stream.setNumber(vmode.value);
    }

    /**
     * Get the Stream URL for the Limelight
     * 
     * @return Stream URL
     */
    public String getStreamUrl(){
        return this.VideoStream.getString("");
    }

    /**
     * Get the Interface URL for the Limelight
     * 
     * @return Interface URL
     */
    public String getInterfaceUrl(){
        return this.Interface.getString("");
    }

    /**
     * Set the Distance Calculation Parts for the getDistance function
     * 
     * @param KnownDistance
     * @param KnownArea
     */
    public void setDistanceControl(double KnownDistance, double KnownArea){
        this.KnownDistance = KnownDistance;
        this.KnownArea = KnownArea;
    }
     
    /**
     * Return the Distance from the Target
     *  Both Params should be constant, Both Known Values
     *  The KnonwArea is based off of the KnownDistance
     * 
     * Place the Robot down on the ground and measure from the limelight
     *  then look at the limelight's web interface for the `ta` value.
     * Pass the 2 values in as a contant and it will return the % of the
     *  selected KnownDistance.
     * 
     * @link http://docs.limelightvision.io/en/latest/cs_estimating_distance.html#using-area-to-estimate-distance
     * @return The Aprox Distnace away from the Target in View with only 2 decimal places
     */
    public double getDistance(){
        if(Math.abs(this.getA()) < 0.02){
            return 0.0;
        }

        double k = this.KnownDistance * Math.sqrt(this.KnownArea);
        double v = k / Math.sqrt(this.getA());
        return (double) Math.round(v * 100) / 100;
    }

}