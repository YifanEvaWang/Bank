

import java.util.*;

/**
 This is the Client base class for representing people and their behaviour
 in a bank.
 */
public class Client extends Person {
    private int reqTime;

    /**
     This is the only constructor for this class.  This constructor
     initializes the person.
     */
    public Client( String name, int start, boolean business, int time ) {
        super( name, start, business );

        reqTime = time;
    }


    /**
     returns: the request time of the client
     */
    public int getRequestTime() {
        return reqTime;
    }


    /**
     returns: a String representing the state of the Client.
     */
    public String toString() {
        return "client " + super.toString();
    }
}
