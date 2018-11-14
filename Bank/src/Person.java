


import java.util.*;

/**
 This is the Person base class for representing people and their behaviour
 in a bank.
 */
public class Person {
    private String name;
    private int startTime;
    private boolean businessPerson;

    private final static String PERSONAL = "personal";
    private final static String BUSINESS = "business";

    private ArrayList<String> languages = new ArrayList<String>();

    private final static String ENGLISH = "English";

    /**
     This is the only constructor for this class.  This constructor
     initializes the person.

     parameter: name : Name of the person
     */
    public Person( String nam, int start, boolean business ) {
        startTime = start;
        name = nam;
        businessPerson = business;
    }

    /**
     This method adds another language to the person.

     parameter: speaks : Name of the language
     */
    public void addLanguage( String speaks ) {
        languages.add( speaks );
    }


    /**
     This method returns true if both people share a common language.

     parameter: p : the other person

     returns true if and only there is one or more languages that both
     people speak.
     */
    public boolean understands( Person p ) {
        // Perform a pairwise comparison of the languages of the two people
        // If they share a common langauge, return true.
        for( String s : languages ) {
            for( String t : p.languages ) {
                if( s.equals( t ) ) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     returns: true if the person is a business person
     */
    public boolean isBusinessPerson() {
        return businessPerson;
    }


    /**
     returns: the start time of the person
     */
    public int getStartTime() {
        return startTime;
    }


    /**
     returns: the name of the person
     */
    public String toString() {
        return name;
    }
}
