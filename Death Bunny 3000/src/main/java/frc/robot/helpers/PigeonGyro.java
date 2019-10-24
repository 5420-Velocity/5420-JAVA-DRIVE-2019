package frc.robot.helpers;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.GyroBase;
import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 * Make a Pigeon into a Gyro Object
 * Implements the Gyro Interface and Extends from GyroBase
 * 
 * @author Noah Halstead <nhalstead00@gmail.com>
 */
public class PigeonGyro extends GyroBase implements Gyro {

    /**
     * Used for the Offset when Resetting the Value
     * 
     * @var double
     */
    private double offset = 0;

    /**
     * 
     * @var PigeonIMU
     */
    private PigeonIMU pigeon;

    /**
     * Setup by Using the TalonSRX
     * 
     * @param talon What Controller the Pigeon is connected to.
     */
    public PigeonGyro(WPI_TalonSRX talon){
        this(new PigeonIMU(talon));
    }

    /**
     * Setup by Using the TalonSRX
     * 
     * @param CANId Input of the CAN Address for the Pigeon Device.
     */
    public PigeonGyro(int CANId){
        this(new PigeonIMU(CANId));
    }

    /**
     * Generate by the PigeonIMU Object
     * 
     * @param gyro
     */
    public PigeonGyro(PigeonIMU gyro){
        this.pigeon = gyro;
    }

    /**
     * Return all of the Elements of Yaw Pitch Roll
     * 
     * @return
     */
    public double[] getYPR(){
        double[] ypr = new double[3];
        this.pigeon.getYawPitchRoll(ypr);
        return ypr;
    }

    /**
     * Return the Yaw
     * 
     * @return
     */
    public double getYaw(){
        return this.getYPR()[0];
    }

    /**
     * Return the Pitch
     * 
     * @return
     */
    public double getPitch(){
        return this.getYPR()[1];
    }

    /**
     * Return the Roll Value
     * 
     * @return 
     */
    public double getRoll(){
        return this.getYPR()[2];
    }

    /**
     * Return the Yaw
     * Required by gyrobase
     * 
     * @return
     */
    public double getAngle(){
        return this.getYaw()+this.offset;
    }

    /**
     * Reset the Gyro IMU
     * (Nothing Setup for this Setup)
     * 
     */
    public void reset(){
        this.offset = -this.getYaw();
    }

    /**
     * Reset the Gyro Settings to Zero.
     * (Nothing Setup for this Setup)
     * 
     */
    public void calibrate(){

    }

    /**
     * Get the Rate of Change
     * (Nothing Setup for this Setup)
     * 
     */
    public double getRate(){
        return 0;
    }

    /**
     * Get the Pigeon Object
     * 
     * @return
     */
    public PigeonIMU getPigeon(){

        return this.pigeon;
    }

}

