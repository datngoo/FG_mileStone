/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package familyconnect;

/**
 *
 * @author DAT
 */
public class FamilyMember {
    // valid bYear 
    public static final int MIN_BIRTH_YEAR = 1800;
    public static final int MAX_BIRTH_YEAR = 2026;
 
    private String memberId;
    private String name;
    private String gender;
    private int birthYear;
 
    public FamilyMember(String memberId, String name, String gender, int birthYear) {
        this.memberId = memberId;   // memberId has NO setter, so the ID never changes
        setName(name);
        setGender(gender);
        setBirthYear(birthYear);
    }
 
    // ---------- getters ----------
    public String getMemberId() {
        return memberId;
    }
 
    public String getName() {
        return name;
    }
 
    public String getGender() {
        return gender;
    }
 
    public int getBirthYear() {
        return birthYear;
    }
 
    // ---------- setters (only accept valid data) ----------
    public void setName(String name) {
        if (isValidName(name)) {
            this.name = name.trim();
        }
    }
 
    public void setGender(String gender) {
        if (isValidGender(gender)) {
            String g = gender.trim();
            // Store as "Male", "Female" or "Other"
            this.gender = g.substring(0, 1).toUpperCase() + g.substring(1).toLowerCase();
        }
    }
 
    public void setBirthYear(int birthYear) {
        if (isValidBirthYear(birthYear)) {
            this.birthYear = birthYear;
        }
    }
 
    // ---------- validation methods (static: can be used without an object) ----------
    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }
 
    public static boolean isValidGender(String gender) {
        if (gender == null) {
            return false;
        }
        String g = gender.trim();
        return g.equalsIgnoreCase("Male")
                || g.equalsIgnoreCase("Female")
                || g.equalsIgnoreCase("Other");
    }
 
    public static boolean isValidBirthYear(int birthYear) {
        return birthYear >= MIN_BIRTH_YEAR && birthYear <= MAX_BIRTH_YEAR;
    }
 
    // ---------- other methods ----------
    /** Update name, gender and birth year at once (the ID stays the same). */
    public void updateInfo(String name, String gender, int birthYear) {
        setName(name);
        setGender(gender);
        setBirthYear(birthYear);
    }
 
    /** Full information of this member (used by show / search / view family). */
    public String getInfo() {
        return "ID: " + memberId
                + " | Name: " + name
                + " | Gender: " + gender
                + " | Birth year: " + birthYear;
    }
 
    /** Short text: Name (ID). */
    @Override
    public String toString() {
        return name + " (" + memberId + ")";
    }
}

