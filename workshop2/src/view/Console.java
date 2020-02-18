package view;

import model.Boat;
import model.Member;

import java.util.*;


public class Console {

    public Console() {}

    public enum Option {
        CREATEMEMBER(1, "Create member"),
        RETRIEVEMEMBER(2, "Retrieve member"),
        UPDATEMEMBER(3, "Update member"),
        DELETEMEMBER(4, "Remove member"),
        LISTMEMBERS(5, "List members"),
        REGISTERBOAT(6, "Register boat"),
        UPDATEBOATINFO(7, "Update boat info"),
        DELETEBOAT(8, "Delete boat"),
        LISTBOATS(9, "List boats"),
        SEARCH(10, "Search");

        private int value;
        private String question;

        Option(int value, String question) {
            this.value = value;
            this.question = question;
        }

        public String getQuestion() {
            return this.question;
        }

        public int getValue() {
            return value;
        }

    }

    public int getMenuAction() {
        Scanner reader;
        int chosenAction;

        while (true) {
            reader = new Scanner(System.in);
            String line = reader.nextLine();

            if (line.equals("quit"))
                System.exit(1);

            try {
                chosenAction = Integer.valueOf(line);
                return chosenAction;
            } catch (NumberFormatException n) {
                System.out.println("\nNot a number, please enter the option number\n");
            }
        }
    }

    public void printMenuActions() {
        for (Option option : Option.values()) {
            System.out.println(option.getValue() + ") " + option.getQuestion());
        }
    }

    public void showWelcomeMessage() {
        System.out.println("This is a registry for the Yacht Club.");
        System.out.println("To quit this program, type 'quit' and press enter.\n");
    }

    public int getListPreference() {
        System.out.println("1) Verbose, 2) Compact");
        return getIntegerInput(1, 2);
    }

    private int getIntegerInput(int min, int max) {
        Scanner in = new Scanner(System.in);
        while (true) {
            try {
                int n = in.nextInt();
                if (n >= min && n <= max) {
                    return n;
                }
                System.out.println("That number is not possible. Try again");
            } catch (InputMismatchException e) {
                System.out.println("That is not a number. Try again");
            }
        }
    }

    public boolean areYouSure() {
        System.out.println("Are you sure? Type 'Y' for yes, 'N' for no:");

        Scanner scanner = new Scanner(System.in);
        String answer = scanner.next();
        while (true) {
            if (answer.toLowerCase().equals("y") || answer.toLowerCase().equals("n")) {
                return (answer.toLowerCase().equals("y"));
            }
            System.out.println("That wasn't a Y or N! Try again");
        }
    }

    /*
    >>>>>> MEMBER
     */

    public void displayMember(Member member) {
        System.out.printf("Name: %s,  Personal number: %d,  MemberID: %d%n",member.getName(), member.getPersonalNumber(), member.getIdNumber());
        if (member.hasBoats()) {
            displayBoats(member.getBoatList());
        }
        System.out.println();
    }

    public void displayMembers (List<Member> memberCollection, Boolean verbose) {
        if (verbose) {
            System.out.println("Name:                          Personal no:   ID:");
            System.out.println("--------------------------------------------------------------");

            for (Member m: memberCollection) {
                System.out.printf("%-30s %-14d %-16d%n", m.getName(), m.getPersonalNumber(), m.getIdNumber());
                if (m.hasBoats()) {
                    displayBoats(m.getBoatList());
                }
            }
            System.out.println();
        } else {
            System.out.println("Name:                          ID:              No. of boats: ");
            System.out.println("--------------------------------------------------------------");

            for (Member m: memberCollection) {
                System.out.printf("%-30s %-16d %12d%n", m.getName(), m.getIdNumber(), m.getNumberOfBoats());
            }
            System.out.println();
        }
    }

    public void showMemberInstructions() {
        System.out.println("Add a member to the registry: \n");
    }

    public int updateMemberInstructions() {
        System.out.println("What do you want to update?\n" +
                "1) Name, 2) Personal number");
        return getIntegerInput(1, 2);
    }

    public void removeMemberInstructions() {
        System.out.println("Please enter the personal number of the member to remove:");
    }

