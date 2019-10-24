package frc.robot;

public class Constants {

    // All Measurements in Inches unless specified otherwise

    public static int encTickPerRev = 4096;

    public static final double wheelDiameterIn = 6.0;
    public static final double wheelDiameterMetres = 0.1524;
    public static final double wheelCircumferenceIn = Math.PI * wheelDiameterIn;
    public static final double wheelCircumferenceMetres = Math.PI * wheelDiameterMetres;

    public static final double WHEEL_DIAMETER = 6.0;
	public static final double WHEEL_CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;
	public static final double TICS_PER_ROTATION = 256; // GaryHill 1847 - 63R256
	public static final double TICS_PER_INCH = TICS_PER_ROTATION / WHEEL_CIRCUMFERENCE;
	
	public static final double ROBOT_WIDTH = 25.3125;
	public static final double ROBOT_CIRCUMFERENCE = Math.PI * ROBOT_WIDTH;
	public static final double TICS_PER_DEGREE = (ROBOT_CIRCUMFERENCE * TICS_PER_INCH)/360.0;
	
	public static final double TOP_ELEVATOR_TICS = 27000;
	public static final double TOP_ELEVATOR_HEIGHT = 73;
    public static final double TICS_PER_ELEVATOR_INCH = TOP_ELEVATOR_TICS/TOP_ELEVATOR_HEIGHT;
    

    public static final double frameWidthIn = 23.25;
    public static final double frameWidthFt = frameWidthIn / 12.0;
    public static final double frameLengthIn = 32.2;

    public static final double PATH_MAX_SPEED = 1.75;

    public static final int maxDrivetrainVelocity = 3155;

    //public static final double drivetrainMultiplier = 1.93; //GOES PERFECT WHILE DRIVING 5 FT
    public static final double drivetrainMultiplier = 1.3;

}