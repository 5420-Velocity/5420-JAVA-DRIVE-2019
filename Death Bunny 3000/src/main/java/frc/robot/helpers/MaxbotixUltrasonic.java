package frc.robot.helpers;

import edu.wpi.first.wpilibj.AnalogInput;

/**
 * MaxbotixUltrasonic
 * Converts the Input to Inches in the Analog Input
 * 
 * @link https://www.chiefdelphi.com/t/java-maxbotix-ultrasonic-sensor-code/102578
 * @author mobilegamer999
 * @author Noah Halstead <nhalstead00@gmail.com>
 */
public class MaxbotixUltrasonic {

    private final double IN_TO_CM_CONVERSION = 2.54;
    private boolean use_units;     // Are we using units or just returning voltage?
    private double min_voltage;	   // Minimum voltage the ultrasonic sensor can return
    private double voltage_range;  // The range of the voltages returned by the sensor (maximum - minimum)
    private double min_distance;   // Minimum distance the ultrasonic sensor can return in inches
    private double distance_range; // The range of the distances returned by this class in inches (maximum - minimum)
    private AnalogInput channel;
    
    /**
     * Init using just the Given Channel
     * 
     * @param _channel Select the Analog Channel to use
     */
    public MaxbotixUltrasonic(int _channel) {
        channel = new AnalogInput(_channel);

        // Default values
		this.use_units = true;
		this.min_voltage = .5;
		this.voltage_range = 5.0 - min_voltage;
		this.min_distance = 3.0;
        this.distance_range = 60.0 - min_distance;
    }
    
    /**
     * Init using extra Params
     * 
     * @param _channel Select the Analog Channel to use
     * @param _use_units If the value returned should be
     * @param _min_voltage Min Voltage of the Sensor
     * @param _max_voltage Max Voltage of the Sensor
     * @param _min_distance Mix Value of the Sensor
     * @param _max_distance Max Value of the Sensor
     */
    public MaxbotixUltrasonic(int _channel, boolean _use_units, double _min_voltage, double _max_voltage, double _min_distance, double _max_distance) {
        channel = new AnalogInput(_channel);

        // Only use unit-specific variables if we're using units
        if (_use_units) {
            use_units = true;
            min_voltage = _min_voltage;
            voltage_range = _max_voltage - _min_voltage;
            min_distance = _min_distance;
            distance_range = _max_distance - _min_distance;
        }
    }
    
    /**
     * Return the Voltage Value that is from the Analog Input
     * 
     * @return Voltage
     */
    public double GetVoltage() {
        return channel.getVoltage();
    }

    /**
     * getRangeInches
     * 
     * Returns the range in inches
     * Returns -1.0 if 
     * units are not being used
     * Returns -2.0 if the voltage is below the minimum voltage
     * 
     * @return Range in Inches
     */
    public double getRangeInches() {
        double range;
        // If we're not using units, return -1, a range that will most likely never be returned
        if(!use_units) {
            return -1.0;
        }

        range = channel.getVoltage();
        if(range < min_voltage) {
            return -2.0;
        }

        // First, Normalize the voltage
        range = (range - min_voltage) / voltage_range;
        // Next, Denormalize to the unit range
        range = (range * distance_range) + min_distance;

        return range;
    }

    /**
     * getRangeCm
     * 
     * Returns the range in centimeters
     * Returns -1.0 if units are not being used
     * Returns -2.0 if the voltage is below the minimum voltage
     * 
     * @return Return range in cm
     */
    public double getRangeCm() {
        double range;
        // If we're not using units, return -1, a range that will most likely never be returned
        if (!use_units) {
            return -1.0;
        }

        range = channel.getVoltage();
        if (range < min_voltage) {
            return -2.0;
        }

        // First, Normalize the voltage
        range = (range - min_voltage) / voltage_range;
        // Next, Denormalize to the unit range
        range = (range * distance_range) + min_distance;
        // Finally, Convert to centimeters
        range *= IN_TO_CM_CONVERSION;

        return range;
    }
}
