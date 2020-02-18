package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

public class Registry {

    private Member member;
    private Map<Long, Member> memberCollection;

    // This class in module Model handles everything related to the model part of the program
    // in order to separate modules. This way, a Controller class doesn't have to worry about a changing model. As long
    // as the Controller can manage the flow part then Model can change as much
    // as it wants, but the Controller doesn't have to change.

    public Registry() {
        memberCollection = new HashMap<>();

        // Upon starting the program, load the members from persistence data.
        loadFromFile();
    }

    public boolean createMember(String name, long pN) {
        // Returns true if created member is found in collection, after adding it there.

        Member member = new Member(name, pN);
        memberCollection.put(pN, member); // store a member object in a map with the pN as key.
        writeToFile();

        return memberCollection.containsValue(member);
    }

    public Member getMember(long pN) {
        return memberCollection.get(pN);
    }

    public boolean memberExists(long pN) {
        return memberCollection.containsKey(pN);
    }

    public boolean updateMemberName(long pN, String name) {
        member = getMember(pN);
        member.setName(name);
        writeToFile();
        return true;
    }

    public boolean updateMemberPNum(long pN, long newPN) {
        member = getMember(pN);
        if (memberExists(newPN))
            return false;
        member.setPersonalNumber(newPN);
        writeToFile();
        return true;
    }

    public boolean removeMember(long pN) {
        Member removedMember = memberCollection.remove(pN);
        writeToFile();
        return (removedMember != null);
    }

    public boolean registerBoat(int model, int length, long memberID) {
        memberCollection.get(memberID).registerBoat(model,length);
        writeToFile();

        return true;
    }

    public boolean updateBoatType(int boatID, int model, long pN) {
        memberCollection.get(pN).getBoat(boatID).setType(model);
        writeToFile();
        return true;
    }

    public boolean updateBoatLength(int boatID, int length, long pN) {
        memberCollection.get(pN).getBoat(boatID).setLength(length);
        writeToFile();
        return true;
    }

    public boolean removeBoat(int boatID, long pN) {
        Boolean returnBool = memberCollection.get(pN).removeBoat(boatID);
        writeToFile();
        return returnBool;
    }

    public Iterator<Boat> getBoatList(long pN) {
        return getMember(pN).getBoatList();
    }

    public List<Member> getMembersList() {

        // This copies the current memberlist and places it into a new list. The base collection
        // stays unaltered this way. (I can now use lists in my program; i have found iterators to be a pain).
        List<Member> members = new ArrayList<>();
        memberCollection.forEach((pNum, member) -> {
            Member copiedMember = member.copy();
            members.add(copiedMember);
        });
        return members;
    }


    // >>>>>>>>>>>>> Persistence part

    private void writeToFile() {
        try (Writer writer = new FileWriter("Registry.json")) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();

            gson.toJson(memberCollection, writer);
        }
        catch (IOException e) {
            System.err.println("Failed to write JSON");
        }
    }

    private void loadFromFile() {
        if (new File("Registry.json").exists()) {
            try (Reader reader = new BufferedReader(new FileReader("Registry.json"))) {

                Gson gson = new Gson();
                memberCollection = gson.fromJson(reader, new TypeToken<HashMap<Long, Member>>() {
                }.getType());

            } catch (IOException f) {
                f.printStackTrace();
            }
        }
    }
}
