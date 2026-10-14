package familyconnect;
 
import java.util.Scanner;
 
/**
 * Main receives console input and shows the menu.
 * All the family-tree logic is inside FamilyTree.
 */
public class Main {
 
    private static Scanner scanner = new Scanner(System.in);
 
    public static void main(String[] args) {
        FamilyTree tree = new FamilyTree();
        int choice;
 
        do {
            showMenu();
            choice = readInt("Your choice: ");
            System.out.println();
 
            switch (choice) {
                case 1:
                    addMemberMenu(tree);
                    break;
                case 2:
                    tree.showAllMembers();
                    break;
                case 3:
                    searchMemberMenu(tree);
                    break;
                case 4:
                    updateMemberMenu(tree);
                    break;
                case 5:
                    deleteMemberMenu(tree);
                    break;
                case 6:
                    addRelationshipMenu(tree);
                    break;
                case 7:
                    viewFamilyMenu(tree);
                    break;
                case 8:
                    searchByNameMenu(tree);
                    break;
                case 9:
                    tree.showAllRelationships();
                    break;
                case 10:
                    loadSampleData(tree);
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice, please choose 0-10.");
            }
            System.out.println();
        } while (choice != 0);
    }
 
    // ---------------------------------------------------
    // Menu
    // ---------------------------------------------------
    private static void showMenu() {
        System.out.println("========== FAMILYCONNECT ==========");
        System.out.println("1. Add member");
        System.out.println("2. Show all members");
        System.out.println("3. Search member by ID");
        System.out.println("4. Update member");
        System.out.println("5. Delete member");
        System.out.println("6. Add relationship");
        System.out.println("7. View family of a member");
        System.out.println("8. Search member by name (extra)");
        System.out.println("9. Show all relationships (extra)");
        System.out.println("10. Load sample data (for testing)");
        System.out.println("0. Exit");
        System.out.println("===================================");
    }
 
    // ---------------------------------------------------
    // One method for each menu option: read input, call FamilyTree
    // ---------------------------------------------------
    private static void addMemberMenu(FamilyTree tree) {
        String memberId = readText("Member ID: ");
        String name = readText("Name: ");
        String gender = readText("Gender (Male/Female/Other): ");
        int birthYear = readInt("Birth year: ");
        tree.addMember(memberId, name, gender, birthYear);
    }
 
    private static void searchMemberMenu(FamilyTree tree) {
        String memberId = readText("Member ID to search: ");
        tree.searchMember(memberId);
    }
 
    private static void searchByNameMenu(FamilyTree tree) {
        String keyword = readText("Name (or part of name) to search: ");
        tree.searchMemberByName(keyword);
    }
 
    private static void updateMemberMenu(FamilyTree tree) {
        String memberId = readText("Member ID to update: ");
        // Follow the flowchart: ask for new information only if the member exists
        if (!tree.memberExists(memberId)) {
            System.out.println("Member not found");
            return;
        }
        String name = readText("New name: ");
        String gender = readText("New gender (Male/Female/Other): ");
        int birthYear = readInt("New birth year: ");
        tree.updateMember(memberId, name, gender, birthYear);
    }
 
    private static void deleteMemberMenu(FamilyTree tree) {
        String memberId = readText("Member ID to delete: ");
        tree.deleteMember(memberId);
    }
 
    private static void addRelationshipMenu(FamilyTree tree) {
        System.out.println("PARENT(A,B): A is the parent of B");
        System.out.println("CHILD(A,B) : A is the child of B");
        System.out.println("SPOUSE(A,B): A and B are spouses");
        String id1 = readText("Member ID 1 (A): ");
        String id2 = readText("Member ID 2 (B): ");
        String type = readText("Type (PARENT/CHILD/SPOUSE): ");
        tree.addRelationship(id1, id2, type);
    }
 
    private static void viewFamilyMenu(FamilyTree tree) {
        String memberId = readText("Member ID to view family: ");
        tree.viewFamily(memberId);
    }
 
    // ---------------------------------------------------
    // Input helpers
    // ---------------------------------------------------
    /** Read one line of text. */
    private static String readText(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }
 
    /** Keep asking until the user types a whole number. */
    private static int readInt(String message) {
        while (true) {
            String text = readText(message);
            if (isNumber(text)) {
                return Integer.parseInt(text);
            }
            System.out.println("Please enter a whole number.");
        }
    }
 
    /** Check that the text contains only digits (so parseInt never fails). */
    private static boolean isNumber(String text) {
        if (text.isEmpty() || text.length() > 9) {
            return false;
        }
        for (int i = 0; i < text.length(); i++) {
            if (!Character.isDigit(text.charAt(i))) {
                return false;
            }
        }
        return true;
    }
 
    private static void loadSampleData(FamilyTree tree) {
        tree.addMember("M01", "Nguyen Van A", "Male", 1950);
        tree.addMember("M02", "Tran Thi B", "Female", 1952);
        tree.addMember("M03", "Nguyen Van C", "Male", 1975);
        tree.addMember("M04", "Le Thi D", "Female", 1978);
        tree.addMember("M05", "Nguyen Van E", "Male", 2005);
 
        tree.addRelationship("M01", "M02", "SPOUSE");
        tree.addRelationship("M01", "M03", "PARENT");
        tree.addRelationship("M02", "M03", "PARENT");
        tree.addRelationship("M03", "M04", "SPOUSE");
        tree.addRelationship("M03", "M05", "PARENT");
        tree.addRelationship("M04", "M05", "PARENT");
    }
}
 
