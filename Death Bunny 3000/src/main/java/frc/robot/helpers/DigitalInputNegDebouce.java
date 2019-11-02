package frc.robot.helpers;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 * DigitalInputNegDebouce
 * 
 * DigitalInput will return if the DIO is true or false if the value is connected.
 * With a Debouce it will detect the first True value return false otherwise till
 *  a set timeout has been reached then another true value can be returned.
 * 
 * This this a Negative Debouce, This will return the Value after a set amount
 *  of Valid Values in the Cycles. This is best served in a procedural env as it
 *  looks at cycles.
 * 
 */
public class DigitalInputNegDebouce extends DigitalInput {

    private int targetCycles = 1;
    private int itereFalse = 0;
    private int itereTrue = 0; // Iteration, Current Valid Cycle

    /**
     * DigitalInputNegDebouce
     * Default 50 Cycles, 1 Seconds
     * 
     * @param channel     DIO Port
     */
    public DigitalInputNegDebouce(int channel) {
        this(channel, 50);
    }

    /**
     * DigitalInputNegDebouce
     * 
     * @param channel       DIO Port
     * @param targetCycles  The total Cycles, One Cycle is about every 20ms. (50 calls per second)
     */
    public DigitalInputNegDebouce(int channel, int targetCycles) {
        super(channel);

        // Check to make sure its not negative or Zero.
        if(Math.abs(targetCycles) != targetCycles || targetCycles == 0) {
            this.targetCycles = 1;
        }
        else {
            this.targetCycles = targetCycles;
        }
    }

    /**
     * This will return the value being true once the input is true for x cycles.
     * 
     * @return the status of the digital input
     */
    @Override
    public boolean get() {
        boolean superValue = super.get();

        if(superValue == true) {
            // Value is True, Add to the Itere
            this.itereTrue++;
        }
        else if(superValue == false) {
            if(this.itereFalse <= 10) {
                // Value is False, Less than 10, Add to the Itere
                this.itereFalse++;
            }
            else {
                // Have 10 Consecutive False Values (200ms)
                // Value Must be false.
                this.itereFalse = 0;
                this.itereTrue = 0;
                return false;
            }
        }

        // Return TRUE if the Itere of True Values are past the target cycles.
        if(this.itereTrue >= this.targetCycles) {
            // Has Equaled or has Passed the Valid Cycles
            this.itereTrue = this.targetCycles;
            return true;
        }
        return false;
    }

}