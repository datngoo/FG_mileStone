/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package familyconnect;
import java.util.ArrayList;

/**
 *
 * @author DAT
 */
public class FamilyTree {
 
    // ---------- private collections ----------
    private ArrayList<FamilyMember> members;
    private ArrayList<Relationship> relationships;
    private int relationshipCounter;   // used to create IDs: R1, R2, R3, ...
 
    // ---------- constructor ----------
    public FamilyTree() {
        members = new ArrayList<>();
        relationships = new ArrayList<>();
        relationshipCounter = 0;
    }
 
    // =====================================================
    // Helper: find a member by ID (reused by many methods)
    // =====================================================
    private FamilyMember findMemberById(String memberId) {
        for (FamilyMember member : members) {
            if (member.getMemberId().equalsIgnoreCase(memberId)) {
                return member;
            }
        }
        return null;   // not found
    }
 
    /** Lets Main check whether an ID exists before asking for more input. */
    public boolean memberExists(String memberId) {
        return findMemberById(memberId) != null;
    }
 
    // =====================================================
    // 2.1 Add member
    // =====================================================
    public boolean addMember(String memberId, String name, String gender, int birthYear) {
        // Step 1: validate the input
        if (memberId == null || memberId.trim().isEmpty()) {
            System.out.println("Member ID must not be empty.");
            return false;
        }
        if (!FamilyMember.isValidName(name)) {
            System.out.println("Name must not be empty.");
            return false;
        }
        if (!FamilyMember.isValidGender(gender)) {
            System.out.println("Gender must be Male, Female or Other.");
            return false;
        }
        if (!FamilyMember.isValidBirthYear(birthYear)) {
            System.out.println("Birth year must be from " + FamilyMember.MIN_BIRTH_YEAR
                    + " to " + FamilyMember.MAX_BIRTH_YEAR + ".");
            return false;
        }
        // Step 2: check duplicate ID
        if (memberExists(memberId.trim())) {
            System.out.println("Member id already exists");
            return false;
        }
        // Step 3: create the object and add it to the list
        FamilyMember member = new FamilyMember(memberId.trim(), name, gender, birthYear);
        members.add(member);
        System.out.println("Member added successfully");
        return true;
    }
 
    // =====================================================
    // 2.2 Show all members
    // =====================================================
    public void showAllMembers() {
        if (members.isEmpty()) {
            System.out.println("No members found!");
            return;
        }
        for (FamilyMember member : members) {
            System.out.println(member.getInfo());
        }
    }
 
    // =====================================================
    // 2.3 Search member by ID (follows the flowchart with "found")
    // =====================================================
    public void searchMember(String memberId) {
        boolean found = false;
        for (FamilyMember member : members) {
            if (member.getMemberId().equalsIgnoreCase(memberId)) {
                System.out.println(member.getInfo());
                found = true;
                break;   // stop the loop, we already found it
            }
        }
        if (!found) {
            System.out.println("Member not found");
        }
    }
 
    // Extra (US3 says "search by ID or name"): search by part of the name
    public void searchMemberByName(String keyword) {
        int count = 0;
        for (FamilyMember member : members) {
            if (member.getName().toLowerCase().contains(keyword.trim().toLowerCase())) {
                System.out.println(member.getInfo());
                count++;
            }
        }
        if (count == 0) {
            System.out.println("Member not found");
        }
    }
 
    // =====================================================
    // 2.4 Update member
    // =====================================================
    public boolean updateMember(String memberId, String newName, String newGender, int newBirthYear) {
        FamilyMember member = findMemberById(memberId);
        if (member == null) {
            System.out.println("Member not found");
            return false;
        }
        if (!FamilyMember.isValidName(newName)) {
            System.out.println("Name must not be empty.");
            return false;
        }
        if (!FamilyMember.isValidGender(newGender)) {
            System.out.println("Gender must be Male, Female or Other.");
            return false;
        }
        if (!FamilyMember.isValidBirthYear(newBirthYear)) {
            System.out.println("Birth year must be from " + FamilyMember.MIN_BIRTH_YEAR
                    + " to " + FamilyMember.MAX_BIRTH_YEAR + ".");
            return false;
        }
        member.updateInfo(newName, newGender, newBirthYear);
        System.out.println("Member updated successfully");
        return true;
    }
 
    // =====================================================
    // 2.5 Delete member
    // =====================================================
    public boolean deleteMember(String memberId) {
        FamilyMember member = findMemberById(memberId);
        if (member == null) {
            System.out.println("Member not found");
            return false;
        }
        // Remove all relationships that involve this member.
        // Loop from the END to the start: removing an item does not
        // disturb the positions we have not checked yet.
        for (int i = relationships.size() - 1; i >= 0; i--) {
            Relationship r = relationships.get(i);
            if (r.getMember1() == member || r.getMember2() == member) {
                relationships.remove(i);
            }
        }
        members.remove(member);
        System.out.println("Member deleted successfully");
        return true;
    }
 
    // =====================================================
    // 2.6 Add relationship
    // =====================================================
    public boolean addRelationship(String memberId1, String memberId2, String type) {
        // Step 1: find both members
        FamilyMember member1 = findMemberById(memberId1);
        FamilyMember member2 = findMemberById(memberId2);
        if (member1 == null || member2 == null) {
            System.out.println("Member not found");
            return false;
        }
        // Step 2: a member cannot be related to himself/herself
        if (member1 == member2) {
            System.out.println("Can't relate to self");
            return false;
        }
        // Step 3: check the type
        String relType = type.trim().toUpperCase();
        if (!Relationship.isValidType(relType)) {
            System.out.println("Invalid Relationship type (use PARENT, CHILD or SPOUSE)");
            return false;
        }
        // Step 4: create a new relationship, then compare it with the existing ones
        Relationship newRelationship =
                new Relationship("R" + (relationshipCounter + 1), member1, member2, relType);
        for (Relationship r : relationships) {
            if (r.isSameAs(newRelationship)) {
                System.out.println("Relationship already exists");
                return false;
            }
        }
        // Step 5: add it
        relationships.add(newRelationship);
        relationshipCounter++;
        System.out.println("Relationship added: " + newRelationship);
        return true;
    }
 
    // =====================================================
    // 2.7 View family of one member
    // =====================================================
    public void viewFamily(String memberId) {
        FamilyMember selected = findMemberById(memberId);
        if (selected == null) {
            System.out.println("Member not found");
            return;
        }
        System.out.println("Selected member -> " + selected.getInfo());
 
        int count = 0;
        for (Relationship r : relationships) {
            boolean involvesSelected = (r.getMember1() == selected || r.getMember2() == selected);
            if (involvesSelected) {
                String role = determineRole(r, selected);
                FamilyMember other = (r.getMember1() == selected) ? r.getMember2() : r.getMember1();
                System.out.println("  " + role + ": " + other.getName() + " (" + other.getMemberId() + ")");
                count++;
            }
        }
        if (count == 0) {
            System.out.println("No family relationship");
        }
    }
 
    /** "Determine role": what is the OTHER person to the selected member? */
    private String determineRole(Relationship r, FamilyMember selected) {
        if (r.getType().equals(Relationship.SPOUSE)) {
            return "Spouse";
        }
        // parent-child: if the selected member is the parent, the other is the child
        if (r.getParent() == selected) {
            return "Child";
        }
        return "Parent";
    }
 
    // Extra: list all relationships (useful for testing)
    public void showAllRelationships() {
        if (relationships.isEmpty()) {
            System.out.println("No relationships found!");
            return;
        }
        for (Relationship r : relationships) {
            r.displayRelationship();
        }
    }
}
