package frc.robot.helpers;

/**
 * RobotOrientation
 * This class is to Flip values as the Robot is Orientated,
 *  This could be easily done but this allows for some extra
 *  flexibility.
 * 
 * @author Noah Halstead <nhalstead00@gmail.com>
 */
public class RobotOrientation {

    public Side directionSide = RobotOrientation.Side.kSideA;

    public Boolean sentOnce = false;

    public enum Side {
        kSideA, // Front Side on Start
        kSideB,  // Back Side
        kNone // Sent Once
    }

    /**
     * Allows you to create one instance of the call and call it 
     *  later without needing to recall a new instance or save it.
     * 
     * @var Save
     */
    private static RobotOrientation constantInstance;

    /**
     * Return the Static Instance of the Save Object.
     * Save System resources and memory by using just one object.
     * 
     * @return RobotOrientation
     */
    public static RobotOrientation getInstance(){
        if(RobotOrientation.constantInstance == null){
            // Make a new Instance
            RobotOrientation.constantInstance = new RobotOrientation();
        }
        return RobotOrientation.constantInstance;
    }

    /**
     * Return the Static Instance of the Save Object.
     * Save System resources and memory by using just one object.
     * 
     * @param in
     */
    public static void setInstance(RobotOrientation in){
        RobotOrientation.constantInstance = in;
    }
   
    /**
     * Set a new Side for the Robot
     * 
     * @param newSide
     */
    public void setSide(Side newSide){
        this.sentOnce = false;
        this.directionSide = newSide;
    }

    /**
     * Flip the Side and Return the New Side.
     * 
     * @return
     */
    public Side flipSide(){
        if(directionSide == Side.kSideA){
            this.setSide(Side.kSideB);
        }
        else{
            this.setSide(Side.kSideA);
        }

        return this.directionSide;
    }

    /**
     * Get the Current State
     * 
     * @return
     */
    public Side getSide(){
        return this.directionSide;
    }

    /**
     * Returns the Side Once
     */
    public Side getSideOnce(){
        if(this.sentOnce == false){
            this.sentOnce = true;
            return this.directionSide;
        }
        return RobotOrientation.Side.kNone;
    }

    /**
     * Return the Value if the Side is as Needed.
     * Otherwise Return the Invert of `valueIn`
     * 
     * @param valueIn
     * @param normalSide
     * @return
     */
    public double fix(double valueIn, Side normalSide){
        if(normalSide != this.directionSide){
            return -valueIn;
        }
        return valueIn;
    }

}