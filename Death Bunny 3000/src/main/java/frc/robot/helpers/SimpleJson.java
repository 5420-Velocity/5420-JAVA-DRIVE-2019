package frc.robot.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Json Encoder
 * This uses some of the standards that were set in the
 *  Json RFC7159 Guidelines while be as simple as possible.
 * 
 * Supported Datatypes:
 *  Double
 *  Float
 *  Integer
 *  Long
 *  String
 *  Boolean
 *  Exception
 *  ArrayList
 *  Json Instance
 * 
 * Compliant with: RFC7159
 * 
 * Use push to add values to the Map internally.
 * 
 * @author Noah Halstead <nhalstead00@gmail.com>
 */
public class SimpleJson {

    /**
     * Stores the Data to Write
     * 
     * @var write
     */
    protected Map<String, String> write;

    /**
     * Stores the Type of data stored in Write
     * 
     * @var writeType
     */
    protected Map<String, Type> writeType;

    /**
     * Is the Output an Associative Array
     * 
     * @var isAssociativeArray
     */
    private Boolean isAssociativeArray;

    /**
     * Data Type Stored in Vars
     * 
     */
    public enum Type {
        Null,
        Raw,
        Enum,
        String,
        Boolean,
        Number
    }

    /**
     * Init as Associative Mode
     * 
     */
    public SimpleJson(){
        this(true);
    }

    /**
     * Init with or With/Without Associative Mode
     * 
     * @param isAssociative Should an Index be Kept and Applied on output.
     */
    public SimpleJson(boolean isAssociative){
    	this.isAssociativeArray = isAssociative;
        this.clear();
    }

    /**
     * Fix String Input for JSON Standard
     * 
     * @param value String Value
     * @return HTML Ready String for JSON
     */
    public static String stringString(String value){
        
        /**
         * Escape Items in String for proper Json Format
         * 
         * @link https://stackoverflow.com/a/50522874/5779200
         */
        value = value.replace("\\", "\\\\");
        value = value.replace("\"", "\\\"");
        value = value.replace("\b", "\\b");
        value = value.replace("\f", "\\f");
        value = value.replace("\n", "\\n");
        value = value.replace("\r", "\\r");
        value = value.replace("\t", "\\t");

        return value;
    }

    /**
     * Overwrite the Current Values in the Object,
     *  Clearing the Stored Data in the Class.
     * 
     */
    public void clear(){
        this.write = new HashMap<String, String>();
        this.writeType = new HashMap<String, Type>();
    }
    
    /**
     * Delete the Key and value Supplied
     * 
     * @param key String
     */
    public void remove(String key){

        this.write.remove(key);
        this.writeType.remove(key);
    }

    /**
     * Set the value to Null, Keep in mind
     *  that null as a value in Json is not
     *  widely adopted despite the RFC7159.
     * 
     * @param key String
     */
    public void nullify(String key){

        this.push(key, "null", Type.Raw);
    }

    /**
     * Write Enum Name to Write Buffer, Push by Random Key
     *  should be used when isAssociativeArray == false
     * 
     * @param value Enum Value
     */
    public void push(Enum<?> value){
        this.push(this.getNextId(), value);
    }

    /**
     * Write Enum Name to Write Buffer
     * 
     * @param key Key Value
     * @param value Enum Value
     */
    public void push(String key, Enum<?> value){

        String buffer = value.getClass() + "." + value.name();
        this.push(key, buffer, Type.Enum);
    }

    /**
     * Write Exception to Write Buffer, Push by Random Key
     *  should be used when isAssociativeArray == false
     * 
     * @param value Exception Value
     */
    public void push(Exception value){
    	push(this.getNextId(), value);
    }

    /**
     * Write Exception to Write Buffer
     * 
     * @param key Key Value
     * @param value Exception value
     */
    public void push(String key, Exception value){
    	push(key, value.getMessage());
    }

    /**
     * Write Value to Write Buffer, Push by Random Key
     *  should be used when isAssociativeArray == false
     * 
     * @param value String
     */
    public void push(String value){
        this.push(this.getNextId(), value);
    }

    /**
     * Write Value to Write Buffer
     * 
     * @param key Key Value
     * @param value String Value
     */
    public void push(String key, String value){

        this.push(key, value, Type.String);
    }

    /**
     * Write Value to Write Buffer, Push by Random Key
     *  should be used when isAssociativeArray == false
     * 
     * @param value Integer Value
     */
    public void push(Integer value){
        this.push(this.getNextId(), value);
    }

    /**
     * Write Value to Write Buffer
     * 
     * @param key Key Value
     * @param value Integer Value
     */
    public void push(String key, Integer value){

        this.push(key, String.valueOf(value), Type.Number);
    }

    /**
     * Write Value to Write Buffer, Push by Random Key
     *  should be used when isAssociativeArray == false
     * 
     * @param value Double Value
     */
    public void push(Double value){
        this.push(this.getNextId(), value);
    }

    /**
     * Write Value to Write Buffer
     * 
     * @param key Key Value
     * @param value Double Value
     */
    public void push(String key, Double value){

        this.push(key, String.valueOf(value), Type.Number);
    }

    /**
     * Write Value to Write Buffer, Push by Random Key
     *  should be used when isAssociativeArray == false
     * 
     * @param value Float Value
     */
    public void push(Float value){
        this.push(this.getNextId(), value);
    }

    /**
     * Write Value to Write Buffer
     * 
     * @param key Key Value
     * @param value Float Value
     */
    public void push(String key, Float value){

        this.push(key, String.valueOf(value), Type.Number);
    }

    /**
     * Write Value to Write Buffer, Push by Random Key
     *  should be used when isAssociativeArray == false
     * 
     * @param value long Value
     */
    public void push(long value){
        this.push(this.getNextId(), value);
    } 

