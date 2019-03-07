package frc.robot.helpers;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PWMSpeedController;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;

/**
 * Allows you to Control the Bosch Seat Motor (am-3564) with the Seat Motor DIO Kit (am-3812).
 * 
 * This class implrenments SpeedController but have some controls that an Encoder 
 *  would have prefixed with encoder followed by the command.
 * encoderGet()       -> Return the Encoder Count
 * encoderDirection() -> Direction of the Counting
 * 
 * Implements SpeedController so you are able to make SpeedController Groups.
 * 
 * @author Noah Halstead <nhalstead00@gmail.com>
 * @link https://wpilib.screenstepslive.com/s/currentCS/m/kop/l/679357-bosch-seat-motor
 * @link https://www.andymark.com/products/bosch-seat-motor-dio-kit
 * @link https://www.andymark.com/products/bosch-seat-motor-and-harness-cable
 */
public class BoschMotor implements SpeedController {

    private PWMSpeedController controller;
    private Counter count;
    private boolean isEncoderInverted = false; // Used to say that encoder direction is the wrong way
    private int position = 0;

    /**
     * Generate instance using the PWM Ouput and the DIO Input
     * 
     * @param PWMInput
     * @param DIOInput
     */
    public BoschMotor(int PWMInput, int DIOInput){
        this(PWMInput, DIOInput, false);
    }
    

    /**
     * Generate instance using the PWM Ouput and the DIO Input
     * 
     * @param PWMInput
     * @param DIOInput
     */
    public BoschMotor(PWMSpeedController PWMInput, int DIOInput){
        this(PWMInput, new DigitalInput(DIOInput), false);
    }

    /**
     * Generate instance using the PWM Output and the DIO Input with
     *  Optinal EncoderInverted Value.
     * 
     * @param PWMInput
     * @param DIOInput
     * @param isEncoderInverted
     */
    public BoschMotor(int PWMInput, int DIOInput, boolean isEncoderInverted){
        this(new Victor(PWMInput), new DigitalInput(DIOInput), isEncoderInverted);
    }

    /**
     * Create new Instace using the PWMSpeedController and the DigitalInput, this
     *  also takes a boolean to Set if the Encoder is Inverted to the Drive Control.
     * 
     * @param controller
     * @param digitalInput
     * @param isEncoderInverted
     */
    public BoschMotor(PWMSpeedController controller, DigitalInput digitalInput, boolean isEncoderInverted){
        this.controller = controller;
        this.count = new Counter(digitalInput);
        this.isEncoderInverted = isEncoderInverted;
    }
    
    /**
     * This function will add the current encoder position on update of the speed.
     * Using this function it will add or subtract based on the direction.
     * If for some reason the Counter is backwards, somehow, you can flip it on init.
     * 
     */
    private void updateDistance(){
        int pending = 0; // Used to Store the Final Value
        int count = this.count.get();
        double speed = this.controller.get();
        this.count.reset();
        
        if (speed > 0) {
            pending = count;
        }
        else if(speed < 0) {
            pending = -count;
        }

        if(this.isEncoderInverted){
            pending = -pending;
        }

        this.position += pending;
    }
    
    /**
     * Update the Encoder Count Index, Return the current Encoder Count.
     *
     * @return Current Encoder Count.
     */
    public double encoderGet() {
        this.updateDistance();
        return this.position;
    }

    /**
     * The last direction the counter value changed.
     *
     * @return The last direction the counter value changed.
     */
    public boolean encoderGetDirection(){
        return this.count.getDirection();
    }

    /**
     * Determine if the clock is stopped. Determine if the clocked input is stopped based on the
     * MaxPeriod value set using the SetMaxPeriod method. If the clock exceeds the MaxPeriod, then the
     * device (and counter) are assumed to be stopped and it returns true.
     *
     * @return true if the most recent counter period exceeds the MaxPeriod value set by SetMaxPeriod.
     */
    public boolean encoderGetStopped(){
        return this.count.getStopped();
    }

    /**
     * Get the Period of the most recent count. Returns the time interval of the most recent count.
     * This can be used for velocity calculations to determine shaft speed.
     *
     * @return The period of the last two pulses in units of seconds.
     */
    public double encoderGetPeriod(){
        return this.count.getPeriod();
    }

    /**
     * Reset the Encoder Value (that is the Counter and Final Position).
     * 
     */
    public void encoderReset(){
        this.count.reset();
        this.position = 0;
    }

    /**
     * Common interface for getting the current set speed of a speed controller.
     *
     * @return The current set speed. Value is between -1.0 and 1.0.
     */
    public double get() {
        return this.controller.get();
    }

    /**
     * Common interface for inverting direction of a speed controller.
     *
     * @param isInverted The state of inversion true is inverted.
     */
    public void setInverted(boolean isInverted){
        this.controller.setInverted(isInverted);
    }

    /**
     * Common interface for returning if a speed controller is in the inverted state or not.
     *
     * @return isInverted The state of the inversion true is inverted.
     */
    public boolean getInverted(){
        return this.controller.getInverted();
    }
    
    /**
     * Common interface for setting the speed of a speed controller.
     *
     * @param speed The speed to set. Value should be between -1.0 and 1.0.
     */
    public void set(Number inValue) {
        this.set(inValue.doubleValue());
    }

    /**
     * Common interface for setting the speed of a speed controller.
     *
     * @param speed The speed to set. Value should be between -1.0 and 1.0.
     */
    public void set(double speed) {
        this.updateDistance();
        this.controller.set(speed);
    }

    /**
     * Disable the speed controller.
     */
    public void disable() {
        this.updateDistance();
        this.controller.disable();
    }
    
    /**
     * Stops motor movement. Motor can be moved again by calling set without
     *  having to re-enable the motor.
     */
    public void stopMotor(){
        this.set(0.0);
        this.controller.stopMotor();
    }

    /**
     * Write out the PID value as seen in the PIDOutput base object.
     *
     * @param output Write out the PWM value as was found in the PIDController
     */
    public void pidWrite(double output){
        this.set(output);
    }

}