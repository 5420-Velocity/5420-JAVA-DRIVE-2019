package frc.robot.exceptions;


public class NotDefinedException extends Exception {

    private static final long serialVersionUID = 7718828512143293558L;

    // Parameterless Constructor
    public NotDefinedException() {}

    public NotDefinedException (String message) {
        super (message);
    }

    public NotDefinedException (Throwable cause) {
        super (cause);
    }

    public NotDefinedException (String message, Throwable cause) {
        super (message, cause);
    }

}