    /**
     * Write Value to Write Buffer
     * 
     * @param key Key Value
     * @param value long Value
     */
    public void push(String key, long value){

        this.push(key, String.valueOf(value), Type.Number);
    }

    /**
     * Write Value to Write Buffer, Push by Random Key
     *  should be used when isAssociativeArray == false
     * 
     * @param value Boolean Value
     */
    public void push(Boolean value){
        this.push(this.getNextId(), value);
    }

    /**
     * Write Value to Write Buffer
     * 
     * @param key Key Value
     * @param value Boolean Value
     */
    public void push(String key, Boolean value){

        this.push(key, value?"true":"false", Type.Boolean);
    }

    /**
     * Write Value to Write Buffer, Push by Random Key
     *  should be used when isAssociativeArray == false
     * 
     * @param value SimpleJson
     */
    public void push(SimpleJson value){
        this.push(this.getNextId(), value);
    }

    /**
     * Write Value to Write Buffer from a Json Object
     * 
     * @param key String
     * @param value SimpleJson
     */
    public void push(String key, SimpleJson value){
        if(value == this) {
        	// Prevent Recursion Issues
        	return;
        }
        
        if(value.toString() == "") {
        	// Do not allow nothing to be returned
        	return;
        }

        this.push(key, value.toString(), Type.Raw);
    }

    
    /**
     * Write Value to Write Buffer, Push by Random Key
     *  should be used when isAssociativeArray == false
     * 
     * @param value ArrayList Value
     */
    public void push(ArrayList<?> value){
    	push(this.getNextId(), value);
    }

    /**
     * Write ArrayList to Write Buffer
     * 
     * Supported Datatype
     *  String
     *  Character
     *  Integer
     *  Double
     *  Float
     *  Long
     *  Boolean
     *  ! Enum
     *  ! Class
     * 
     * @param key Key Value
     * @param value ArrayList Value
     */
    public void push(String key, ArrayList<?> value){
        StringBuilder buffer = new StringBuilder("[");

        for(Object v : value){
            if(buffer.toString() != "["){
                buffer.append(",");
            }

            if(v instanceof String){
                buffer.append("\"").append(SimpleJson.stringString(String.valueOf(v))).append("\"");
            }
            else if(v instanceof Character){
                buffer.append((String) v);
            }
            else if(v instanceof Integer){
                buffer.append(String.valueOf((Integer) v));
            }
            else if(v instanceof Double){
                buffer.append(String.valueOf((Double) v));
            }
            else if(v instanceof Float){
                buffer.append(String.valueOf((Float) v));
            }
            else if(v instanceof Long){
                buffer.append(String.valueOf((Long) v));
            }
            else if(v instanceof Boolean){
                buffer.append((Boolean) v ? "true" : "false");
            }
            else if(v instanceof Enum){
                // Need to figure out a way to do this.
                //  Convert Object v, Cast as Enum, Get Enum Name
            }
            else if(v instanceof Class){
                System.out.println("[SimpleJson] Can not take type: Class -> " + ((Class<?>)v.getClass()).getName());
                buffer.append("null");
            }
            else {
                // No Data Type Registered for the Input
                System.out.println("[SimpleJson] Can not take type: " + v.toString());
                buffer.append("null");
            }
        }

        buffer.append("]");

        this.push(key, buffer.toString(), Type.Raw);
    }

    /**
     * Insert Data to the Internal Storage
     * All push Functions use this to add data to the Maps.
     * 
     * @param key Key Value
     * @param value String Value
     * @param t Type of Value Insert
     */
    protected void push(String key, String value, Type t){

        this.write.put(key, String.valueOf(value));
        this.writeType.put(key, t);
    }

    /**
     * Return the JSON Object as a String, In JSON Notation.
     * 
     * @return String Version of the Value Store
     */
    public String toString(){
        StringBuilder buffer = new StringBuilder();

        for (Map.Entry<String, String> entry : this.write.entrySet()) {
            
            if(writeType.get(entry.getKey()) == null){
                // Skip this iteration for processing, No Datatype
                continue;
            }
            
            if(buffer.toString() != ""){
                // Add Comma  
                buffer.append(",");
            }

            if(this.isAssociativeArray == true){
                // Write Key Value
                buffer.append("\"").append(entry.getKey()).append("\":");
            }

            if(writeType.get(entry.getKey()) == Type.Number){
                // Write as Plain Text (Number)
                buffer.append(entry.getValue());
            }
            else if(writeType.get(entry.getKey()) == Type.Raw){
                // Write what is given in Directly Out.
                buffer.append(entry.getValue());
            }
            else if(writeType.get(entry.getKey()) == Type.Enum){
                // Write what is given in Directly Out.
                buffer.append(entry.getValue());
            }
            else if(writeType.get(entry.getKey()) == Type.Boolean){
                // Write what is given in Directly Out.
                buffer.append(entry.getValue());
            }
            else if(writeType.get(entry.getKey()) == Type.String){
                // Write String
            	
            	String value = entry.getValue();
                
                SimpleJson.stringString(value);

                // Puts string in Value for JSON wrapped in Quotes
                buffer.append("\"").append(value).append("\"");
            }
        }

        if(buffer.toString() != "") {
        	if(this.isAssociativeArray == false){
                return "["+buffer+"]";
            }
            else {
            	return "{"+buffer+"}";
            }
        }
        else {
        	return "";
        }
    }

    /**
     * Used to Get an ID that is next in Line.
     * Used when making a NON-Associative Array.
     * 
     * @return Next Index Id for the Array
     */
    private String getNextId(){
        return String.valueOf(this.write.size()+1);
    }

}