package frc.robot.helpers;

public class Toggle {

    private boolean state = false;

    public Toggle(){

    }

    /**
     * Get
     * 
     * @return Return the Stored State
     */
    public boolean get(){
        return this.state;
    }

    /**
     * Flips the Value in Store
     * 
     */
    public void flip(){
        this.state = !this.state;
    }

    /**
     * New State
     * 
     * @param v 
     */
    public void set(boolean v){
        this.state = v;
    }

}