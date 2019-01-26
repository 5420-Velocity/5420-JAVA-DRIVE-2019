package frc.robot;

import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Save {

    /**
     * File IO Stream in Java, Allows you to read and write
     *  to the file. Its set to Write Only, With non Blocking.
     * 
     * @var FileWriter
     */
    private FileWriter fileStream;


    /**
     * Allows you to create one instance of the call and call it 
     *  later without needing to recall a new instance or svae it.
     * 
     * @var Save
     */
    private static Save constantInstance;

    /**
     * Return the Static Instance of the Save Object.
     * Save System resources and memory by using just one object.
     * 
     * @return Save
     */
    public static Save getInstance(){
        if(Save.constantInstance == null){
            // Make a new Instance
            Save.constantInstance = new Save();
        }
        return Save.constantInstance;
    }

    /**
     * Constructor Function
     * Build a new Instance of Save to Write Logs to a File.
     */
    public Save(){
        // Check to see if the File Exists.
        // 

        if(Save.constantInstance == null){
            // Save this Instance to the Static Part if it's not set.
            Save.constantInstance = this;
        }
    }

    /**
     * Write string data to File.
     * 
     * This writes all of the log in a base64 so new lines
     *  and other data is written properly for reading.
     * 
     * @param String Log Data
     * @return Save Return the Self Instance to Call Functions back to back.
     */
    public Save write(String... input){
        String buffer = "";
        for (String single : input) {
            buffer += single;
        }

        //String data = "-----\r\n" + buffer + "\r\n-----";
        byte[] message = buffer.getBytes(StandardCharsets.UTF_8);
        String encoded = Base64.getEncoder().encodeToString(message);
        this.writeRaw(encoded);
        return this;
    }

    /**
     * Write string data to File.
     * 
     * 
     * @param String Log Data
     * @return Save Return the Self Instance to Call Functions back to back.
     */
    public Save writeComment(String... input){
        String buffer = "";
        for (String single : input) {
            buffer += single;
        }
        this.writeRaw(buffer);
        return this;
    }

    /**
     * Write string data to File.
     * 
     * 
     * @param String Log Data
     * @return Save Return the Self Instance to Call Functions back to back.
     */
    public Save writeRaw(String... input){
        String buffer = "";
        for (String single : input) {
            buffer += single;
        }

        try {
            String data = buffer.getBytes("UTF-8").toString();
            this.fileStream.write( data );
        }
        catch(Exception e){
            System.out.println("Conversion Error," + e.getMessage());
        }
        return this;
    }

}