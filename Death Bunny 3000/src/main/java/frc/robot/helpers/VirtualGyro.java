package frc.robot.helpers;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.GyroBase;
import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 * Virtual Gyro
 * Implements the Gyro Interface and Extends from GyroBase
 * This class allows you to reset the gyro's angle without any
 *   
 * 
 * @author Noah Halstead <nhalstead00@gmail.com>
 */
public class VirtualGyro extends GyroBase implements Gyro {

    /**
     * Used for the Offset when Resetting the Value
     * 
     * @var double
     */
    private double offset = 0;

    /**
     * 
     * @var Gyro
     */
    private Gyro gyro;

    /**
     * Setup by Using the TalonSRX
     * 
     * @param gyro Gyro Object Instance
     */
    public VirtualGyro(Gyro gyro){
        this(gyro, 0);
    }

    /**
     * Take in the gyro and the starting offset.
     * 
     * @param gyro Gyro Object Instance
     * @param offset
     */
    public VirtualGyro(Gyro gyro, int offset){
        this.gyro = gyro;
        this.offset = offset;
    }

    /**
     * Return the Angle
     * 
     * 
     * @return
     */
    public double getAngle(){
        return this.gyro.getAngle()+this.offset;
    }

    /**
     * Reset this Angle to be Zero
     * This is one of the key parts for the "VirtualGyro"
     * 
     */
    public void reset(){
        this.offset = -this.gyro.getAngle();
    }

    /**
     * Reset the Gyro Settings to Zero.
     * 
     */
    public void calibrate(){

    }

    /**
     * Get the Rate of Change
     * 
     */
    public double getRate(){
        return this.gyro.getRate();
    }

}

