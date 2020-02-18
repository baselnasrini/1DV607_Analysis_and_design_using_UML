package controller;

import model.Boat;
import model.Registry;
import model.searchrules.Criteria;
import model.searchrules.SearchBirthMonthCriteria;
import model.searchrules.SearchNamePhraseCriteria;
import view.Console;

import java.util.HashMap;
import java.util.List;

// Controller asks view to present stuff, or get input. Based on that input, do stuff here.
// "Control the program flow"

public class Controller {
    private Console c_view;
    private Registry registry;
    private static boolean isAuthenticated;
    private HashMap<String, String> authUsers;

    public boolean startProgram(Console c_view, Registry registry) {

        this.c_view = c_view;
        this.registry = registry;
        this.authUsers = new HashMap<>();

        // Authentication 
        authUsers.put("admin", "admin");

        // Show introduction text:
        c_view.showWelcomeMessage();
        c_view.printMenuActions();

        return getMenuAction();
    }

    private boolean getMenuAction() {
        int answer = c_view.getMenuAction();

        switch (answer) {
            case 1: return createMember();
            case 2: return retrieveMember();
            case 3: return updateMember();
            case 4: return removeMember();
            case 5: return listMembers();
            case 6: return registerBoat();
            case 7: return updateBoatInformation();
            case 8: return removeBoat();
            case 9: return listBoats();
            case 10: return search();
        }
        return true;
    }

    private boolean createMember() {
        if (!isAuthenticated) {
            authenticate();
            return createMember();
        }

        // Ask user for name and personal number
        // Let Registry handle the creation of a member object.
        c_view.showMemberInstructions();

        String name = c_view.getMemberName();
        long pNum = c_view.getMemberPersonalNumber();

        if (registry.memberExists(pNum))
            c_view.errorMemberExists();
        else
            // Return value is true if member is successfully added to registry.
            return registry.createMember(name, pNum);
        return false;
    }


    private boolean retrieveMember() {
        long pN = c_view.getMemberPersonalNumber();

        if (registry.memberExists(pN)) {
            c_view.displayMember(registry.getMember(pN));
        } else
            c_view.errorMemberDoesNotExist();
        return true;
    }

    private boolean updateMember() {
        if (!isAuthenticated) {
            authenticate();
            return updateMember();
        }
        long pNum = c_view.getMemberPersonalNumber();

        c_view.displayMember(registry.getMember(pNum));
        int whatToUpdate = c_view.updateMemberInstructions(); // 1 to update name, 2 to update pNum

        if (whatToUpdate == 1) {
            String name = c_view.getMemberName();
            return registry.updateMemberName(pNum, name);
        } else {
            long newPN = c_view.getMemberPersonalNumber();
            return registry.updateMemberPNum(pNum, newPN);
        }
    }

    private boolean removeMember() {
        if (!isAuthenticated) {
            authenticate();
            return removeMember();
        }

        c_view.removeMemberInstructions();
        long pNum = c_view.getMemberPersonalNumber();

        return c_view.areYouSure() && registry.removeMember(pNum);
    }

    private boolean listMembers() {
        int answer = c_view.getListPreference();
        c_view.displayMembers(registry.getMembersList(), (answer == 1));
        return true;
    }

    private boolean registerBoat() {
        if (!isAuthenticated) {
            authenticate();
            return registerBoat();
        }
        long pNum = c_view.getMemberPersonalNumber();

        int model = c_view.getBoatModel();
        int length = c_view.getBoatLength();

        return registry.registerBoat(model, length, pNum);
    }

    private boolean updateBoatInformation() {
        if (!isAuthenticated) {
            authenticate();
            return updateBoatInformation();
        }

        c_view.updateBoatInfoInstructions();
        long pNum = c_view.getMemberPersonalNumber();

        int boatID = c_view.getBoatSelection(registry.getBoatList(pNum)) - 1;

        int whatToUpdate = c_view.updateBoatInfoChoice();
        if (whatToUpdate == 1) {
            int model = c_view.getBoatModel();
            return registry.updateBoatType(boatID, model, pNum);
        } else {
            int length = c_view.getBoatLength();
            return registry.updateBoatLength(boatID, length, pNum);
        }
    }

    private boolean removeBoat() {
        if (!isAuthenticated) {
            authenticate();
            return removeBoat();
        }

        c_view.removeBoatInstructions();

        long pNum = c_view.getMemberPersonalNumber();

        int boatID = c_view.getBoatSelection(registry.getBoatList(pNum)) - 1;

        return c_view.areYouSure() && registry.removeBoat(boatID, pNum);
    }

    private boolean listBoats() {
        long pNum = c_view.getMemberPersonalNumber();
        c_view.displayBoats(registry.getBoatList(pNum));
        return true;
    }

    private boolean authenticate() {
        String userLogin = c_view.getUserLogin();
        if (!authUsers.containsKey(userLogin)) {
            c_view.displayUserNotExist();
            return authenticate();
        }
        String userPass = c_view.getUserPass();

        if (this.authUsers.containsKey(userLogin)) {
            boolean response = authUsers.get(userLogin).equals(userPass);
            if (response) {
                isAuthenticated = true;
                return true;
            }
            c_view.displayPassIncorrect();
        }
        return false;
    }

    private boolean search() {
        int n = c_view.getSearchOption();
        switch (n) {
            case 1:
                Criteria searchNamePhraseCriteria = new SearchNamePhraseCriteria(c_view.getSearchPhrase());
                c_view.displayMembers(searchNamePhraseCriteria.meets(registry.getMembersList()),true);
                break;
            case 2:
                Criteria searchBirthMonthCriteria = new SearchBirthMonthCriteria(c_view.getBirthMonthSearch());
                c_view.displayMembers(searchBirthMonthCriteria.meets(registry.getMembersList()), true);
        }
        return true;
    }
}
