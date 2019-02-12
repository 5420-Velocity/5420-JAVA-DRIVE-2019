package frc.robot.helpers;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * console
 * Replicates some of the same functions that JavaScript
 *  has in its `console` object.
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
    private NetworkTableEntry ledMode;
    private NetworkTableEntry camMode;
    private NetworkTableEntry pipeline;

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
        this.table = NetworkTableInstance.getDefault().getTable(tableName);
        this.tx = this.table.getEntry("tx");
        this.ty = this.table.getEntry("ty");
        this.ta = this.table.getEntry("ta");
        this.ts = this.table.getEntry("ts");
        this.tv = this.table.getEntry("tv");
        this.ledMode = this.table.getEntry("ledMode");
        this.camMode = this.table.getEntry("camMode");
        this.pipeline = this.table.getEntry("pipeline");

        if(Limelight.constantInstance == null){
            // Save this Instance to the Static Part if it's not set.
            Limelight.constantInstance = this;
        }
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
        this.ledMode.setNumber(mode.value);
    }

    /**
     * Sets limelight’s operation mode
     * 
     * @param mode
     */
    public void setMode(Limelight.camMode mode){
        this.camMode.setNumber(mode.value);
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

}