    public String getMemberName() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter name: ");
            String name = scanner.nextLine();
            if (name.length() < 2) {
                System.out.println("You have to enter a name!");
            } else {
                return name;
            }
        }
    }

    public long getMemberPersonalNumber() {
        while (true) {
            try {
                System.out.println("Please enter Personal Number:");
                return new Scanner(System.in).nextLong();
            } catch (InputMismatchException i) {
                System.out.println("That is not a number, please try again.");
            }
        }
    }

    public void errorMemberExists() {
        System.err.println("This member already exists in this database!");
    }

    public void errorMemberDoesNotExist() {
        System.err.println("This personal number is unknown in database!");
    }


    /*
    >>>>>> BOATS
     */

    public void displayBoats(Iterator<Boat> boatList) {
        int i = 0;
        while (boatList.hasNext()) {
            Boat boat = boatList.next();
            String sb = "\t" + String.valueOf(++i) + ") " + getBoatTypeString(boat.getType()) +
                    ", " + boat.getLength() + "cm.";
            System.out.println(sb);
        }
        System.out.println();
    }

    public int getBoatSelection(Iterator<Boat> boatList) {
        int i = 0;
        while (boatList.hasNext()) {
            Boat boat = boatList.next();
            String sb = "\t" + String.valueOf(++i) + ") " + getBoatTypeString(boat.getType()) +
                    ", " + boat.getLength() + "cm.";
            System.out.println(sb);
            //i++;
        }
        System.out.println("Enter the boat number:");
        return getIntegerInput(1, i);
    }

    public int getBoatModel() {
        StringBuilder sb = new StringBuilder();
        List<Boat.BoatType> types = Arrays.asList(Boat.BoatType.values());
        for (int i = 1; i <= types.size(); i++) {
            sb.append(i).append(") ")
                    .append(types.get(i-1).toString());
            if (i != types.size()) {
                sb.append(", ");
            }
        }

        System.out.println(sb.toString());

        return getIntegerInput(1,types.size()) - 1;
    }

    public int getBoatLength() {
            System.out.println("Please enter boat length in cm: ");
            return getIntegerInput(1, 999999);
    }

    public void updateBoatInfoInstructions() {
        System.out.println("First enter the personal number of the member, then the ID of the boat to update.");
    }

    public int updateBoatInfoChoice() {
        System.out.println("What to update?\n" +
                "1) Boat type, 2) Length");
        return getIntegerInput(1, 2);
    }

    public void removeBoatInstructions() {
        System.out.println("First enter the personal number of the member, then the ID of the boat to remove.");
    }

    private String getBoatTypeString(Boat.BoatType type) {
        switch (type) {
            case KAYAK: return "Kayak";
            case MOTORSAILER: return "Motorsailer";
            case SAILBOAT: return "Sailboat";
            case OTHER: return "Other";

            default: return "No type selected.";
        }
    }


    /*
    >>>>>>AUTHENTICATION
     */

    public String getUserLogin() {
        Scanner scanner;
        while (true) {
            scanner = new Scanner(System.in);
            System.out.println("Please enter your username:");
            String userName = scanner.next();
            if (!userName.isEmpty())
                return userName;
            System.out.println("No username entered, try again");
        }
    }

    public String getUserPass() {
        Scanner scanner;
        while (true) {
            scanner = new Scanner(System.in);
            System.out.println("Please enter your password:");
            String userPass = scanner.next();
            if (!userPass.isEmpty())
                return userPass;
            System.out.println("No password entered, try again");
        }
    }

    public void displayPassIncorrect() {
        System.out.println("The password you entered for this user is incorrect");
    }

    public void displayUserNotExist() {
        System.out.println("This user does not exist! Contact the system administrator.");
    }


    /*
    >>>>>>SEARCH
     */

    public int getSearchOption() {
        System.out.println("Select the search criteria");
        System.out.println("1. Name");
        System.out.println("2. Month of birth");
        return getIntegerInput(1, 2);
    }

    public String getSearchPhrase() {
        Scanner scanner;
        while (true) {
            System.out.println("Please enter search phrase:");
            scanner = new Scanner(System.in);

            String str = scanner.nextLine();
            if (!str.isEmpty())
                return str;
            System.out.println("That is an empty phrase. Try again");
        }
    }

    public int getBirthMonthSearch() {
        System.out.println("Please enter month number to search:");
        return getIntegerInput(1, 12);
    }
}

// No determining of text inside controller, since controller shouldn't be deciding how the view should display itself
// So, questions etc. should go into view. (Where options of course can still be handled)
// Data (state) is retrieved from model to represent the model in view. getBoats(