/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package familyconnect;

/**
 *
 * @author DAT
 */
public class Relationship {
    public static final String PARENT = "PARENT";
    public static final String CHILD = "CHILD";
    public static final String SPOUSE = "SPOUSE";
 
    private String relationshipId;
    private FamilyMember member1;
    private FamilyMember member2;
    private String type;
 
    public Relationship(String relationshipId, FamilyMember member1,
                        FamilyMember member2, String type) {
        this.relationshipId = relationshipId;
        this.member1 = member1;
        this.member2 = member2;
        this.type = type;
    }
 
    // ---------- getters only ----------
    public String getRelationshipId() {
        return relationshipId;
    }
 
    public FamilyMember getMember1() {
        return member1;
    }
 
    public FamilyMember getMember2() {
        return member2;
    }
 
    public String getType() {
        return type;
    }
 
    // ---------- validation ----------
    public static boolean isValidType(String type) {
        return PARENT.equals(type) || CHILD.equals(type) || SPOUSE.equals(type);
    }
 
    // ---------- helper methods ----------
    /** The parent of this relationship (null if the type is SPOUSE). */
    public FamilyMember getParent() {
        if (type.equals(PARENT)) {
            return member1;
        }
        if (type.equals(CHILD)) {
            return member2;
        }
        return null;
    }
 
    /** The child of this relationship (null if the type is SPOUSE). */
    public FamilyMember getChild() {
        if (type.equals(PARENT)) {
            return member2;
        }
        if (type.equals(CHILD)) {
            return member1;
        }
        return null;
    }
 
    /**
     * Two relationships have the same meaning when:
     *  - both are SPOUSE and link the same two people (in any order), or
     *  - they have the same parent and the same child
     *    (so PARENT(A,B) is the same as CHILD(B,A)).
     */
    public boolean isSameAs(Relationship other) {
        boolean thisIsSpouse = type.equals(SPOUSE);
        boolean otherIsSpouse = other.type.equals(SPOUSE);
 
        if (thisIsSpouse && otherIsSpouse) {
            return (member1 == other.member1 && member2 == other.member2)
                    || (member1 == other.member2 && member2 == other.member1);
        }
        if (thisIsSpouse || otherIsSpouse) {
            return false;   // one is SPOUSE, the other is parent-child
        }
        return getParent() == other.getParent() && getChild() == other.getChild();
    }
 
    /** Readable sentence describing the relationship. */
    @Override
    public String toString() {
        if (type.equals(PARENT)) {
            return member1.getName() + " is the parent of " + member2.getName();
        }
        if (type.equals(CHILD)) {
            return member1.getName() + " is the child of " + member2.getName();
        }
        return member1.getName() + " and " + member2.getName() + " are spouses";
    }
 
    /** Print the relationship to the console. */
    public void displayRelationship() {
        System.out.println(relationshipId + ": " + toString());
    }
}

