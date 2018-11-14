

import java.util.*;

/**
 This is the Teller base class for representing tellers and their behaviour
 in a bank.
 */
public class Teller extends Person {
    private int timeToCompletion;
    private int shiftLength;

    private Client client;

    private final static int EXTRA_TIME = 5;

    /**
     This is the only constructor for this class.  This constructor
     initializes the teller.
     */
    public Teller( String name, int start, boolean business, int len ) {
        super( name, start, business );

        shiftLength = len;
    }

    /**
     This method sets the client for the teller

     parameter: c : Client
     */
    public void setClient( Client c ) {
        //  Set the client and the client's request time.
        timeToCompletion = c.getRequestTime();
        client = c;

        // If the teller does not understand the client, add extra request time
        if( !understands( c ) ) {
            timeToCompletion += EXTRA_TIME;
        }
    }


    /**
     This method returns the finished client of the teller and set it to null;

     returns: client (finished) of the teller.
     */
    public Client exitClient() {
        Client c = null;

        // If teller has client, and client is done
        if( ( client != null ) && available() ) {
            // save client and remove from teller
            c = client;
            client = null;
        }

        // return removed client
        return c;
    }


    /**
     This method increments the clock for the teller.   It returns
     true if the client is finished or its shift is done.

     returns if shift is done or client is finished
     */
    public boolean clockTick() {
        // If teller still working with client, decrement time remaining
        if( timeToCompletion > 0 ) {
            timeToCompletion--;
        }

        // If teller's shift has not ended, decrement it.
        if( shiftLength >= 0 ) {
            shiftLength--;
        }

        // return true if teller is free, or teller's shift is done
        return available() || finished();
    }


    /**
     This method returns true if the teller is available for next client

     returns true if and only the teller is free.
     */
    public boolean available() {
        return timeToCompletion == 0;
    }


    /**
     This method returns true if the teller is available for next client

     returns true if and only the teller is free.
     */
    public boolean finished() {
        // A teller is finished if they have no client and their shift is done
        return ( client == null ) && ( shiftLength < 0 );
    }


    /**
     returns: a String representing the state of the Teller.
     */
    public String toString() {
        return "teller " + super.toString();
    }
}
