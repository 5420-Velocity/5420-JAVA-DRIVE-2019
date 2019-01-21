package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableEntry;
import java.util.Map;
import java.util.HashMap;

/**
 * NetworkTableEntryStore
 * This is to store all of the Object by the Table Entry Name or via
 *  an alias. Keep in mind that this allows you to store the object
 *  in on variable using the Map without needing to define extra vairables.
 * 
 * @autor Noah Halstead <nhalstead00@gmail.com>
 */
public class NetworkTableEntryStore {

    /**
     * Allows you to create one instance of the call and call it 
     *  later without needing to recall a new instance or svae it.
     * 
     * @var NetworkTableEntryStore
     */
    private static NetworkTableEntryStore constantInstance;

    /**
     * Return the Static Instance of the NetworkTableEntryStore Object.
     * Save System resources and memory by using just one object.
     * 
     * @return NetworkTableEntryStore
     */
    public static NetworkTableEntryStore getInstance(){
        if(NetworkTableEntryStore.constantInstance == null){
            // Make a new Instance with Default
            NetworkTableInstance tableInstance = NetworkTableInstance.getDefault(); // Get the Default Instance.
            NetworkTable table = tableInstance.getTable("SmartDashboard"); // Get the Default Dasboard Store.
            
            // Create and Store the Instance
            NetworkTableEntryStore.constantInstance = new NetworkTableEntryStore(table);
        }
        return NetworkTableEntryStore.constantInstance;
    }


    /**
     * Set the Constant Instance to the Input.
     * 
     * @param ent NetworkTableEntryStore Instance to Save in Static
     */
    public static void setInstance(NetworkTableEntryStore ent){
        NetworkTableEntryStore.constantInstance = ent;
    }

    /**
     * Stores the NetworkTableEntry in an Associative Array
     * 
     * @var Map
     */
    Map<String, NetworkTableEntry> tableEntryIndex = new HashMap<String, NetworkTableEntry>();

    /**
     * Network Table Instance
     * 
     * @var NetworkTable
     */
    private NetworkTable table = null;

    /**
     * Takes the NetworkTable and Allows you to
     *  call Entries from the Network Tables.
     * 
     * @see NetowrkEntryStore
     * @param table NetworkTable
     */
    public NetworkTableEntryStore(NetworkTable table){
        this(table, true);
    }

    /**
     * Takes the NetworkTable and Allows you to
     *  call Entries from the Network Tables.
     * 
     * @param table NetworkTable
     * @param bool Set this new Instnace as the Class Instance
     */
    public NetworkTableEntryStore(NetworkTable table, boolean setClassInstnace){
        // Save the Table to the Local Instance.
        this.table = table;
        NetworkTableEntryStore.constantInstance = this;
    }

    /**
     * Add an Entry to the Index using the Entry Table name.
     * 
     * @param tableEntryName
     */
    public void addToIndex(String tableEntryName){
        this.addToIndex(tableEntryName, tableEntryName);
    }

    /**
     * Add an Entry to the Index using the Entry Table
     *  name as well as adding it via an Alias for easy access.
     * If you want to add an Alias use this otherwise you can `get`
     *  that will add to the index by calling this inline.
     * 
     * @param String Table Entry in the Server
     * @param String Alias for the Entry
     */
    public void addToIndex(String tableEntryName, String aliasName){
        this.tableEntryIndex.put(aliasName, table.getEntry(tableEntryName));
    }

    /**
     * Get Entry from the Index or from 
     * 
     * @param name Network Table Entry for the Server, You can use either an Alias or the Table Name.
     * @return NetworkTableEntry
     */
    public NetworkTableEntry get(String name){
        if(this.tableEntryIndex.get(name) == null){
            this.addToIndex(name);
        }
        return this.tableEntryIndex.get(name);
    }

    /**
     * Remove a Selected Alias or Name and relateed Object from
     *  the index
     * 
     * @param name String Alias or Name to remove from index.
     */
    public void remove(String name){
        if(this.tableEntryIndex.get(name) != null){
            this.tableEntryIndex.remove(name);
        }
    }

    /**
     * Removes all Elements stored in the index.
     * 
     */
    public void clear(){
        this.tableEntryIndex.clear();
    }

    /**
     * Return if the Naame or Alias exists in the Index
     * 
     * @param name Name or Alias
     * @return If the Entry exists in the Index
     */
    public boolean exists(String name){
        return (this.tableEntryIndex.get(name) == null);
    }

}