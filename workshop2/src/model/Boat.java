package model;

public class Boat {

    private long ownerPersonalNumber;
    private int boatID;
    private int length;
    private BoatType type;

    public enum BoatType {
        SAILBOAT, MOTORSAILER, KAYAK, OTHER;

        BoatType() { }
    }

    Boat () {
    }

    Boat(int type, int length, long ownerPersonalNumber ) {
        this.ownerPersonalNumber = ownerPersonalNumber;
        this.length = length; //length in centimeters
        this.type = BoatType.values()[type];
    }
    
    public int getLength(){
    	return length ;
    }
    
    public BoatType getType(){
    	return type ;
    }

    void setLength (int length){
    	this.length = length ;
    }
    
    void setType (int n){
    	this.type = BoatType.values()[n];
    }

    @Override
    public String toString() {
        return this.type + " , " + this.length;
    }
    
}
