package frc.robot.helpers;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 * Edge
 * This class allows for Edge Case Triggers Only.
 * So this will trigger on cases like Rising Event or Falling Events.
 * 
 * @author Noah Halstead <nhalstead00@gmail.com>
 */
public class Edge {

    private DigitalInput dio;
    private EdgeMode DetectMode;
    private boolean alreadyReturned = false;
    private boolean DETECTB;

    public enum EdgeMode {
        kFalling,
        kRising
    }
    
    public Edge(DigitalInput dio, EdgeMode DetectMode){
        this.dio = dio;
        this.DetectMode = DetectMode;

        if(this.DetectMode == EdgeMode.kFalling){
            this.DETECTB = false;
        }
        else if(this.DetectMode == EdgeMode.kRising ){
            this.DETECTB = true;
        }
    }

    /**
     * 
     * 
     * @return boolean Return True if the Action is a selected DetectMode
     */
    public boolean get(){

        if(dio.get() == this.DETECTB && alreadyReturned == false){
            alreadyReturned = true;
            return true;
        }
        else if(dio.get() == !this.DETECTB){
            alreadyReturned = false;
        }
        else {
            return false;
        }

        return false;
    }

}