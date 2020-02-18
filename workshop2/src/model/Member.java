package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Member {
    private String name;
    private long idNumber;
    private long personalNumber;
    private ArrayList<Boat> boats = new ArrayList<>();

    public Member() {
    }

    public Member(String name, long personalNumber) {
        this.setName(name);
        this.setIdNumber(name, personalNumber);
        this.setPersonalNumber(personalNumber);
    }

    public Boat getBoat(int boatID) { // BoatID is the position in the Boat ArrayList. Should also be used in front end
        return boats.get(boatID);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String name, long personalNumber) {
        int hC = name.hashCode();
        hC %= 8192;
        hC = (hC < 0 ? -hC : hC); //make hash positive
        hC += 1000; // ID always length 4;
        this.idNumber = Long.parseLong(String.valueOf(personalNumber) + String.valueOf(hC));
    }

    public long getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(long personalNumber) {
        this.personalNumber = personalNumber;
    }

    public Iterator<Boat> getBoatList() {
        return this.boats.iterator();
    }

    private void setBoats(ArrayList<Boat> boats) {
        this.boats = boats;
    }

    public int getNumberOfBoats() {
        return this.boats.size();
    }

    @Override
    public String toString() {
        return "Name:\t" + this.getName() + ", " + "Personal Number:\t" +this.getPersonalNumber();
    }

    public boolean hasBoats() {
        return this.boats.size() != 0;
    }

    boolean registerBoat(int model, int length) {
        Boat boat = new Boat(model,length,this.personalNumber);
        return boats.add(boat);
    }

    boolean removeBoat(int boatID) {
        Boat removedBoat = boats.remove(boatID);
        return (removedBoat != null);
    }

    public Member copy() {
        Member returnMember = new Member(this.getName(),this.getPersonalNumber());
        returnMember.setBoats(this.boats);

        return returnMember;
    }
}