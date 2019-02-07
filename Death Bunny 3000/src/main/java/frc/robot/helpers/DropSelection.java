package frc.robot.helpers;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

/**
 * Uses the SendableChooser Object from WPI to
 *  do all of the extra steps that is adding in the NetworkTableEntry,
 *  add entry etc.
 * 
 * Just a helper class.
 * 
 * @author Noah Halstead <nhalstead00@gmail.com>
 */
public class DropSelection<V> {

    /**
     * SendableChooser Object
     * 
     * @var SenadableChooser
     */
    private SendableChooser<V> selection = new SendableChooser<V>();

    /**
     * If the Sendable Chooser was sent once
     * 
     * @var Boolean
     */
    private boolean sentOnce = false;

    /**
     * Init by String Table Entry on Default Table.
     * Key found at: /SmartDashboard/{entryName}
     * 
     * @param entryName
     */
    public DropSelection(String entryName){

        this.selection.setName(entryName);
    }

    /**
     * Return the Entry Name
     * 
     */
    public String getName(){
        
        return this.selection.getName();
    }

    /**
     * Set the Object name.
     * 
     * @param name
     */
    public void setName(String name){

        this.selection.setName(name);
    }

    /**
     * Send data to the selected Entry
     * 
     */
    public void send(){
        this.sentOnce = true;

        //this.en.setValue(this.selection);
        SmartDashboard.putData(this.selection);
    }

    /**
     * If the SendableChooser was sent once to the
     *  selected entry.
     * 
     * @return
     */
    public boolean hasSent(){
        return this.sentOnce;
    }

    /**
     * Set the Chooser Object, Overriding anything in it.
     * 
     * @param sc
     */
    public void setChooser( SendableChooser<V> sc ){
        this.selection = sc;
    }

    /**
     * Get the Chooser Object
     * 
     * @return <V>
     */
    public SendableChooser<V> getChooser(){
        return this.selection;
    }

    /**
     * Return the Value Stored
     * 
     * @return <V>
     */
    public V get(){
        return this.selection.getSelected();
    }

    /**
     * Add Option using <V> datatype
     * 
     * @param name
     * @param op
     */
    public void add(String name, V op){
        this.add(name, false, op);
    }

    /**
     * Add Option using <V> datatype AS DEFAULT
     * 
     * @param name
     * @param setDefault
     * @param op
     */
    public void add(String name, boolean setDefault, V op){
        if(setDefault == true){
            this.selection.setDefaultOption(name, op);
        }
        else {
            this.selection.addOption(name, op);
        }
    }

}